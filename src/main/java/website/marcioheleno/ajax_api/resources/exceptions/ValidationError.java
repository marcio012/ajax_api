package website.marcioheleno.ajax_api.resources.exceptions;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationError extends StandartError{

    private List<FieldMessage> errors = new ArrayList<>();

    public void addError(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }

}
