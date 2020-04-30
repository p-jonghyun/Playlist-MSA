package notification.Common.Result;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultService {

    public Result getFailResult(String msg) {
        System.out.println("여기도?");
        return Result.builder()
                .msg(msg)
                .success(false)
                .build();
    }

    public Result getFailResults(String msg, List<Result.FieldError> errors) {
        return Result.builder()
                .msg(msg)
                .success(false)
                .errors(errors)
                .build();
    }

    public <T> Result<T> getSuccessResult(T data) {

        Result result =  Result.builder()
                .data(data)
                .success(true)
                .msg("성공했습니다")
                .build();

        return result;
    }

    public List<Result.FieldError> getFieldErrors(BindingResult bindingResult) {
        final List<FieldError> errors = bindingResult.getFieldErrors();
        return errors.parallelStream()
                .map(error -> Result.FieldError.builder()
                        .reason(error.getDefaultMessage())
                        .field(error.getField())
                        .value((String) error.getRejectedValue())
                        .build())
                .collect(Collectors.toList());
    }
}
