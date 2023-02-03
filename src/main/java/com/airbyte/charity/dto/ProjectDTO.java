package com.airbyte.charity.dto;

import java.util.List;

public class ProjectDTO {
    private String title;
    private String description;
    private String status;
    private Long like;
    private CommentDTO comment;
    private Double expectedBudge;
    private Double prepareBudge;
    private String startDate;
    private String endDate;
    private ReportDTO report;
    private String profileId;
    private String motivationSentence;
    private String priority;
    private List<FileDTO> files;
}
