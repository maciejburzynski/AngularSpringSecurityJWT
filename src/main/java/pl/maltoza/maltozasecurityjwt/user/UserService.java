package pl.maltoza.maltozasecurityjwt.user;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.maltoza.maltozasecurityjwt.email.EmailService;
import pl.maltoza.maltozasecurityjwt.exception.ActivationCodeNotFoundException;
import pl.maltoza.maltozasecurityjwt.user.activationcode.ActivationCodeService;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ActivationCodeService activationCodeService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserService(UserRepository userRepository,
                       ActivationCodeService activationCodeService,
                       @Lazy PasswordEncoder passwordEncoder,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.activationCodeService = activationCodeService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Lazy
    public void createUser(UserCreationRequest userCreationRequest) throws MessagingException {
        User user = new User(
                userCreationRequest.getUsername(),
                userCreationRequest.getEmail(),
                passwordEncoder.encode(userCreationRequest.getPassword()));
        user.setActivationCode(activationCodeService.generateActivationCode());
        emailService.sendMail(userCreationRequest.getEmail(),
                "Witaj w Malto! Aktywuj konto",
                "Twój kod aktywacyjny to: " + user.getActivationCode(),
                null,
                false);
        save(user);

    }

    public Integer findActivationCodeByUserId(Long userId) {
        return userRepository.findActivationCodeByUserId(userId)
                .orElseThrow(() -> new ActivationCodeNotFoundException("Activation code not found")).getActivationCode();
    }
}
