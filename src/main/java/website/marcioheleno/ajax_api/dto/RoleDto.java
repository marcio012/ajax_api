package website.marcioheleno.ajax_api.dto;

import lombok.*;
import website.marcioheleno.ajax_api.entities.Role;

@Getter
@Setter
public class RoleDto {

    private Long id;
    private String authority;

    public RoleDto() {
    }

    public RoleDto(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public RoleDto(Role role) {
        id = role.getId();
        authority = role.getAuthority();
    }
}
