package com.example.backend.dto;

import java.time.LocalDateTime;

public class PollDTO {
    private String pollId;
    private String pollQuestion;
    private PollOptionDTO[] options;
    private String status;
    private Integer totalVotes;
    private LocalDateTime endDate;

    public PollDTO() {}

    public PollDTO(String pollId, String pollQuestion, PollOptionDTO[] options,
                   String status, Integer totalVotes, LocalDateTime endDate) {
        this.pollId = pollId;
        this.pollQuestion = pollQuestion;
        this.options = options;
        this.status = status;
        this.totalVotes = totalVotes;
        this.endDate = endDate;
    }

    public String getPollId() { return pollId; }
    public void setPollId(String pollId) { this.pollId = pollId; }

    public String getPollQuestion() { return pollQuestion; }
    public void setPollQuestion(String pollQuestion) { this.pollQuestion = pollQuestion; }

    public PollOptionDTO[] getOptions() { return options; }
    public void setOptions(PollOptionDTO[] options) { this.options = options; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getTotalVotes() { return totalVotes; }
    public void setTotalVotes(Integer totalVotes) { this.totalVotes = totalVotes; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
}
