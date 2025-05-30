package com.both.gamified_habit_tracker_api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileMetadata {

    private String fileName;
    private String fileType;
    private String fileUrl;
    private Long fileSize;

}
