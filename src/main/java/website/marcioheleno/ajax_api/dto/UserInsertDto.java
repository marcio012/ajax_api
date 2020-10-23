package website.marcioheleno.ajax_api.dto;

import lombok.Getter;
import website.marcioheleno.ajax_api.services.validation.UserInsertValid;

@Getter
@UserInsertValid
public class UserInsertDto extends UserDto {

    private String password;

    public UserInsertDto() {
        super();
    }
}
