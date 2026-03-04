package com.bugrahan.noteapp.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="notes")
public class Notes {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotBlank(message = "Başlık boş olamaz")
    @Size(max = 200, message = "Başlık en fazla 200 karakter olabilir")
    @Column(name="title")
    private String title;

    @Size(max = 10000, message = "İçerik en fazla 10000 karakter olabilir")
    @Column(name="content")
    private String content;

    @NotNull(message = "Not bir kullanıcıya ait olmalıdır")
    @ManyToOne
    @JoinColumn(name="user_id")
    private Users users;

    public Notes(){


    }

    public Notes(String title , String content , Users users){
        this.title = title ;
        this.content = content ; 
        this.users = users ; 
    }

    public void setId(Long id){
        this.id = id ;
    }

    public Long getId(){
        return id ;

    }
    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title ;
    }

    public String getContent(){
        return content ;
    
    }

    public void setContent(String content){
        this.content = content ;
    }

    public Users getUsers(){
        return users;
    }

    public void setUsers(Users users){
        this.users = users ;
    }

    @Override
    public String toString() {
        
        return "Notes [id=" + id + ", title=" + title + ", content=" + content + ", users=" + users + "]";
    }

    

























}
