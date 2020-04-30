package notification.Advice;

import lombok.RequiredArgsConstructor;
import notification.Common.Result.ResultService;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResultService resultService;
    private final MessageSource messageSource;

}
