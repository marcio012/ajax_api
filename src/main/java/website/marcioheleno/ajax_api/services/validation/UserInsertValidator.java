package website.marcioheleno.ajax_api.services.validation;

import org.springframework.beans.factory.annotation.Autowired;
import website.marcioheleno.ajax_api.dto.UserInsertDto;
import website.marcioheleno.ajax_api.entities.User;
import website.marcioheleno.ajax_api.repositories.UserRepository;
import website.marcioheleno.ajax_api.resources.exceptions.FieldMessage;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDto> {

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserInsertValid constraint) {
    }

    @Override
    public boolean isValid(UserInsertDto obj, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();
        User user = repository.findUserByEmail(obj.getEmail());

        if (user != null)
            list.add(FieldMessage.builder()
                    .fieldName("email")
                    .message("Email já existe")
                    .build());

        for (FieldMessage e: list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
