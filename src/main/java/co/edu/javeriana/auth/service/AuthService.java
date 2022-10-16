package co.edu.javeriana.auth.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.javeriana.auth.dto.User;
import co.edu.javeriana.auth.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User login(String username, String pwd) {

        String token = getJWTToken(username);
        User user = new User();
        user.setUsername(username);
        user.setPassword(pwd);
        user.setToken(token);

        if(userRepository.findById(username).isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username doesent exist");

        if(userRepository.findById(username).get().getPassword().equals(pwd)) {
            return user;
        }
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong login parameters");
    }


    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

}
