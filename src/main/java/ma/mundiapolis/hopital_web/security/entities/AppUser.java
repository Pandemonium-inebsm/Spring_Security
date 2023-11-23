package ma.mundiapolis.hopital_web.security.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AppUser {
    @Id
    private String userId;
    //2 util pas le meme nom
    @Column(unique = true)
    private String username;
    private String password;
    private String email;

    //si je mets lazy ne charge la derniere qu'au besoin mais avec eager il charche tout
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> roles;
}
