package album.Advice;

import album.Common.Result.Result;
import album.Common.Result.ResultService;
import album.Exception.IllegalLocalException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResultService resultService;
    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        List<Result.FieldError> fieldErros = resultService.getFieldErrors(e.getBindingResult());
        return resultService.getFailResults(getMessage("invalidRequestBody.msg"), fieldErros);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result requestBodyBindException(HttpServletRequest request, BindException e) {
        List<Result.FieldError> fieldErros = resultService.getFieldErrors(e.getBindingResult());
        return resultService.getFailResults(getMessage("invalidRequestBody.msg"), fieldErros);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result requestBodyBindException(HttpServletRequest request, HttpMessageNotReadableException e) {
        return resultService.getFailResult(getMessage("invalidRequestBody.msg"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidPasswordException(HttpServletRequest request, IllegalArgumentException e) {
        return resultService.getFailResult(getMessage("illegalArgument.msg"));
    }

    @ExceptionHandler(SongNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result songNotFound(HttpServletRequest request, SongNotFoundException e) {
        return resultService.getFailResult(getMessage("songNotFound.msg"));
    }
    private String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}
