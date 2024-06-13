package com.lps.ldtracker.entity;

import static com.lps.ldtracker.service.StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER;
import static com.lps.ldtracker.service.StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER;
import static org.hibernate.id.OptimizableGenerator.INCREMENT_PARAM;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "USER_DTL")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtl implements UserDetails{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_DTL")
    @GenericGenerator(
        name = "SEQ_USER_DTL", 
        strategy = "com.lps.ldtracker.service.StringPrefixedSequenceIdGenerator", 
        parameters = {
            @Parameter(name = INCREMENT_PARAM, value = "1"),
            @Parameter(name = VALUE_PREFIX_PARAMETER, value = "04-2024"),
            @Parameter(name = NUMBER_FORMAT_PARAMETER, value = "%09d") })
	@Column(name = "USER_ID", length = 100)
	private String userId;
	@Column(name = "IS_ACTIVE")
	private Boolean isActive;
	@Column(name = "USER_NAME", nullable = false)
	private String userName;
	@Column(name = "USER_PASS", nullable = false)
	private String userPass;
	@Column(name = "IS_DELETED")
	private Boolean isDeleted;
	@Column(name = "CREATED_BY", length = 36)
	private String createdBy;
	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;
	@Column(name = "UPDATED_BY", length = 36)
	private String updatedBy;
	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "MEMBER_ID",
			foreignKey = @ForeignKey(name = "FK_UD_MD", value = ConstraintMode.CONSTRAINT)
	)
	private MemberDetail memberDtl;
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "STATUS_ID",
			foreignKey = @ForeignKey(name = "FK_UD_SD", value = ConstraintMode.CONSTRAINT)
	)
	private StatusDetail statusDtl;
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "ROLE_ID",
			foreignKey = @ForeignKey(name = "FK_UD_RD", value = ConstraintMode.CONSTRAINT)
	)
	private RoleDtl roleDtl;
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "AL_ID",
			foreignKey = @ForeignKey(name = "FK_UD_AL", value = ConstraintMode.CONSTRAINT))
	private AccessLevel accessLevel;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(accessLevel.getAlName().toUpperCase()));
	}

	@Override
	public String getPassword() {
		return userPass;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
