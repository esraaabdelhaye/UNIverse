package com.example.backend.dto.response;

import java.util.List;

public class PageResponse<T> {
    private List<T> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalElements;
    private Integer totalPages;
    private Boolean isLastPage;
    private Boolean isFirstPage;

    public PageResponse() {}

    public PageResponse(List<T> content, Integer pageNumber, Integer pageSize,
                        Integer totalElements, Integer totalPages, Boolean isLastPage, Boolean isFirstPage) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.isLastPage = isLastPage;
        this.isFirstPage = isFirstPage;
    }

    public List<T> getContent() { return content; }
    public void setContent(List<T> content) { this.content = content; }

    public Integer getPageNumber() { return pageNumber; }
    public void setPageNumber(Integer pageNumber) { this.pageNumber = pageNumber; }

    public Integer getPageSize() { return pageSize; }
    public void setPageSize(Integer pageSize) { this.pageSize = pageSize; }

    public Integer getTotalElements() { return totalElements; }
    public void setTotalElements(Integer totalElements) { this.totalElements = totalElements; }

    public Integer getTotalPages() { return totalPages; }
    public void setTotalPages(Integer totalPages) { this.totalPages = totalPages; }

    public Boolean getIsLastPage() { return isLastPage; }
    public void setIsLastPage(Boolean isLastPage) { this.isLastPage = isLastPage; }

    public Boolean getIsFirstPage() { return isFirstPage; }
    public void setIsFirstPage(Boolean isFirstPage) { this.isFirstPage = isFirstPage; }
}

