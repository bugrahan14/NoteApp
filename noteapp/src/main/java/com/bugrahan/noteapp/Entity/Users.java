package com.bugrahan.noteapp.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id ;

    @NotBlank(message = "Kullanıcı adı boş olamaz")
    @Size(min = 2, max = 50, message = "Kullanıcı adı 2-50 karakter arasında olmalıdır")
    @Column(name="username")
    private String username;

    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 6, max = 100, message = "Şifre en az 6 karakter olmalıdır")
    @Column(name="password")
    private String password;

    @NotBlank(message = "E-posta boş olamaz")
    @Email(message = "Geçerli bir e-posta adresi giriniz")
    @Column(name="email")
    private String email;

    @Size(max = 20, message = "Rol en fazla 20 karakter olabilir")
    @Column(name="role")
    private String role ;

    public Users(){

    }

    public Users(String username , String password , String email , String role){
        this.username = username ; 
        this.password = password ;
        this.email=email;
        this.role = role ;
    }


    public Long getId(){
        return id ;
    }

    public String getUsername(){
        return username ;
    }

    public void setUsername(String username){
        this.username = username ;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password ;
    }

    public String getEmail(){
        return email ;
    }

    public void setEmail(String email){
        this.email= email;
    }

    public String getRole(){
        return role ;
    }

    public void setRole(String role){
        this.role = role ;
    }

    @Override
    public String toString() {
        
        return "Users [id=" + id + ", username=" + username + ", email=" + email + ", role=" + role + "]";
    }
    
    

    

    






}
