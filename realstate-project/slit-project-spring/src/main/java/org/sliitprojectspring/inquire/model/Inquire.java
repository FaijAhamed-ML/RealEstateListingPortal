package org.sliitprojectspring.inquire.model;

import org.sliitprojectspring.user.model.User;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Table( name = "inquire")
@Entity

public class Inquire

{
    @OneToMany(mappedBy = "inquire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InquireMessage> messages = new ArrayList<>();

    public List<InquireMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<InquireMessage> messages) {
        this.messages = messages;
    }


    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id   ;
    private String  description  ;

    @ManyToOne

    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    private String contactNumber ;
    private String name ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    private String propertyId ;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private LocalDateTime createdAt ;

    private LocalDateTime updateAt  ;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }


    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    private String reply;
    private LocalDateTime repliedAt;

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public LocalDateTime getRepliedAt() {
        return repliedAt;
    }

    public void setRepliedAt(LocalDateTime repliedAt) {
        this.repliedAt = repliedAt;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    private boolean isRead = false;

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
