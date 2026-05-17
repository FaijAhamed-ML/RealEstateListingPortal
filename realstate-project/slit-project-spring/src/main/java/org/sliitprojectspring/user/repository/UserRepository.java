package org.sliitprojectspring.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sliitprojectspring.user.model.User;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User , Long> {

    @Override
    Optional<User> findById(Long s);

    @Override
    void deleteById(Long s);


    Optional<User> findByUsername(String username);
    java.util.List<User> findByRole(String role);














}
