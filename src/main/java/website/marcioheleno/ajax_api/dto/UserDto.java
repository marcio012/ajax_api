package website.marcioheleno.ajax_api.dto;

import lombok.*;
import website.marcioheleno.ajax_api.entities.User;

import javax.persistence.Column;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    @Getter
    Set<RoleDto> roles = new HashSet<>();

    public UserDto() {}

    public UserDto(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UserDto(User userEntity) {
        id = userEntity.getId();
        firstName = userEntity.getFirstName();
        lastName = userEntity.getLastName();
        email = userEntity.getEmail();
        userEntity.getRoles().forEach(role -> this.roles.add(new RoleDto(role)));
    }
}
