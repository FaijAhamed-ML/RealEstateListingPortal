package org.sliitprojectspring.user.model;

import org.sliitprojectspring.inquire.model.Inquire;

import org.sliitprojectspring.booking.model.Booking;

import org.sliitprojectspring.property.model.Property;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table( name =  "users"
)
public class User{


    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long id  ;
     private String name ;
     private String role ;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column( unique = true)
     private String username ;

     @Column( unique = true)
      private String email ;
      private String password ;

      private String phoneNumber ;


      @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
      private List<Inquire> inquireList ;

      @OneToMany(mappedBy = "owner" , cascade = CascadeType.ALL)
      private List<Inquire> ownerInquireList ;

    public List<Inquire> getInquireList() {
        return inquireList;
    }

    public void setInquireList(List<Inquire> inquireList) {
        this.inquireList = inquireList;
    }

    @OneToMany( mappedBy = "user" , cascade = CascadeType.ALL)
      private List<Property> properties;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Booking> bookingList;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Booking> ownerBookingList;

    public List<Property> getProperties() {
        return properties;
    }

    public String getPhoneNumber() {
          return phoneNumber;
      }

      public void setPhoneNumber(String phoneNumber) {
          this.phoneNumber = phoneNumber;
      }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Inquire> getOwnerInquireList() {
        return ownerInquireList;
    }

    public void setOwnerInquireList(List<Inquire> ownerInquireList) {
        this.ownerInquireList = ownerInquireList;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public List<Booking> getOwnerBookingList() {
        return ownerBookingList;
    }

    public void setOwnerBookingList(List<Booking> ownerBookingList) {
        this.ownerBookingList = ownerBookingList;
    }
}