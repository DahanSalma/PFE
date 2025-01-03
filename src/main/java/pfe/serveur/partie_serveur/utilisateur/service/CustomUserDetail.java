package pfe.serveur.partie_serveur.utilisateur.service;

import java.util.Collection;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pfe.serveur.partie_serveur.utilisateur.model.User;


@Service
public class CustomUserDetail implements UserDetails {

    private  User user;

    public CustomUserDetail(User user) {
        this.user = user;
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return List.of(() -> user.getRole());
    }

    public String getEmail(){
        return user.getEmail();
    }
    public String getFirstname(){
        return user.getFirstname();
    }
    public String getLastName(){
        return user.getLastname();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}