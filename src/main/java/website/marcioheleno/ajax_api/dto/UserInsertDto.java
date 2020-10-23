package website.marcioheleno.ajax_api.dto;

import lombok.Getter;

@Getter
public class UserInsertDto extends UserDto {

    private String password;

    public UserInsertDto() {
        super();
    }
}
