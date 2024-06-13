package com.lps.ldtracker.entity;

import java.util.Date;

import com.lps.ldtracker.security.RoleSecurity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SKILL_DTL")
@Data
@NoArgsConstructor
@AllArgsConstructor 
public class SkillsDetail {

    @Id
    @Column(name = "SKILL_ID")
    private String skillId;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;
    
    @Column(name = "SKILL_NAME")
    private String skillName;
    
    @Column(name = "SKILL_DESC")
    private String skillDesc;
    
    @Column(name = "IS_DELETED")
    private boolean isDeleted;
    
    @Column(name = "CREATED_BY")
    private String createdBy;
    
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

}