package pl.maltoza.maltozasecurityjwt.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final IUserRepository iUserRepository;

    public void save(User user) {
        iUserRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return iUserRepository.findByUsername(username);
    }

    public Optional<User> findActivationCodeByUserId(Long userId) {
        return iUserRepository.findById(userId);
    }
}
