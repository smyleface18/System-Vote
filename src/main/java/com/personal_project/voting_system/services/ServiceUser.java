package com.personal_project.voting_system.services;

import com.personal_project.voting_system.dtos.ErrorApp;
import com.personal_project.voting_system.dtos.Role;
import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.exceptions.ObjectNotFoundException;
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
                        .body(new ErrorApp("Ese nombre de usuario ya esta registrado por favor ingrese otro nombre",
                                HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date()));
            }
            if (iRepositoryUser.checkExistingEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .body(new ErrorApp("Ese correo ya esta registrado por favor ingrese otro correo",
                                HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date()));
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            iRepositoryUser.addUser(user);
        }
        catch(ObjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorApp("no se pudo encontrar el role",HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
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
