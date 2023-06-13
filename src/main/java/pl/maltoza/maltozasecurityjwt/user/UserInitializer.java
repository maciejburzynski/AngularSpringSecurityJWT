package pl.maltoza.maltozasecurityjwt.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.maltoza.maltozasecurityjwt.email.EmailService;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;

@Component
@RequiredArgsConstructor
public class UserInitializer {

    private final EmailService emailService;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    void createUsers() throws MessagingException {
        User user = new User("Maciej", passwordEncoder.encode("test"), UserRole.ADMIN, true, true, true, true);
        User user1 = new User("Andrzej", passwordEncoder.encode("test"), UserRole.USER, true, true, true, true);

        userService.save(user);
        userService.save(user1);
    }
}
