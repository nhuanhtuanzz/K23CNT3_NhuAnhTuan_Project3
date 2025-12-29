package prj3.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import prj3.model.NatUsers;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final NatUsersService usersService;

    public CustomUserDetailsService(NatUsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        NatUsers natUser = usersService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return new User(
                natUser.getNatEmail(),
                natUser.getNatPassword(), // Password phải được encode
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + natUser.getNatRole()))
        );
    }
}