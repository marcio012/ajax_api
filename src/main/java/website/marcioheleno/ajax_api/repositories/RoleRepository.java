package website.marcioheleno.ajax_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import website.marcioheleno.ajax_api.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
