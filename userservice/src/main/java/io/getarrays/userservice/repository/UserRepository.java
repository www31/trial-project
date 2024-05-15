package io.getarrays.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.getarrays.userservice.domain.User;

/**
 * @author Junior RT
 * @version 1.0
 * @license Get Arrays, LLC (https://getarrays.io)
 * @since 6/24/2023
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmailIgnoreCase(String email);
	Boolean existsByEmail(String email);
}
