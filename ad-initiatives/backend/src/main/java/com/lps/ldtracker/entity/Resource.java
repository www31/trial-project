package com.lps.ldtracker.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lps.ldtracker.security.RoleSecurity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MEMBER_DTL")
@NoArgsConstructor
@AllArgsConstructor 
@Data
public class Resource implements UserDetails{

//	private static final long serialVersionUID = 1L;
	@Id
//	@GeneratedValue(strategy= GenerationType.IDENTITY)
//	@SequenceGenerator(sequenceName = "resource_seq", allocationSize = 1, name = "resource")
//	private Long id;
	
	@Column(name = "MEMBER_ID")
	private String memberId;
	
	@Column(name = "FIRST_NAME")
	private String firstname;
	
	@Column(name = "LAST_NAME")
	private String lastname;
	
	@Column(name = "MIDDLE_NAME")
	private String middlename;
	
	private String suffix;
	
	private String gender;
	
	@Column(name = "EMPLOYEE_NUM")
	private String empId;
	
	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;
	
	@Column(name = "REGION_ID")
	private String region;
	
	@Column(name = "IS_ACTIVE")
	private Boolean isEnabled = false;
	
	@Column(name = "CAREER_LEVEL_ID")
	private String careerStep;
	
	@Column(name = "TEAM_ID")
	private String team;
	
	@Column(name = "STATUS_ID")
	private String status = "";
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private RoleSecurity role;
	
	private String skills;
	
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name="resource_id", referencedColumnName = "id")
//    private List<CertificationFileUpload> certifications;
	
	@Override
	public String getPassword() {
	    return password;
	}
	
	@Override 
	public String getUsername() {
		return emailAddress;
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
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorities();
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
