package ma.mundiapolis.hopital_web;

import ma.mundiapolis.hopital_web.entities.Patient;
import ma.mundiapolis.hopital_web.repositories.PatientRepository;
import ma.mundiapolis.hopital_web.security.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class HopitalWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HopitalWebApplication.class, args);
    }

    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {
            patientRepository.save(new Patient(null,"Mustapha", new Date(), false,312));
            patientRepository.save(new Patient(null,"Loubna", new Date(), true,321));
            patientRepository.save(new Patient(null,"Ichraq", new Date(), true,65));
            patientRepository.save(new Patient(null,"Sabrine", new Date(), false,32));
            patientRepository.save(new Patient(null,"Mohammed", new Date(), false,70));


            patientRepository.findAll().forEach(p->{
                System.out.println(p.getNom());
            });
        };
    }

    //@Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager){
        PasswordEncoder passwordEncoder = passwordEncoder();
        return args -> {
            //si il n'existe pas 3aadabach il l'ajoute
            UserDetails u1 = jdbcUserDetailsManager.loadUserByUsername("user11");
            if (u1 == null)
                jdbcUserDetailsManager.createUser(User.withUsername("user11").password(passwordEncoder.encode("1234")).roles("USER").build());

            UserDetails u2 = jdbcUserDetailsManager.loadUserByUsername("user22");
            if (u2 == null)
                jdbcUserDetailsManager.createUser(User.withUsername("user22").password(passwordEncoder.encode("1234")).roles("USER").build());

            UserDetails admin = jdbcUserDetailsManager.loadUserByUsername("admin2");
            if (admin == null)
                jdbcUserDetailsManager.createUser(User.withUsername("admin2").password(passwordEncoder.encode("1234")).roles("USER").build());

        };
    }


    //@Bean
    CommandLineRunner commandLineRunnerUserDetails(AccountService accountService){
        return args -> {
            accountService.addnewRole("USER");
            accountService.addnewRole("ADMIN");
            accountService.addnewUser("user1","1234","user1@gmail.com","1234");
            accountService.addnewUser("user2","1234","user2@gmail.com","1234");
            accountService.addnewUser("admin","1234","admin@gmail.com","1234");

            accountService.addRoleToUser("user1","USER");
            accountService.addRoleToUser("user2","USER");
            accountService.addRoleToUser("admin","ADMIN");
            accountService.addRoleToUser("admin","USER");


        };
    }

    //hasher le mot de passe
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
