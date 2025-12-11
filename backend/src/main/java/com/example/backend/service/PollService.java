package com.example.backend.service;

import com.example.backend.dto.DoctorDTO;
import com.example.backend.dto.PollDTO;
import com.example.backend.dto.PollOptionDTO;
import com.example.backend.dto.PostAuthor;
import com.example.backend.dto.SupervisorDTO;
import com.example.backend.dto.request.CreatePollRequest;
import com.example.backend.dto.request.VotePollRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.Doctor;
import com.example.backend.entity.Poll;
import com.example.backend.entity.PollOption;
import com.example.backend.entity.Supervisor;
import com.example.backend.repository.DoctorRepo;
import com.example.backend.repository.PollOptionRepo;
import com.example.backend.repository.PollRepo;
import com.example.backend.repository.SupervisorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import com.example.backend.dto.PollAuthor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PollService {

    private final PollRepo pollRepo;
    private final PollOptionRepo pollOptionRepo;
    private final DoctorRepo doctorRepo;
    private final SupervisorRepo supervisorRepo;

    @Autowired
    public PollService(PollRepo pollRepo, PollOptionRepo pollOptionRepo,
                       DoctorRepo doctorRepo, SupervisorRepo supervisorRepo) {
        this.pollRepo = pollRepo;
        this.pollOptionRepo = pollOptionRepo;
        this.doctorRepo = doctorRepo;
        this.supervisorRepo = supervisorRepo;
    }

    /**
     * Main entry point for Poll creation
     */
    public ApiResponse<PollDTO> createPoll(PollAuthor author, CreatePollRequest request) {
        if (author instanceof DoctorDTO doctorDTO) {
            return createPollForDoctor(doctorDTO, request);
        }
        if (author instanceof SupervisorDTO supervisorDTO) {
            return createPollForSupervisor(supervisorDTO, request);
        }
        return ApiResponse.unauthorized("This user is not authorized");
    }

    /**
     * Poll Creation methods
     */
    private ApiResponse<PollDTO> createPollForDoctor(DoctorDTO doctorDTO, CreatePollRequest request) {
        try {
            Long doctorId = Long.parseLong(doctorDTO.getDoctorId());
            Optional<Doctor> doctorOpt = doctorRepo.findById(doctorId);

            if (doctorOpt.isEmpty()) {
                return ApiResponse.badRequest("Doctor not found");
            }

            Doctor doctor = doctorOpt.get();
            Poll poll = buildPollEntity(request);
            poll.setDoctorCreator(doctor);

            pollRepo.save(poll);

            return ApiResponse.success("Created poll successfully", convertToDTO(poll));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid doctor ID format");
        }
    }

    private ApiResponse<PollDTO> createPollForSupervisor(SupervisorDTO supervisorDTO, CreatePollRequest request) {
        try {
            Long supervisorId = Long.parseLong(supervisorDTO.getEmployeeId());
            Optional<Supervisor> supervisorOpt = supervisorRepo.findById(supervisorId);

            if (supervisorOpt.isEmpty()) {
                return ApiResponse.badRequest("Supervisor not found");
            }

            Supervisor supervisor = supervisorOpt.get();
            Poll poll = buildPollEntity(request);
            poll.setSupervisorCreator(supervisor);

            pollRepo.save(poll);

            return ApiResponse.success("Created poll successfully", convertToDTO(poll));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid supervisor ID format");
        }
    }

    /**
     * Poll Modification methods
     */
    public ApiResponse<PollDTO> updatePoll(PostAuthor author, PollDTO pollDTO) {
        try {
            Long pollId = Long.parseLong(pollDTO.getPollId());
            Optional<Poll> pollOpt = pollRepo.findById(pollId);

            if (pollOpt.isEmpty()) {
                return ApiResponse.badRequest("Poll not found");
            }

            Poll poll = pollOpt.get();

            // Verify authorization
            ApiResponse<Void> authCheck = verifyPollOwnership(author, poll);
            if (!authCheck.isSuccess()) {
                return ApiResponse.serviceUnavailable(authCheck.getMessage());
            }

            // Update poll fields
            if (pollDTO.getPollQuestion() != null) {
                poll.setTitle(pollDTO.getPollQuestion());
            }
            if (pollDTO.getEndDate() != null) {
                poll.setEndTime(pollDTO.getEndDate());
            }

            // Update options is not supported in this version
            // it may require complex handling of existing votes
            // so we skip that for now

            pollRepo.save(poll);

            return ApiResponse.success("Updated poll successfully", convertToDTO(poll));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid poll ID format");
        }
    }

    /**
     * Poll deletion methods
     */
    public ApiResponse<PollDTO> deletePoll(PostAuthor author, PollDTO pollDTO) {
        try {
            Optional<Poll> pollOpt = pollRepo.findById(Long.parseLong(pollDTO.getPollId()));
            if (pollOpt.isEmpty()) return ApiResponse.badRequest("Poll not found");

            Poll poll = pollOpt.get();
            ApiResponse<Void> authCheck = verifyPollOwnership(author, poll);
            if (!authCheck.isSuccess()) {
                return ApiResponse.serviceUnavailable(authCheck.getMessage());
            }

            pollRepo.delete(poll);
            return ApiResponse.success("Deleted poll successfully", convertToDTO(poll));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid poll ID format");
        }
    }

    /**
     * Poll retrieval methods
     */
    public ApiResponse<PollDTO> getPoll(String pollId) {
        try {
            Optional<Poll> pollOpt = pollRepo.findById(Long.parseLong(pollId));
            if (pollOpt.isEmpty()) return ApiResponse.badRequest("Poll not found");

            Poll poll = pollOpt.get();
            return ApiResponse.success("Fetched poll successfully", convertToDTO(poll));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid poll ID format");
        }
    }

    public ApiResponse<List<PollDTO>> getAllPolls(int page, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(page, pageSize, Sort.by("startTime").descending());
            Page<Poll> pollsPage = pollRepo.findAll(pageable);

            List<PollDTO> pollDTOs = pollsPage.getContent().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success("Polls retrieved successfully", pollDTOs);
        } catch (Exception e) {
            return ApiResponse.badRequest("Error retrieving polls");
        }
    }

    public ApiResponse<List<PollDTO>> getPollsByAuthor(PostAuthor author, int page, int pageSize) {
        try {
            List<Poll> polls;

            if (author instanceof DoctorDTO doctorDTO) {
                Long authorId = Long.parseLong(doctorDTO.getDoctorId());
                polls = pollRepo.findByDoctorCreator_Id(authorId);
            } else if (author instanceof SupervisorDTO supervisorDTO) {
                Long authorId = Long.parseLong(supervisorDTO.getEmployeeId());
                polls = pollRepo.findBySupervisorCreator_Id(authorId);
            } else {
                return ApiResponse.unauthorized("Invalid poll author");
            }

            List<PollDTO> pollDTOs = polls.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success("Author's polls retrieved successfully", pollDTOs);
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid author ID format");
        }
    }

    public ApiResponse<List<PollDTO>> getActivePolls() {
        List<Poll> polls = pollRepo.findByEndTimeAfter(LocalDateTime.now());

        List<PollDTO> pollDTOs = polls.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ApiResponse.success("Active polls retrieved successfully", pollDTOs);
    }

    public ApiResponse<List<PollDTO>> searchPolls(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return ApiResponse.badRequest("Search keyword cannot be empty");
        }

        List<Poll> polls = pollRepo.findByTitleContainingIgnoreCase(keyword);

        List<PollDTO> pollDTOs = polls.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ApiResponse.success("Search completed", pollDTOs);
    }

    /**
     * Voting methods
     */
    public ApiResponse<PollDTO> vote(VotePollRequest request) {
        try {
            Optional<Poll> pollOpt = pollRepo.findById(Long.parseLong(request.getPollId()));
            if (pollOpt.isEmpty()) return ApiResponse.badRequest("Poll not found");

            Poll poll = pollOpt.get();

            // Check if poll is still active
            if (poll.getEndTime() != null && poll.getEndTime().isBefore(LocalDateTime.now())) {
                return ApiResponse.badRequest("Poll has ended");
            }

            if (poll.getOptions().size() < request.getSelectedOptionIndex())  return ApiResponse.badRequest("Invalid poll options");


            PollOption option = poll.getOptions().get(request.getSelectedOptionIndex());

            // Verify option belongs to this poll
            if (!option.getPoll().getId().equals(poll.getId())) {
                return ApiResponse.badRequest("Option does not belong to this poll");
            }

            option.setVoteCount(option.getVoteCount() + 1);
            pollOptionRepo.save(option);

            // Currently we don't check for multiple votes by the same user
            // This can be implemented with user tracking if needed
            // But now for simplicity we allow multiple votes

            return ApiResponse.success("Vote recorded successfully", convertToDTO(poll));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid ID format");
        }
    }

    /**
     * Helper methods
     */
    private Poll buildPollEntity(CreatePollRequest request) {
        Poll poll = new Poll();

        String title = request.getPollQuestion() != null && !request.getPollQuestion().trim().isEmpty()
                ? request.getPollQuestion()
                : "Untitled Poll";

        poll.setTitle(title);
        poll.setStartTime(LocalDateTime.now());
        poll.setEndTime(request.getEndDate());

        // Add options
        if (request.getOptions() != null) {
            for (String optionText : request.getOptions()) {
                if (optionText != null && !optionText.trim().isEmpty()) {
                    PollOption option = new PollOption();
                    option.setText(optionText);
                    option.setVoteCount(0);
                    poll.addOption(option);
                }
            }
        }

        return poll;
    }

    private ApiResponse<Void> verifyPollOwnership(PostAuthor author, Poll poll) {
        try {
            if (author instanceof DoctorDTO doctorDTO) 
            {
                Long requesterId = Long.parseLong(doctorDTO.getDoctorId());
                if (poll.getDoctorCreator() == null || !requesterId.equals(poll.getDoctorCreator().getId())) {
                    return ApiResponse.serviceUnavailable("Creator id doesn't match");
                }
            } 
            else if (author instanceof SupervisorDTO supervisorDTO) {
                Long requesterId = Long.parseLong(supervisorDTO.getEmployeeId());
                if (poll.getSupervisorCreator() == null || !requesterId.equals(poll.getSupervisorCreator().getId())) {
                    return ApiResponse.serviceUnavailable("Creator id doesn't match");
                }
            } 
            else return ApiResponse.unauthorized("Invalid author type");
        } 
        catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid author ID format");
        }

        return ApiResponse.success(null);
    }

    private PollDTO convertToDTO(Poll poll) {
        PollDTO dto = new PollDTO();
        dto.setPollId(String.valueOf(poll.getId()));
        dto.setPollQuestion(poll.getTitle());
        dto.setEndDate(poll.getEndTime());

        // Determine status based on end time
        if (poll.getEndTime() != null && poll.getEndTime().isBefore(LocalDateTime.now())) {
            dto.setStatus("CLOSED");
        } else {
            dto.setStatus("ACTIVE");
        }

        // Convert options and calculate total votes
        // Store Options DTOs
        int totalVotes = 0;
        if (poll.getOptions() != null && !poll.getOptions().isEmpty()) {
            for (PollOption opt : poll.getOptions()) {
                totalVotes += opt.getVoteCount();
            }

            final int finalTotalVotes = totalVotes;
            PollOptionDTO[] optionDTOs = poll.getOptions().stream()
                    .map(opt -> {
                        PollOptionDTO optDTO = new PollOptionDTO();
                        optDTO.setOptionText(opt.getText());
                        optDTO.setVoteCount(opt.getVoteCount());
                        optDTO.setVotePercentage(finalTotalVotes > 0
                                ? (double) opt.getVoteCount() / finalTotalVotes * 100
                                : 0.0);
                        return optDTO;
                    })
                    .toArray(PollOptionDTO[]::new);

            dto.setOptions(optionDTOs);
        }
    
        dto.setTotalVotes(totalVotes);

        return dto;
    }

}
