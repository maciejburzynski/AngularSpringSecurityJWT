package pl.maltoza.maltozasecurityjwt.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @GetMapping
    public String hello() {
        return "Hello World";
    }
}
