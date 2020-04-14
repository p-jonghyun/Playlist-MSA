package user.Advice;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import user.Common.Result.Result;
import user.Common.Result.ResultService;
import user.Exception.CUserNotFoundException;
import user.Exception.InvalidPasswordException;
import user.Exception.InvalidRoleException;
import user.Exception.UserAlreadyExistsException;

import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindException;

import java.util.List;

@RequiredArgsConstructor
@RestControllerAdvice
public class CommonExceptionAdvice {

    private final ResultService resultService;
    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected  Result handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
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

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result userNotFoundException(HttpServletRequest request, CUserNotFoundException e) {
        System.out.println("여기에들어옴");
        return resultService.getFailResult(getMessage("userNotFound.msg"));
    }

    @ExceptionHandler(InvalidRoleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidRoleException(HttpServletRequest request, InvalidRoleException e) {
        return resultService.getFailResult(getMessage("invalidRole.msg"));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result userAlreadyExistException(HttpServletRequest request, UserAlreadyExistsException e) {
        return resultService.getFailResult(getMessage("userAlreadyExist.msg"));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Result invalidPasswordException(HttpServletRequest request, InvalidPasswordException e) {
        return resultService.getFailResult(getMessage("invalidPassword.msg"));
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}

