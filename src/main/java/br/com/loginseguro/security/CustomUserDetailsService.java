package br.com.loginseguro.security;

import br.com.loginseguro.model.User;
import br.com.loginseguro.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;
    public CustomUserDetailsService(UserRepository repository){this.repository=repository;}
    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u=repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Credenciais inválidas."));
        String[] authorities=u.getRoles().stream().map(r -> "ROLE_"+r.name()).toArray(String[]::new);
        return org.springframework.security.core.userdetails.User.withUsername(u.getUsername()).password(u.getPassword()).authorities(authorities).disabled(!u.isEnabled()).build();
    }
}
