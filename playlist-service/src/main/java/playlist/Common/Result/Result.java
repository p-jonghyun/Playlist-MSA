package playlist.Common.Result;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private T data;
    private boolean success;
    private String msg;
    private List<FieldError> errors = new ArrayList<>();

    @Builder
    public Result(T data, String msg, boolean success, List<FieldError> errors) {
        this.data = data;
        this.msg = msg;
        this.success = success;
        this.errors = initErrors(errors);
    }

    private List<FieldError> initErrors(List<FieldError> errors) {
        return (errors == null) ? new ArrayList<>() : errors;
    }

    @Getter
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        @Builder
        public FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }

}