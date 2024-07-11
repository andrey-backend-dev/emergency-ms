package org.example.emergency.repository;

import org.example.emergency.entity.Caller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CallerRepository extends CrudRepository<Caller, Long> {
    Optional<Caller> findByUsername(String username);
    void deleteByUsername(String username);
}
