package com.example.backend.dto.request;

public class VotePollRequest {
    private String pollId;
    private String voterId;
    private Integer selectedOptionIndex;

    public VotePollRequest() {}

    public VotePollRequest(String pollId, String voterId, Integer selectedOptionIndex) {
        this.pollId = pollId;
        this.voterId = voterId;
        this.selectedOptionIndex = selectedOptionIndex;
    }

    public String getPollId() { return pollId; }
    public void setPollId(String pollId) { this.pollId = pollId; }

    public String getVoterId() { return voterId; }
    public void setVoterId(String voterId) { this.voterId = voterId; }

    public Integer getSelectedOptionIndex() { return selectedOptionIndex; }
    public void setSelectedOptionIndex(Integer selectedOptionIndex) { this.selectedOptionIndex = selectedOptionIndex; }
}