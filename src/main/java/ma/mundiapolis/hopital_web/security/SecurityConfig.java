package ma.mundiapolis.hopital_web.security;

import lombok.AllArgsConstructor;
import ma.mundiapolis.hopital_web.security.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity

@EnableMethodSecurity(prePostEnabled = true)
// a la place de httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");

@AllArgsConstructor
public class SecurityConfig {

    //@Autowired
    private PasswordEncoder passwordEncoder;
    private UserDetailServiceImpl userDetailServiceImpl;



    //avec base de donnée
    //@Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }


    //@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("user2").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build()
        );


    }


    //nécessité authentification pour toutes les autres requêtesl
    //gestion des erreurs d'accès
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll(); //utilisation formulaire de connex perso et tjr revenir vers /
        httpSecurity.rememberMe(); //connectés même après la fermeture du navigateur

        httpSecurity.authorizeHttpRequests().requestMatchers("/webjars/**","/h2-console/**").permitAll();//autorisation accès sans authentification car on a eu un prob pour charger bootstrap
        //gerer les autorisations quand je commente user peut supp un patient avec url
        //httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");
        //httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();//autres requêtes nécessitent une authentification
        httpSecurity.exceptionHandling().accessDeniedPage("/notAuthorized");//Configure page en cas d'accès refusé
        httpSecurity.userDetailsService(userDetailServiceImpl);//service utilisé pour charger les info sur l'uti lors de l'authentification pour 3ieme methode
        return httpSecurity.build();
    }
}
