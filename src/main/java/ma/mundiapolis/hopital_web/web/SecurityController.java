package ma.mundiapolis.hopital_web.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class SecurityController {
    //pour page avec acces non autorisé
    @GetMapping(path = "/notAuthorized")
    public String notAuthorized(){
        return "notAuthorized";
    }

    //pour page login perso
    @GetMapping(path = "/login")
    public String login(){
        return "login";
    }
}
