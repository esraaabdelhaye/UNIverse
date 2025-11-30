package com.example.backend.dto;

public class PollOptionDTO {
    private String optionText;
    private Integer voteCount;
    private Double votePercentage;

    public PollOptionDTO() {}

    public PollOptionDTO(String optionText, Integer voteCount, Double votePercentage) {
        this.optionText = optionText;
        this.voteCount = voteCount;
        this.votePercentage = votePercentage;
    }

    public String getOptionText() { return optionText; }
    public void setOptionText(String optionText) { this.optionText = optionText; }

    public Integer getVoteCount() { return voteCount; }
    public void setVoteCount(Integer voteCount) { this.voteCount = voteCount; }

    public Double getVotePercentage() { return votePercentage; }
    public void setVotePercentage(Double votePercentage) { this.votePercentage = votePercentage; }
}
