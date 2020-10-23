package website.marcioheleno.ajax_api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import website.marcioheleno.ajax_api.dto.UserDto;
import website.marcioheleno.ajax_api.dto.UserInsertDto;
import website.marcioheleno.ajax_api.services.UserService;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserResources {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "firstName") String orderBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<UserDto> userDtoPage = userService.findAllPage(pageRequest);
        return ResponseEntity.ok().body(userDtoPage);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        return ResponseEntity.ok().body(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> insert(@RequestBody UserInsertDto userInsertDto) {
        UserDto userDto = userService.insert(userInsertDto);

        // TODO: verificar outra opção de setar o path
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto userDto) {
        userDto = userService.update(id, userDto);
        return ResponseEntity.ok().body(userDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
