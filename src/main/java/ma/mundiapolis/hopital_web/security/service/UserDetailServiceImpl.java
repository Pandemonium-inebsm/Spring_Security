package ma.mundiapolis.hopital_web.security.service;

import lombok.AllArgsConstructor;
import ma.mundiapolis.hopital_web.security.entities.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service //classe comme un service Spring
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private AccountService accountService;
    @Override //méthode suivante doit remplacer une méthode héritée de l'interface UserDetailsService
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = accountService.loadUserByUsername(username); //récupére objet AppUser en fonction du nom d'utilisateur
        if (appUser==null) throw new UsernameNotFoundException(String.format("User %s Not found", username));

        // Récupère rôles de l'uti sous forme de tableau de chaînes à partir de l'objet appUser
        String[] roles = appUser.getRoles().stream().map(u -> u.getRole()).toArray(String[]::new);
        UserDetails userDetails = User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(roles)
                .build();
        return userDetails;
    }
}
