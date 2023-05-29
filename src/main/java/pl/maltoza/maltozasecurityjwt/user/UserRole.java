package pl.maltoza.maltozasecurityjwt.user;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.maltoza.maltozasecurityjwt.user.UserPermission.ADMIN_READ;
import static pl.maltoza.maltozasecurityjwt.user.UserPermission.USER_READ;


@Getter
public enum UserRole {
    ADMIN(Sets.newHashSet(ADMIN_READ,USER_READ)),
    USER(Sets.newHashSet(USER_READ));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public List<SimpleGrantedAuthority> getGrantedAuthorities() {
        List<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissions;
    }
}
