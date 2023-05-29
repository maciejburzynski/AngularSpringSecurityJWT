package pl.maltoza.maltozasecurityjwt.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.maltoza.maltozasecurityjwt.user.User;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthenticationManager authenticationManager;

    @PostMapping(path = "/auth")
    ResponseEntity auth(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken
                            (authRequest.getUsername(), authRequest.getPassword()));
            User user = (User) authenticate.getPrincipal();

            String[] permissions = user.getUserRole().getPermissions()
                    .stream()
                    .map(permission -> permission.getPermission()).toArray(String[]::new);

            Algorithm algorithm = Algorithm.HMAC256("Kluczyk-Byku");
            String token = JWT.create()
                    .withIssuer("maltoza-security-jwt")
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + (1 * 60 * 1000))) // 1 minute
                    .withArrayClaim("permissions", permissions)
                    .sign(algorithm);

            AuthResponse authResponse = new AuthResponse(user.getUsername(), token);
            return ResponseEntity.ok(authResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(403).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(501).build();
        } catch (JWTCreationException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
