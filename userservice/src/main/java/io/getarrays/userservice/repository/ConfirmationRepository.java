package io.getarrays.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.getarrays.userservice.domain.Confirmation;

/**
 * @author Junior RT
 * @version 1.0
 * @license Get Arrays, LLC (https://getarrays.io)
 * @since 6/24/2023
 */
@Repository
public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {
	Confirmation findByToken(String token);
}
