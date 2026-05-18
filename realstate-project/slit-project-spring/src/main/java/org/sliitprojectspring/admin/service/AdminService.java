package org.sliitprojectspring.admin.service;


import org.springframework.stereotype.Service;
import org.sliitprojectspring.user.dto.UserCreatDto;
import org.sliitprojectspring.user.dto.UserResponseDto;
import org.sliitprojectspring.property.model.Property;
import org.sliitprojectspring.user.model.User;
import org.sliitprojectspring.property.repository.PropertyRepository;
import org.sliitprojectspring.user.repository.UserRepository;
import org.sliitprojectspring.common.utils.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;

    private final PropertyRepository propertyRepository;






    public AdminService( UserRepository userRepository, PropertyRepository propertyRepository   ) {
        this.userRepository
                =userRepository;

        this.propertyRepository = propertyRepository;


    }

     public  void deletePropertyById(  Long id) {


         Property property = propertyRepository.findById(id).orElseThrow(
                 () -> new RuntimeException("Property Not  found")
         );



                 propertyRepository.delete(property);
     }

    public List<UserResponseDto> getAllUser( ) {


        List<User> userList = userRepository.findAll();


        return  userList.stream().map(   user ->
                 new UserResponseDto( user.getId() , user.getName()   ,user.getRole(), user.getEmail() , user.getPhoneNumber())

        ).collect(Collectors.toList()) ;

    }




    public UserResponseDto createUser(UserCreatDto dto) {
         User user = new User();




         user.setEmail(dto.email());
         user.setName( dto.name());
         user.setRole(dto.role());
         user.setPhoneNumber( dto.phoneNumber());

         user.setUsername( dto.username());


         user.setPassword(  PasswordEncoder.hashPassword(dto.password()));




         User createdUser  = userRepository.save(user);


         return new UserResponseDto( createdUser.getId() , createdUser.getName(), createdUser
                 .getRole() ,createdUser.getEmail()  ,
                 createdUser.getPhoneNumber()

    );


    }


     public void removeUserById(Long  userId  ) {


         User user = userRepository.findById(userId)
                 .orElseThrow(() -> new RuntimeException("User not found"));

         userRepository.delete(user);





     }



}
