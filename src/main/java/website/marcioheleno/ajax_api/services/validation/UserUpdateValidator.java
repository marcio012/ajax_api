package website.marcioheleno.ajax_api.services.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;
import website.marcioheleno.ajax_api.dto.UserUpdateDto;
import website.marcioheleno.ajax_api.entities.User;
import website.marcioheleno.ajax_api.repositories.UserRepository;
import website.marcioheleno.ajax_api.resources.exceptions.FieldMessage;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDto> {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserUpdateValid userUpdateValid) {
    }

    @Override
    public boolean isValid(UserUpdateDto dto, ConstraintValidatorContext context) {

        @SuppressWarnings("unchecked")
        var uri = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long userId = Long.parseLong(uri.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        User user = repository.findUserByEmail(dto.getEmail());

        if (user != null && userId != user.getId())
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
