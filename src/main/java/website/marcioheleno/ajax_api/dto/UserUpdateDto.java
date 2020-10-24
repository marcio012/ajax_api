package website.marcioheleno.ajax_api.dto;

import lombok.Getter;
import website.marcioheleno.ajax_api.services.validation.UserInsertValid;
import website.marcioheleno.ajax_api.services.validation.UserUpdateValid;

@Getter
@UserUpdateValid
public class UserUpdateDto extends UserDto {

}
