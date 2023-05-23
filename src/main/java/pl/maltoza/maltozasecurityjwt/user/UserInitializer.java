package pl.maltoza.maltozasecurityjwt.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class UserInitializer {

    private final UserService userService;

    @PostConstruct
    void createUsers() {
        User user = new User("Maciej");
        User user1 = new User("Andrzej");

        userService.save(user);
        userService.save(user1);
    }
}
