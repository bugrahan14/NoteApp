package com.bugrahan.noteapp.Entity;

import jakarta.persistence.*;
@Entity
@Table(name="notes")
public class Notes {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

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

    

























}
