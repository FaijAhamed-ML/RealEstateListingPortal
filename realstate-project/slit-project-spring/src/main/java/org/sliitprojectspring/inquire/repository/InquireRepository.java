package org.sliitprojectspring.inquire.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sliitprojectspring.inquire.model.Inquire;


import java.util.*;
import java.util.Optional;

@Repository
public  interface
InquireRepository  extends JpaRepository<Inquire , Long> {

     List<Inquire > getInquiresByUserId( Long userId );


     List<Inquire> getInquiresByOwnerId( Long ownerId) ;



    List<Inquire> getInquireByPropertyId( Long propertyId);
    Optional<Inquire> getInquireByPropertyIdAndUserId( Long userId , Long PropertyId);







}
