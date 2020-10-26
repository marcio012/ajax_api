package website.marcioheleno.ajax_api.resources;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import website.marcioheleno.ajax_api.dto.LoginFormDto;
import website.marcioheleno.ajax_api.dto.TokenDto;
import website.marcioheleno.ajax_api.services.TokenService;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenServices;

    @PostMapping
    public ResponseEntity<TokenDto> autentication(@RequestBody @Valid LoginFormDto form) {
        UsernamePasswordAuthenticationToken login = form.convert();

        try {
            Authentication authenticate = authenticationManager.authenticate(login);
            String token = tokenServices.createToken(authenticate);
            log.info("Token >>> " + token);
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        }catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }



    }
}
