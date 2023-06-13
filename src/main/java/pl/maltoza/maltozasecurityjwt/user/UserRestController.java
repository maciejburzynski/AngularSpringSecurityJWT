package pl.maltoza.maltozasecurityjwt.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.maltoza.maltozasecurityjwt.auth.AuthService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@Slf4j
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


    @PostMapping(path = "/registration/users")
    ResponseEntity register(@RequestBody UserCreationRequest userCreationRequest,
                            HttpServletResponse httpServletResponse) {
        try {
            userService.createUser(userCreationRequest);
            log.info("User to register: {}", userCreationRequest);
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

    @PostMapping(path = "/activation/users")
    ResponseEntity activate(@RequestBody UserActivationRequest userCreationRequest,
                            HttpServletRequest httpServletRequest) {
        String token = getCookieValue(httpServletRequest, "auth-cookie-malto");
        Algorithm algorithm = Algorithm.HMAC256("Kluczyk-Byku"); //use more secure key
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        Long userId = Long.parseLong(jwt.getSubject());

        Integer activationCode = userService.findActivationCodeByUserId(userId);

//        userService.activateUser(userId);
        return ResponseEntity
                .status(200)
                .build();

    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}
