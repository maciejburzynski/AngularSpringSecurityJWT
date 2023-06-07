package pl.maltoza.maltozasecurityjwt.user;

import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.maltoza.maltozasecurityjwt.auth.AuthService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping(path = "/hello-user")
    public String helloUser() {
        return "Hello User World";
    }

    @GetMapping(path = "/hello-admin")
    public String helloAdmin() {
        return "Hello Admin World";
    }

    @PostMapping(path = "register/users")
    ResponseEntity register(@RequestBody UserCreationRequest userCreationRequest,
                            HttpServletResponse httpServletResponse) {
        try {
            userService.createUser(userCreationRequest);
            return ResponseEntity
                    .status(200)
                    .body(authService.getTokenResponse(userCreationRequest, httpServletResponse));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(403).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(501).build();
        } catch (JWTCreationException e) {
            return ResponseEntity.status(500).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
