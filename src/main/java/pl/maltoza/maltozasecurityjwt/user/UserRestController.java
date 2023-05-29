package pl.maltoza.maltozasecurityjwt.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @GetMapping(path = "/hello-user")
    public String helloUser() {
        return "Hello User World";
    }

    @GetMapping(path = "/hello-admin")
    public String helloAdmin() {
        return "Hello Admin World";
    }
}
