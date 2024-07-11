package org.example.emergency.repository;

import org.example.emergency.entity.EmergencyCall;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyCallRepository extends CrudRepository<EmergencyCall, Long> {
}
