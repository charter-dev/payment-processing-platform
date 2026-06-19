package payment.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import payment.entity.ApiResponse;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ApiResponse<?>> handleNotFound(NoHandlerFoundException ex, HttpServletRequest request) {

		log.warn("Endpoint not found: {}", request.getRequestURI());

		return buildResponse(HttpStatus.NOT_FOUND, "Endpoint not found", "ENDPOINT_NOT_FOUND", request.getRequestURI());
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException ex,
			HttpServletRequest request) {

		log.warn("Resource not found: {}", ex.getMessage());

		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getErrorCode(), request.getRequestURI());
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiResponse<?>> handleBusiness(BusinessException ex, HttpServletRequest request) {

		log.warn("Business error: {}", ex.getMessage());

		 HttpStatus status = HttpStatus.BAD_REQUEST;//ex.getHttpStatus() != null ? ex.getHttpStatus() : HttpStatus.BAD_REQUEST;

		 return buildResponse(status, ex.getMessage(), ex.getErrorCode(), request.getRequestURI());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleGeneric(Exception ex, HttpServletRequest request) {

		log.error("Unexpected error", ex);

		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "INTERNAL_ERROR",
				request.getRequestURI());
	}

	private ResponseEntity<ApiResponse<?>> buildResponse(HttpStatus status, String message, String errorCode,
			String path) {

		return ResponseEntity.status(status).body(ApiResponse.builder().timestamp(LocalDateTime.now().toString())
				.status(status.value()).message(message).errorCode(errorCode).path(path).data(null).build());
	}
}