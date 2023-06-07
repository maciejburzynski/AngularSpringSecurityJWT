package pl.maltoza.maltozasecurityjwt.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import pl.maltoza.maltozasecurityjwt.user.User;
import pl.maltoza.maltozasecurityjwt.user.UserCreationRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    public AuthResponse getTokenResponse(@RequestBody UserCreationRequest userCreationRequest,
                                         HttpServletResponse httpServletResponse) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (userCreationRequest.getUsername(), userCreationRequest.getPassword()));
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

        httpServletResponse.addCookie(new Cookie("auth-cookie-malto", token));
        return new AuthResponse(user.getUsername(), token);
    }
}

