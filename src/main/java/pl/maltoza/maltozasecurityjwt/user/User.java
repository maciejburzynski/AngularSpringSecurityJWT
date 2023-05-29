package pl.maltoza.maltozasecurityjwt.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String username;
    String password;
    @Enumerated(EnumType.STRING)
    UserRole userRole;
    boolean isAccountNonExpired;
    boolean isAccountNonLocked;
    boolean isCredentialsNonExpired;
    boolean isEnabled;

    public User(String username, String password, UserRole userRole, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRole.getGrantedAuthorities();
    }

}
