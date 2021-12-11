package com.totwgforum.gforum.config.auth;

import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User entity = userRepository.findByEmail(email);

        if (entity != null) {
            return new PrincipalDetails(entity);
        }

        return null;
    }
}
