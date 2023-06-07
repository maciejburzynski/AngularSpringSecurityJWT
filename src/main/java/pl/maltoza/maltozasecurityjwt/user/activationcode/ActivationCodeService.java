package pl.maltoza.maltozasecurityjwt.user.activationcode;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ActivationCodeService {

    public Integer generateActivationCode(){
        Random random = new Random();
        return random.nextInt(10000);
    }
}
