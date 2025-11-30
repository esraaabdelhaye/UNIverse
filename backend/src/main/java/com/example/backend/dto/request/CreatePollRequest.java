package com.example.backend.dto.request;

import java.time.LocalDateTime;

public class CreatePollRequest {
    private String pollQuestion;
    private String[] options;
    private LocalDateTime endDate;
    private String visibility;

    public CreatePollRequest() {}

    public CreatePollRequest(String pollQuestion, String[] options, LocalDateTime endDate, String visibility) {
        this.pollQuestion = pollQuestion;
        this.options = options;
        this.endDate = endDate;
        this.visibility = visibility;
    }

    public String getPollQuestion() { return pollQuestion; }
    public void setPollQuestion(String pollQuestion) { this.pollQuestion = pollQuestion; }

    public String[] getOptions() { return options; }
    public void setOptions(String[] options) { this.options = options; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }
}

