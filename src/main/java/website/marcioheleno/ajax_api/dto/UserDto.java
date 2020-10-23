package website.marcioheleno.ajax_api.dto;

import lombok.Getter;
import lombok.Setter;
import website.marcioheleno.ajax_api.entities.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {

    private Long id;

    @Size(min = 3, max = 30, message = "FirstName deve ter entre 3 e 30 caracteres")
    @NotBlank(message = "O Campo firstName é obrigatório")
    private String firstName;

    @NotBlank(message = "O Campo lastName é obrigatório")
    private String lastName;

    @Email(message = "Digitar um email válido")
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
