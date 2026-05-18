package org.sliitprojectspring.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sliitprojectspring.admin.model.Admin;

public interface AdminRepository extends JpaRepository<Admin ,Long> {


}
