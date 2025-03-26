package com.partnerd.repository.notificationRepository;

import com.partnerd.mongoRepository.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
