package com.gui.picpaySimplified.domain;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="users")
@Table(name="users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class User {
	
	@Id
	@UuidGenerator
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
