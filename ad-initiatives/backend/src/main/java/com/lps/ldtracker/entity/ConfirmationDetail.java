package com.lps.ldtracker.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "CONFIRMATION_DTL")
public class ConfirmationDetail {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	@Column(name="TOKEN")
	private String token;
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@Column(name="CREATED_DATE")
	private Timestamp createdDate;
	@OneToOne(targetEntity = UserDtl.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "USER_ID",
		foreignKey = @ForeignKey(name = "FK_CD_UD", value = ConstraintMode.CONSTRAINT))
	private UserDtl user;
	
	public ConfirmationDetail(UserDtl user) {
		this.user = user;
		this.createdDate = Timestamp.valueOf(LocalDateTime.now());
		this.token = UUID.randomUUID().toString();
	}
}
