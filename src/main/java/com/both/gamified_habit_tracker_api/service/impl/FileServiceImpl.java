package com.both.gamified_habit_tracker_api.service.impl;

import com.both.gamified_habit_tracker_api.model.entity.FileMetadata;
import com.both.gamified_habit_tracker_api.service.FileService;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

	@Value("${minio.bucket.name}")
	private String bucketName;

	private final MinioClient minioClient;

	@SneakyThrows
	@Override
	public FileMetadata uploadFile(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("File cannot be null or empty.");
		}

		String originalFileName = file.getOriginalFilename();
		if (!StringUtils.hasText(originalFileName)) {
			throw new IllegalArgumentException("File name is required.");
		}

		// Check if the bucket exists, and create it if it doesn't
		try {
			boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
			if (!bucketExists) {
				minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to check or create bucket: " + bucketName, e);
		}

		// Generate a unique file name
		String fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(originalFileName);

		// Upload the file to MinIO
		try {
			minioClient.putObject(
							PutObjectArgs.builder()
											.bucket(bucketName)
											.object(fileName)
											.contentType(file.getContentType())
											.stream(file.getInputStream(), file.getSize(), -1)
											.build()
			);
		} catch (Exception e) {
			throw new RuntimeException("Failed to upload file: " + fileName, e);
		}

		// Generate the file URL
		String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/v1/files/preview-file/" + fileName)
						.toUriString();

		return FileMetadata.builder()
						.fileName(fileName)
						.fileUrl(fileUrl)
						.fileType(file.getContentType())
						.fileSize(file.getSize())
						.build();
	}

	@SneakyThrows
	@Override
	public InputStream getFileByFileName(String fileName) {
		if (!StringUtils.hasText(fileName)) {
			throw new IllegalArgumentException("File name is required.");
		}

		try {
			return minioClient.getObject(
							GetObjectArgs.builder()
											.bucket(bucketName)
											.object(fileName)
											.build()
			);
		} catch (ErrorResponseException e) {
			if ("NoSuchKey".equals(e.errorResponse().code())) {
				throw new FileNotFoundException("File not found: " + fileName);
			}
			throw new RuntimeException("Error retrieving file: " + fileName, e);
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error while retrieving file: " + fileName, e);
		}
	}

}
