package az.shuseynli.sales_project
.exception;

import az.shuseynli.sales_project
.exception.custom.CustomException;
import az.shuseynli.sales_project
.exception.dto.ExceptionResponse;
import az.shuseynli.sales_project
.exception.dto.ValidationErrorDto;
import az.shuseynli.sales_project
.exception.constants.ErrorCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

   private final MessageSource messageSource;


   @ExceptionHandler(value = CustomException.class)
   public ResponseEntity<ExceptionResponse> handleCustomException(CustomException e, WebRequest request) {

      String localizedMessage = messageSource.getMessage(
              e.getErrorCode().getMessage(),
              e.getArgs(),
              LocaleContextHolder.getLocale()
      );

      ExceptionResponse response = ExceptionResponse
              .builder()
              .status(e.getErrorCode().getHttpStatus().value())
              .code(e.getErrorCode().name())
              .message(localizedMessage)
              .timestamp(LocalDateTime.now().toString())
              .path(((ServletWebRequest) request).getRequest().getRequestURI())
              .build();

      return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(response);
   }

   @ExceptionHandler(value = MethodArgumentNotValidException.class)
   public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException e, WebRequest request) {

      List<ValidationErrorDto> validationErrors = e.getBindingResult().getFieldErrors().stream()
              .map(fieldError -> new ValidationErrorDto(
                      fieldError.getField(),
                      fieldError.getDefaultMessage()
              ))
              .collect(Collectors.toList());

      String localizedMessage = messageSource.getMessage(
              ErrorCodes.VALIDATION_ERROR.getMessage(),
              null,
              LocaleContextHolder.getLocale()
      );

      ExceptionResponse response = ExceptionResponse
              .builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .code(ErrorCodes.VALIDATION_ERROR.name())
              .message(localizedMessage)
              .timestamp(LocalDateTime.now().toString())
              .path(((ServletWebRequest) request).getRequest().getRequestURI())
              .handlers(validationErrors) // Xəta detallarını bura əlavə edirik
              .build();

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
   }

   @ExceptionHandler(value = Exception.class)
   public ResponseEntity<ExceptionResponse> handleGenericException(Exception e, WebRequest request) {

      String localizedMessage = messageSource.getMessage(
              ErrorCodes.INTERNAL_SERVER_ERROR.getMessage(),
              null,
              LocaleContextHolder.getLocale()
      );
      String path = ((ServletWebRequest) request).getRequest().getRequestURI();

      ExceptionResponse response = ExceptionResponse
              .builder()
              .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
              .code(ErrorCodes.INTERNAL_SERVER_ERROR.name())
              .message(localizedMessage)
              .timestamp(LocalDateTime.now().toString())
              .path(path)
              .build();

      log.error("Unexpected error occurred at path: {}", path, e);

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
   }



}
