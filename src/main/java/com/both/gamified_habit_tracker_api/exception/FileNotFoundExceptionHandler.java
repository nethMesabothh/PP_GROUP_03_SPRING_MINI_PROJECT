package com.both.gamified_habit_tracker_api.exception;

public class FileNotFoundExceptionHandler extends RuntimeException {
	public class FileNotFoundException extends RuntimeException {
		public FileNotFoundException(String message) {
			super(message);
		}
	}

	public class FileUploadException extends RuntimeException {
		public FileUploadException(String message, Throwable cause) {
			super(message, cause);
		}
	}
}
