package org.sliitprojectspring.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sliitprojectspring.property.model.Property;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property ,Long> {

    @Override
    void deleteById(Long aLong);

    Optional<Property> findById(String s);

    List<Property> getPropertiesByValueBetween(double minValue , double  maxValue);






     List<Property> getPropertiesByUserId( Long id) ;
    @Override
    List<Property> findAll();

}
