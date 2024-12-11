package com.personal_project.voting_system.services;

import com.personal_project.voting_system.dtos.Information;
import com.personal_project.voting_system.dtos.ResponseApp;
import com.personal_project.voting_system.dtos.Role;
import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.exceptions.ObjectNotFoundException;
import com.personal_project.voting_system.exceptions.OccupiedAttributes;
import com.personal_project.voting_system.respository.IRepositoryRole;
import com.personal_project.voting_system.respository.IRepositoryUser;
import com.personal_project.voting_system.security.TokenData;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ServiceUser {

    private final IRepositoryUser iRepositoryUser;
    private final IRepositoryRole iRepositoryRole;
    private final PasswordEncoder passwordEncoder;
    private final TokenData tokenData;

    @Autowired
    public ServiceUser(IRepositoryUser iRepositoryUser, IRepositoryRole iRepositoryRole, PasswordEncoder passwordEncoder, TokenData tokenData) {
        this.iRepositoryUser = iRepositoryUser;
        this.iRepositoryRole = iRepositoryRole;
        this.passwordEncoder = passwordEncoder;
        this.tokenData = tokenData;
    }


    public User getUser(String name){
        return iRepositoryUser.findByNameOrEmailUser(name);
    }

    public User getUserById(Long id){
        return iRepositoryUser.findById(id);
    }

    @Transactional
    public ResponseEntity<?> addUser(User user) throws ObjectNotFoundException {
        try {
            Role roleUser = iRepositoryRole.findByName("ROLE_USER");
            List<Role> roles = new ArrayList<>();
            if (roleUser != null){
                roles.add(roleUser);
            }
            else {
                throw new ObjectNotFoundException("ROLE_USER not found");
            }
            if(user.isAdmin()){
                Role roleAdmin = iRepositoryRole.findByName("ROLE_ADMIN");
                if (roleAdmin != null){
                    roles.add(roleAdmin);
                }
            }
            user.setRoles(roles);
            if(iRepositoryUser.checkExistingName(user.getName()) ){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .body(new OccupiedAttributes("error when trying to register",
                        "This email is already named please enter another name, you can add numbers to it "));
            }
            if (iRepositoryUser.checkExistingEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .body(new OccupiedAttributes("error when trying to register",
                                "This email is already email please enter another email, enter another email "));
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            iRepositoryUser.addUser(user);
        }
        catch(ObjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseApp("no se pudo encontrar el role",HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new Information("Successful registration! verify your email address","If you don't find the email in your inbox, be sure to check your spam or junk mail folder. If you are still having problems, please do not hesitate to contact us.","success"));
    }


    @Transactional
    public Map<String,String > checkEmail(String token) throws JwtException, IllegalArgumentException{
        Map<String,String > body = new LinkedHashMap<>();
        try {
            String emailUser = (String) tokenData.Readclaims(token).get("email");
            User user = iRepositoryUser.findByNameOrEmailUser(emailUser);
            if (user == null) {
                body.put("title","Oe Oe identificate");
                body.put("subtitle","Este correo no lo conozco.");
                body.put("solution","Lamentamos informarle que ha recibido este correo por error. Es posible que uno de nuestros usuarios haya cometido un error al escribir su dirección de correo electrónico y, como resultado, este mensaje le ha llegado a usted inadvertidamente.\n" +
                        "\n" +
                        "Le pedimos disculpas por cualquier inconveniente que esto pueda haberle causado.");
                return body;
            }
            user.setEnabled(true);
            iRepositoryUser.addUser(user);


        }
       catch (JwtException | IllegalArgumentException ignored){
                body.put("title", "Oh 🤔, ha ocurrido algo que no esperaba, hay un error con tu token por lo tanto." +
                        "No puedo verficar tu cuenta por ahora 😞");
                body.put("subtitle","Oye, pero no te preocupes; lo vamos a solucionar juntos.");
                body.put("solution","Ingresa de nuevo a la web principal y solicita que te reenvie el email de confirmación.");
            return body;
       }
        body.put("title", "Bienvenido a Voto Libre. Tu cuenta ha sido activada. ✅");
        body.put("subtitle","Estamos felices de tenerte como uno de nuestros usuarios.");
        body.put("solution","Ya puedes iniciar sesión y obtener todos los privilegios de los usuarios.");
        return body;
    }
}
