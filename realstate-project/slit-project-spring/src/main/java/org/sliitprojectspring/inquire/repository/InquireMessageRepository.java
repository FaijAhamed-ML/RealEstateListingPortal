package org.sliitprojectspring.inquire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sliitprojectspring.inquire.model.InquireMessage;

public interface InquireMessageRepository extends JpaRepository<InquireMessage, Long> {
}
