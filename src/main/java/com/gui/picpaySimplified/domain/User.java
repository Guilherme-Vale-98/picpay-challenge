package com.gui.picpaySimplified.domain;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class User {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", type = org.hibernate.id.uuid.UuidGenerator.class)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;
	
	@NotNull
    @NotBlank
    @Size(max = 50)
    @Column(length = 50)
	private String firstName;
	
	@NotNull
	@NotBlank
	@Size(max = 50)
	@Column(length = 50)
	private String lastName;
	
	@NotNull
    @NotBlank
    @Size(max = 50)
    @Column( unique=true, length = 50)
	private String document;
	
	@NotNull
    @NotBlank
    @Size(max = 50)
    @Column( unique=true, length = 50)
	private String email;
	
	@NotNull
    @NotBlank
	private String password;
	
	
}
