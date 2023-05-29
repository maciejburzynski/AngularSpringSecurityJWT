package pl.maltoza.maltozasecurityjwt.auth;

import lombok.Data;

@Data
public class AuthRequest {
    String username;
    String password;
}
