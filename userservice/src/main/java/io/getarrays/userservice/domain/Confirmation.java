package io.getarrays.userservice.domain;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.AUTO;
import static jakarta.persistence.TemporalType.TIMESTAMP;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Junior RT
 * @version 1.0
 * @license Get Arrays, LLC (https://getarrays.io)
 * @since 6/24/2023
 */
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "confirmations")
public class Confirmation {
	@Id @GeneratedValue(strategy = AUTO)
	private Long id;
	private String token;
	@Temporal(TIMESTAMP)
	@CreatedDate
	private LocalDateTime createdDate;
	@OneToOne(targetEntity = User.class, fetch = EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;
	
	public Confirmation(User user) {
		this.user = user;
		this.createdDate = LocalDateTime.now();
		this.token = UUID.randomUUID().toString();
	}
}
