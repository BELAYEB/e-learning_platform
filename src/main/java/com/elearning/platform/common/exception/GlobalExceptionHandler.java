package com.elearning.platform.common.exception;

import com.elearning.platform.common.web.ApiError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ========== Custom Business Exceptions ==========

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return createApiError(
                HttpStatus.NOT_FOUND,
                "Resource Not Found",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDuplicateResource(DuplicateResourceException ex, HttpServletRequest request) {
        return createApiError(
                HttpStatus.CONFLICT,
                "Duplicate Resource",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(BadRequestException ex, HttpServletRequest request) {
        return createApiError(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleUnauthorized(UnauthorizedException ex, HttpServletRequest request) {
        return createApiError(
                HttpStatus.UNAUTHORIZED,
                "Unauthorized",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleForbidden(ForbiddenException ex, HttpServletRequest request) {
        return createApiError(
                HttpStatus.FORBIDDEN,
                "Forbidden",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(EnrollmentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleEnrollmentException(EnrollmentException ex, HttpServletRequest request) {
        return createApiError(
                HttpStatus.BAD_REQUEST,
                "Enrollment Error",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleInvalidCredentials(InvalidCredentialsException ex, HttpServletRequest request) {
        return createApiError(
                HttpStatus.UNAUTHORIZED,
                "Invalid Credentials",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    // ========== Spring Validation Exceptions ==========

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();

        return createApiError(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                "One or more fields are invalid",
                request.getRequestURI(),
                errors
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMissingParams(MissingServletRequestParameterException ex, HttpServletRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        return createApiError(
                HttpStatus.BAD_REQUEST,
                "Missing Parameter",
                error,
                request.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        return createApiError(
                HttpStatus.BAD_REQUEST,
                "Type Mismatch",
                error,
                request.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return createApiError(
                HttpStatus.BAD_REQUEST,
                "Malformed JSON Request",
                "Request body is not readable or malformed",
                request.getRequestURI(),
                null
        );
    }

    // ========== Spring Security Exceptions ==========

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleAuthentication(AuthenticationException ex, HttpServletRequest request) {
        return createApiError(
                HttpStatus.UNAUTHORIZED,
                "Authentication Failed",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        return createApiError(
                HttpStatus.UNAUTHORIZED,
                "Bad Credentials",
                "Invalid username or password",
                request.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        return createApiError(
                HttpStatus.FORBIDDEN,
                "Access Denied",
                "You don't have permission to access this resource",
                request.getRequestURI(),
                null
        );
    }

    // ========== HTTP Method and Content Type Exceptions ==========

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiError handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        String error = ex.getMethod() + " method is not supported for this endpoint";
        return createApiError(
                HttpStatus.METHOD_NOT_ALLOWED,
                "Method Not Allowed",
                error,
                request.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ApiError handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        return createApiError(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                "Unsupported Media Type",
                "Content type " + ex.getContentType() + " is not supported",
                request.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNoHandlerFound(NoHandlerFoundException ex, HttpServletRequest request) {
        return createApiError(
                HttpStatus.NOT_FOUND,
                "Endpoint Not Found",
                "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL(),
                request.getRequestURI(),
                null
        );
    }

    // ========== Database Exceptions ==========

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        String message = "Database constraint violation";
        if (ex.getMessage().contains("Duplicate entry")) {
            message = "Record with this value already exists";
        } else if (ex.getMessage().contains("foreign key constraint")) {
            message = "Cannot delete/modify because of existing references";
        }
        return createApiError(
                HttpStatus.CONFLICT,
                "Data Integrity Violation",
                message,
                request.getRequestURI(),
                null
        );
    }

    // ========== IllegalArgument Exception ==========

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        return createApiError(
                HttpStatus.BAD_REQUEST,
                "Invalid Argument",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleIllegalState(IllegalStateException ex, HttpServletRequest request) {
        return createApiError(
                HttpStatus.CONFLICT,
                "Invalid State",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    // ========== Generic Exception Handler ==========

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleGeneric(Exception ex, HttpServletRequest request) {
        // Log the exception for debugging
        ex.printStackTrace();

        return createApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "An unexpected error occurred. Please contact support.",
                request.getRequestURI(),
                null
        );
    }

    // ========== Helper Method ==========

    private ApiError createApiError(HttpStatus status, String error, String message,
                                     String path, List<String> validationErrors) {
        return new ApiError(
                LocalDateTime.now(),
                status.value(),
                error,
                message,
                path,
                validationErrors != null ? validationErrors : Collections.emptyList()
        );
    }
}

