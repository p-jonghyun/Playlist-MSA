package playlist.Advice;


import com.netflix.ribbon.proxy.annotation.Http;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import playlist.Common.Result.Result;
import playlist.Common.Result.ResultService;
import playlist.Exception.*;

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

//    @ExceptionHandler(IllegalArgumentException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    protected Result invalidPasswordException(HttpServletRequest request, IllegalArgumentException e) {
//        return resultService.getFailResult(getMessage("illegalArgument.msg"));
//    }

    @ExceptionHandler(SongNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result songNotFound(HttpServletRequest request, SongNotFoundException e) {
        return resultService.getFailResult(getMessage("songNotFound.msg"));
    }


    @ExceptionHandler(PlaylistNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result playlistNotFound(HttpServletRequest request, PlaylistNotFoundException e) {
        return resultService.getFailResult(getMessage("playlistNotFound.msg"));
    }

    @ExceptionHandler(PlaylistMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result playlistnotMatch(HttpServletRequest request, PlaylistMatchException e) {
        return resultService.getFailResult(getMessage("playlistNotFound.msg"));
    }
    @ExceptionHandler(PlayListTitleDuplicateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result playlistTitleDuplicate(HttpServletRequest request, PlayListTitleDuplicateException e) {
        return resultService.getFailResult(getMessage("playlistTitleduplication.msg"));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result playlistTitleDuplicate(HttpServletRequest request, ConstraintViolationException e) {
        return resultService.getFailResult(getMessage("playListSongDuplicate.msg"));
    }

    @ExceptionHandler(IllegalLocalException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result illegalLocalException(HttpServletRequest request, ConstraintViolationException e) {
        return resultService.getFailResult(getMessage("illegalLocalException.msg"));
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}
