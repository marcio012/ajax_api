package website.marcioheleno.ajax_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import website.marcioheleno.ajax_api.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
