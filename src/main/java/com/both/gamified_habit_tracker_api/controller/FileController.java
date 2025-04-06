package com.both.gamified_habit_tracker_api.controller;

import com.both.gamified_habit_tracker_api.model.entity.FileMetadata;
import com.both.gamified_habit_tracker_api.model.response.APIResponseAuth;
import com.both.gamified_habit_tracker_api.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

	@PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Upload a file")
	public ResponseEntity<APIResponseAuth<FileMetadata>> uploadFile(@RequestParam MultipartFile file) {
		FileMetadata fileMetadata = fileService.uploadFile(file);

		APIResponseAuth<FileMetadata> apiResponse = new APIResponseAuth<>(
						true,
						"File uploaded successfully",
						HttpStatus.CREATED,
						fileMetadata,
						LocalDateTime.now()
		);

		return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
	}

	@GetMapping("/preview-file/{file-name}")
	@Operation(summary = "Preview a file")
	public ResponseEntity<?> getFileByFileName(@PathVariable("file-name") String fileName) throws IOException {
		InputStream inputStream = fileService.getFileByFileName(fileName);


		return ResponseEntity.status(HttpStatus.OK)
						.contentType(MediaType.IMAGE_PNG)
						.body(inputStream.readAllBytes());
	}
}
