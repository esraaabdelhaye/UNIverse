package com.example.backend.service;

import com.example.backend.dto.DoctorDTO;
import com.example.backend.dto.QuestionDTO;
import com.example.backend.dto.StudentDTO;
import com.example.backend.dto.request.AnswerQuestionRequest;
import com.example.backend.dto.request.AskQuestionRequest;
import com.example.backend.dto.response.ApiResponse;
import com.example.backend.entity.Doctor;
import com.example.backend.entity.Question;
import com.example.backend.entity.Student;
import com.example.backend.repository.DoctorRepo;
import com.example.backend.repository.QuestionRepo;
import com.example.backend.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepo questionRepo;
    private final StudentRepo studentRepo;
    private final DoctorRepo doctorRepo;

    @Autowired
    public QuestionService(QuestionRepo questionRepo, StudentRepo studentRepo, DoctorRepo doctorRepo) {
        this.questionRepo = questionRepo;
        this.studentRepo = studentRepo;
        this.doctorRepo = doctorRepo;
    }

    /**
     * Main entry point for asking a question (Students only)
     */
    
    /** Not forget to mention that we can get the studentDTO from the security context in the controller layer
     * and pass it here as a parameter this is allowed due to the fact that we use spring security with sessions
     * so we can get the authenticated user details from the security context
     */
    public ApiResponse<QuestionDTO> askQuestion(StudentDTO studentDTO, AskQuestionRequest request) {
        try {
            Long studentId = Long.parseLong(studentDTO.getStudentId());
            Optional<Student> studentOpt = studentRepo.findById(studentId);

            if (studentOpt.isEmpty()) {
                return ApiResponse.badRequest("Student not found");
            }

            Student student = studentOpt.get();
            Question question = buildQuestionEntity(request);
            question.setAuthor(student);

            questionRepo.save(question);

            return ApiResponse.success("Question asked successfully", convertToDTO(question));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid student ID format");
        }
    }

    /**
     * Answer a question (Doctors only)
     * In case of supervision, supervisor will be treated as doctor
     * this is allowed by database schema design
     * since supervisor entity extends doctor entity
     * using Single Table Inheritance strategy
     * I may also add a role check later if needed and separate supervisors from doctors
     */
    public ApiResponse<QuestionDTO> answerQuestion(DoctorDTO doctorDTO, AnswerQuestionRequest request) {
        try {
            Long doctorId = Long.parseLong(doctorDTO.getDoctorId());
            Optional<Doctor> doctorOpt = doctorRepo.findById(doctorId);

            if (doctorOpt.isEmpty()) {
                return ApiResponse.badRequest("Doctor not found");
            }

            Long questionId = Long.parseLong(request.getQuestionId());
            Optional<Question> questionOpt = questionRepo.findById(questionId);

            if (questionOpt.isEmpty()) {
                return ApiResponse.badRequest("Question not found");
            }

            Doctor doctor = doctorOpt.get();
            Question question = questionOpt.get();

            question.setAnswer(request.getAnswerContent());
            question.setDoctorResponder(doctor);
            question.setAnsweredAt(LocalDateTime.now());
            question.setStatus("ANSWERED");
            question.setAnswerCount(question.getAnswerCount() + 1);

            questionRepo.save(question);

            return ApiResponse.success("Question answered successfully", convertToDTO(question));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid ID format");
        }
    }

    /**
     * Question Modification methods
     */
    public ApiResponse<QuestionDTO> updateQuestion(StudentDTO studentDTO, QuestionDTO questionDTO) {
        try {
            Long questionId = Long.parseLong(questionDTO.getQuestionId());
            Optional<Question> questionOpt = questionRepo.findById(questionId);

            if (questionOpt.isEmpty()) {
                return ApiResponse.badRequest("Question not found");
            }

            Question question = questionOpt.get();

            // Verify authorization - only the author can update
            ApiResponse<Void> authCheck = verifyQuestionOwnership(studentDTO, question);
            if (!authCheck.isSuccess()) {
                return ApiResponse.serviceUnavailable(authCheck.getMessage());
            }

            // Update question fields
            if (questionDTO.getQuestionTitle() != null) {
                question.setTitle(questionDTO.getQuestionTitle());
            }
            if (questionDTO.getQuestionContent() != null) {
                question.setContent(questionDTO.getQuestionContent());
            }
            if (questionDTO.getTags() != null) {
                question.setTags(Arrays.toString(questionDTO.getTags()));
            }
            if (questionDTO.getPriority() != null) {
                question.setPriority(questionDTO.getPriority());
            }

            questionRepo.save(question);

            return ApiResponse.success("Question updated successfully", convertToDTO(question));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid question ID format");
        }
    }

    public ApiResponse<QuestionDTO> addUpvote(String questionId) {
        try {
            Optional<Question> questionOpt = questionRepo.findById(Long.parseLong(questionId));

            if (questionOpt.isEmpty()) {
                return ApiResponse.badRequest("Question not found");
            }

            Question question = questionOpt.get();
            question.setUpvotes(question.getUpvotes() + 1);
            questionRepo.save(question);

            return ApiResponse.success("Upvote added successfully", convertToDTO(question));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid question ID format");
        }
    }

    public ApiResponse<QuestionDTO> incrementViewCount(String questionId) {
        try {
            Optional<Question> questionOpt = questionRepo.findById(Long.parseLong(questionId));

            if (questionOpt.isEmpty()) {
                return ApiResponse.badRequest("Question not found");
            }

            Question question = questionOpt.get();
            question.setViewCount(question.getViewCount() + 1);
            questionRepo.save(question);

            return ApiResponse.success("View count incremented", convertToDTO(question));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid question ID format");
        }
    }

    /**
     * Question deletion methods
     */
    public ApiResponse<QuestionDTO> deleteQuestion(StudentDTO studentDTO, QuestionDTO questionDTO) {
        try {
            Optional<Question> questionOpt = questionRepo.findById(Long.parseLong(questionDTO.getQuestionId()));
            if (questionOpt.isEmpty()) return ApiResponse.badRequest("Question not found");

            Question question = questionOpt.get();
            ApiResponse<Void> authCheck = verifyQuestionOwnership(studentDTO, question);
            if (!authCheck.isSuccess()) {
                return ApiResponse.serviceUnavailable(authCheck.getMessage());
            }

            questionRepo.delete(question);
            return ApiResponse.success("Question deleted successfully", convertToDTO(question));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid question ID format");
        }
    }

    /**
     * Question retrieval methods
     */
    public ApiResponse<QuestionDTO> getQuestion(String questionId) {
        try {
            Optional<Question> questionOpt = questionRepo.findById(Long.parseLong(questionId));
            if (questionOpt.isEmpty()) return ApiResponse.badRequest("Question not found");

            Question question = questionOpt.get();
            return ApiResponse.success("Fetched question successfully", convertToDTO(question));
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid question ID format");
        }
    }

    public ApiResponse<List<QuestionDTO>> getAllQuestions(int page, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
            Page<Question> questionsPage = questionRepo.findAll(pageable);

            List<QuestionDTO> questionDTOs = questionsPage.getContent().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success("Questions retrieved successfully", questionDTOs);
        } catch (Exception e) {
            return ApiResponse.badRequest("Error retrieving questions");
        }
    }

    public ApiResponse<List<QuestionDTO>> getQuestionsByStudent(StudentDTO studentDTO, int page, int pageSize) {
        try {
            Long studentId = Long.parseLong(studentDTO.getStudentId());
            List<Question> questions = questionRepo.findByAuthor_Id(studentId);

            List<QuestionDTO> questionDTOs = questions.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ApiResponse.success("Student's questions retrieved successfully", questionDTOs);
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid student ID format");
        }
    }

    public ApiResponse<List<QuestionDTO>> getQuestionsByStatus(String status) {
        List<Question> questions = questionRepo.findByStatus(status);

        List<QuestionDTO> questionDTOs = questions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ApiResponse.success("Questions retrieved successfully", questionDTOs);
    }

    public ApiResponse<List<QuestionDTO>> getUnansweredQuestions() {
        List<Question> questions = questionRepo.findByStatus("UNANSWERED");

        List<QuestionDTO> questionDTOs = questions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ApiResponse.success("Unanswered questions retrieved successfully", questionDTOs);
    }

    public ApiResponse<List<QuestionDTO>> searchQuestions(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return ApiResponse.badRequest("Search keyword cannot be empty");
        }

        List<Question> questions = questionRepo.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);

        List<QuestionDTO> questionDTOs = questions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ApiResponse.success("Search completed", questionDTOs);
    }

    /**
     * Helper methods
     */
    private Question buildQuestionEntity(AskQuestionRequest request) {
        Question question = new Question();

        String content = request.getQuestionContent() != null && !request.getQuestionContent().trim().isEmpty()
                ? request.getQuestionContent()
                : "No content";

        String title = request.getQuestionTitle() != null && !request.getQuestionTitle().trim().isEmpty()
                ? request.getQuestionTitle()
                : "Untitled Question";

        question.setContent(content);
        question.setTitle(title);
        question.setCreatedAt(LocalDateTime.now());
        question.setStatus("UNANSWERED");
        question.setPriority(request.getPriority() != null ? request.getPriority() : "NORMAL");
        question.setViewCount(0);
        question.setUpvotes(0);
        question.setAnswerCount(0);

        // Handle tags safely
        if (request.getTags() != null && request.getTags().length > 0) {
            question.setTags(Arrays.toString(request.getTags()));
        } 
        else 
        {
            question.setTags("[]");
        }

        return question;
    }

    private ApiResponse<Void> verifyQuestionOwnership(StudentDTO studentDTO, Question question) {
        try {
            Long requesterId = Long.parseLong(studentDTO.getStudentId());
            if (question.getAuthor() == null || !requesterId.equals(question.getAuthor().getId())) {
                return ApiResponse.serviceUnavailable("Author id doesn't match");
            }
        } catch (NumberFormatException e) {
            return ApiResponse.badRequest("Invalid student ID format");
        }

        return ApiResponse.success(null);
    }

    private QuestionDTO convertToDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setQuestionId(String.valueOf(question.getId()));
        dto.setQuestionTitle(question.getTitle());
        dto.setQuestionContent(question.getContent());
        dto.setAskedDate(question.getCreatedAt());
        dto.setStatus(question.getStatus());
        dto.setPriority(question.getPriority());
        dto.setViewCount(question.getViewCount());
        dto.setUpvotes(question.getUpvotes());
        dto.setAnswerCount(question.getAnswerCount());

        // Parse tags back to array
        if (question.getTags() != null && !question.getTags().equals("[]")) {
            String tagsStr = question.getTags().replaceAll("[\\[\\]]", "");
            if (!tagsStr.isEmpty()) {
                dto.setTags(tagsStr.split(",\\s*"));
            }
        }

        // Set author info
        if (question.getAuthor() != null) {
            dto.setAskedBy(String.valueOf(question.getAuthor().getId()));
            dto.setAskerName(question.getAuthor().getName());
        }

        return dto;
    }
}
