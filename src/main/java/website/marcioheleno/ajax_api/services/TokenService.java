package website.marcioheleno.ajax_api.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import website.marcioheleno.ajax_api.entities.User;

import java.util.Date;


@Service
public class TokenService {

    @Value("${jwt.duration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    public String createToken(Authentication authenticate) {
        User user = (User) authenticate.getPrincipal();
        Date now  = new Date();
        Date dateExpiration = new Date(now.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("Backend Ajax")
                .setSubject(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(dateExpiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
