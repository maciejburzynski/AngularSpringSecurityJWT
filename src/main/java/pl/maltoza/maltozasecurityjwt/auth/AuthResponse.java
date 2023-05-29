package pl.maltoza.maltozasecurityjwt.auth;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    String username;
    String token;
}
