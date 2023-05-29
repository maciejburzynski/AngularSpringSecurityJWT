package pl.maltoza.maltozasecurityjwt.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getUsernamePasswordAuthenticationToken(token);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }


    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256("Kluczyk-Byku"); //use more secure key
        JWTVerifier verifier = JWT.require(algorithm)
                .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token.substring(7));
        String[] permissions = jwt.getClaim("permissions").asArray(String.class);
        List<SimpleGrantedAuthority> permissionsList = Stream.of(permissions)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(jwt.getSubject(), null, permissionsList);
    }

}

