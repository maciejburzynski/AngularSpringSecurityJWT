package pl.maltoza.maltozasecurityjwt.auth;

import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.maltoza.maltozasecurityjwt.user.UserCreationRequest;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @PostMapping(path = "/login/users")
    ResponseEntity authenticate(@RequestBody UserCreationRequest userCreationRequest, HttpServletResponse httpServletResponse) {
        try {
            return ResponseEntity.ok(authService.getTokenResponse(userCreationRequest, httpServletResponse));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(403).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(501).build();
        } catch (JWTCreationException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
