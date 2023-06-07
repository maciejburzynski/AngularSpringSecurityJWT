package pl.maltoza.maltozasecurityjwt.user;

import lombok.Data;

@Data
public class UserCreationRequest {
    private String username;
    private String email;
    private String password;
}
