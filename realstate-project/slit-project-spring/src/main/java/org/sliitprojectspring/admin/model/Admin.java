package org.sliitprojectspring.admin.model;


import jakarta.persistence.*;

@Entity
@Table(   name = "admin")


public class Admin{



     @Id
     @GeneratedValue( strategy = GenerationType.IDENTITY)
     private Long id  ;
     private String name ;
     private String email ;
     private String phoneNumber ;

     private String role ;


}