package com.both.gamified_habit_tracker_api.service;

import com.both.gamified_habit_tracker_api.model.entity.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileService {
    FileMetadata uploadFile(MultipartFile file);

    InputStream getFileByFileName(String fileName);
}
