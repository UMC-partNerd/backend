package com.partnerd.repository.contactMethodRepository;

import com.partnerd.domain.ContactMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMethodRepository extends JpaRepository<ContactMethod, Long> {
}
