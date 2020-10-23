package website.marcioheleno.ajax_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website.marcioheleno.ajax_api.dto.RoleDto;
import website.marcioheleno.ajax_api.dto.UserDto;
import website.marcioheleno.ajax_api.dto.UserInsertDto;
import website.marcioheleno.ajax_api.entities.Role;
import website.marcioheleno.ajax_api.entities.User;
import website.marcioheleno.ajax_api.repositories.RoleRepository;
import website.marcioheleno.ajax_api.repositories.UserRepository;
import website.marcioheleno.ajax_api.services.exceptions.DatabaseException;
import website.marcioheleno.ajax_api.services.exceptions.ResourceNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UserDto> findAllPage(PageRequest pageRequest) {
        Page<User> usersPage = userRepository.findAll(pageRequest);
        return usersPage.map(UserDto::new);
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User userEntity = optionalUser.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
            return new UserDto(userEntity);
    }

    @Transactional
    public UserDto insert(UserInsertDto userInsertDto) {
        User userEntity = new User();
        convertUserDtoToUserEntity(userInsertDto, userEntity);
        userEntity.setPassword(passwordEncoder.encode(userInsertDto.getPassword()));
        userEntity = userRepository.save(userEntity);
        return new UserDto(userEntity);
    }

    @Transactional
    public UserDto update(Long id, UserDto userDto) {
        try {
            User userEntity = userRepository.getOne(id);
            convertUserDtoToUserEntity(userDto, userEntity);
            userEntity = userRepository.save(userEntity);
            return new UserDto(userEntity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void convertUserDtoToUserEntity(UserDto userDto, User userEntity) {

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setEmail(userDto.getEmail());
        System.out.println(userDto.getRoles().toString());

        userEntity.getRoles().clear();

        for (RoleDto roleDto: userDto.getRoles()) {
            Role role = roleRepository.getOne(roleDto.getId());
            userEntity.getRoles().add(role);
        }
    }
}

