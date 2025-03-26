package com.partnerd.repository.contactMethodRepository;

import com.partnerd.mongoRepository.domain.ContactMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMethodRepository extends JpaRepository<ContactMethod, Long> {
}
