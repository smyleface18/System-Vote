package com.personal_project.voting_system.services;

import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.respository.RepositoryUserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private RepositoryUserImpl repositoryUser;

    @Autowired
    public JpaUserDetailsService(RepositoryUserImpl repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
         User user =  repositoryUser.findByNameUser(name);
         if(user == null){
             throw new UsernameNotFoundException(String.format("El usuario %s no existe en el sistemas",name));
         }


        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getName(),user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities);
    }
}
