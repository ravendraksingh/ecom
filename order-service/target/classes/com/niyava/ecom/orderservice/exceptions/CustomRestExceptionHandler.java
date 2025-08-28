package com.niyava.ecom.orderservice.exceptions;


import com.niyava.ecom.common.error.ApiError;
import com.niyava.ecom.common.error.ErrorResponse;
import com.niyava.ecom.common.exceptions.BadRequestException;
import com.niyava.ecom.common.exceptions.BaseException;
import com.niyava.ecom.common.exceptions.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.niyava.ecom.orderservice.constants.OrderServiceErrorCodes.ORDER_NOT_FOUND;
import static com.niyava.ecom.orderservice.constants.OrderServiceErrorMessages.ERR_MSG_API_ERROR;
import static com.niyava.ecom.orderservice.constants.OrderServiceErrorMessages.ERR_MSG_INVALID_PAYLOAD;

//@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomRestExceptionHandler.class);

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> badRequestExceptionHandler(BadRequestException ex) {
        log.error("exception: {}, message: {}", ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity notFoundExceptionHandler(NotFoundException ex) {
        log.error("exception: {}, message: {}", ex.getClass().getName(), ex.getMessage());
        final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ORDER_NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid (final MethodArgumentNotValidException ex,
//                                                                   final HttpHeaders headers,
//                                                                   final HttpStatus status,
//                                                                   final WebRequest request) {
//        log.error("exception: {}", ex.getClass().getName());
//        final List<String> errors = new ArrayList<String>();
//        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
////            errors.add(error.getField() + ": " + error.getDefaultMessage());
//            errors.add(error.getDefaultMessage());
//        }
//        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
//            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
//        }
//        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ERR_MSG_INVALID_PAYLOAD, errors);
//        log.error("apiError: {}", apiError);
//        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
//    }

    private String getErrorMessage(List<String> errors) {
        String errorMessage = errors.stream().map(String::valueOf).collect(Collectors.joining(", "));
        return errorMessage;
    }

//    @Override
//    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
//                                                         final HttpStatus status, final WebRequest request) {
//        final List<String> errors = new ArrayList<String>();
//        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
//            errors.add(error.getField() + ":" + error.getDefaultMessage());
//        }
//        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
//            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
//        }
//        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ERR_MSG_INVALID_PAYLOAD, errors);
//        log.error("apiError: {}", apiError);
//        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
//    }

//    @Override
//    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
//                                                        final HttpStatus status, final WebRequest request) {
//        log.error("exception: {}", ex.getClass().getName());
//        final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();
//        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ERR_MSG_INVALID_PAYLOAD, error);
//        log.error("apiError: {}", apiError);
//        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
//    }

//    @Override
//    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
//                                                                     final HttpHeaders headers,
//                                                                     final HttpStatus status,
//                                                                     final WebRequest request) {
//        log.error("exception: {}", ex.getClass().getName());
//        final String error = ex.getRequestPartName() + " part is missing";
//        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ERR_MSG_INVALID_PAYLOAD, error);
//        log.error("apiError: {}", apiError);
//        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
//    }

//    @Override
//    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex,
//                                                                          final HttpHeaders headers,
//                                                                          final HttpStatus status,
//                                                                          final WebRequest request) {
//        log.error("exception: {}", ex.getClass().getName());
//        final String error = ex.getParameterName() + " parameter is missing";
//        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ERR_MSG_INVALID_PAYLOAD, error);
//        log.error("apiError: {}", apiError);
//        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
//    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex) {
        log.error("exception: {}", ex.getClass().getName());
        final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ERR_MSG_INVALID_PAYLOAD, error);
        log.error("apiError: {}", apiError);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex) {
        log.error("exception: {}", ex.getClass().getName());
        final List<String> errors = new ArrayList<String>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }

        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ERR_MSG_INVALID_PAYLOAD, errors);
        log.error("apiError: {}", apiError);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // 404
//    @Override
//    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
//                                                                   final HttpHeaders headers,
//                                                                   final HttpStatus status,
//                                                                   final WebRequest request) {
//        log.error("exception: {}", ex.getClass().getName());
//        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
//        final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ERR_MSG_INVALID_PAYLOAD, error);
//        log.error("apiError: {}", apiError);
//        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
//    }

    // 405
//    @Override
//    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex,
//                                                                         final HttpHeaders headers,
//                                                                         final HttpStatus status,
//                                                                         final WebRequest request) {
//        log.error("exception: {}", ex.getClass().getName());
//        final StringBuilder builder = new StringBuilder();
//        builder.append(ex.getMethod());
//        builder.append(" method is not supported for this request. Supported methods are ");
//        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
//
//        final ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());
//        log.error("apiError: {}", apiError);
//        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
//    }

    // 415
//    @Override
//    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
//                                                                     final HttpHeaders headers,
//                                                                     final HttpStatus status,
//                                                                     final WebRequest request) {
//        log.error("exception: {}", ex.getClass().getName());
//        final StringBuilder builder = new StringBuilder();
//        builder.append(ex.getContentType());
//        builder.append(" media type is not supported. Supported media types are ");
//        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));
//
//        final ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(),
//                builder.substring(0, builder.length() - 2));
//        log.error("apiError: {}", apiError);
//        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
//    }

//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
//                                                                  final HttpHeaders headers,
//                                                                  final HttpStatus status,
//                                                                  final WebRequest request) {
////        return super.handleHttpMessageNotReadable(ex, headers, status, request);
//        log.error("exception: {}", ex.getClass().getName());
//        final ApiError apiError = new ApiError(status, ERR_MSG_INVALID_PAYLOAD, "Bad request");
//        log.error("apiError: {}", apiError);
//        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
//    }

    // 500
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        log.error("exception: {}", ex.getClass().getName());
        final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ERR_MSG_API_ERROR,
                "error occurred");
        log.error("apiError: {}", apiError);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    private ErrorResponse getErrorObject(BaseException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(e.getStatus());
        errorResponse.setCode(e.getCode());
        errorResponse.setMessage(e.getCustomMessage());
        return errorResponse;
    }

}
