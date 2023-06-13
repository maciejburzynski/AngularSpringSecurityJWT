package pl.maltoza.maltozasecurityjwt.user.activationcode;

import org.springframework.stereotype.Service;

import java.util.Random;

import static java.lang.String.*;

@Service
public class ActivationCodeService {

    public Integer generateActivationCode(){
        Random random = new Random();
        return Integer.valueOf(format("%04d%n", random.nextInt(10000)));
    }
}
