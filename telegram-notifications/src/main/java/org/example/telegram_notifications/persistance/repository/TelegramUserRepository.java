package org.example.telegram_notifications.persistance.repository;

import org.example.telegram_notifications.persistance.entity.TelegramUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelegramUserRepository extends CrudRepository<TelegramUser, Long> {
    Optional<TelegramUser> findByUsername(String username);
}
