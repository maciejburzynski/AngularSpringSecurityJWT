package pl.maltoza.maltozasecurityjwt.user;

import lombok.Data;

@Data
public class UserActivationRequest {

    private Integer activationCode;
}
