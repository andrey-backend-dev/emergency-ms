package org.example.emergency.repository;

import org.example.emergency.entity.Receiver;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiverRepository extends CrudRepository<Receiver, Long> {

    Optional<Receiver> findByName(String name);

    Optional<Receiver> findOneByCallerIdAndName(long id, String name);

    @Query(value = "SELECT * FROM receiver r WHERE (name = :name OR email = :email) AND caller_id = :callerId", nativeQuery = true)
    Optional<Receiver> findCallersReceiverWithEmail(@Param("name") String name,
                                           @Param("email") String email,
                                           @Param("callerId") long callerId);

    @Query(value = "SELECT * FROM receiver r WHERE (name = :name OR telegram_id = :tgId) AND caller_id = :callerId", nativeQuery = true)
    Optional<Receiver> findCallersReceiverWithTelegram(@Param("name") String name,
                                                    @Param("tgId") long telegramId,
                                                    @Param("callerId") long callerId);

    @Query(value = "SELECT * FROM receiver r WHERE (name = :name OR email = :email OR telegram_id = :tgId) AND caller_id = :callerId", nativeQuery = true)
    Optional<Receiver> findCallersReceiverWithEmailAndTelegram(@Param("name") String name,
                                                    @Param("email") String email,
                                                    @Param("tgId") long telegramId,
                                                    @Param("callerId") long callerId);

    List<Receiver> findByCallerId(long id);
}
