package com.seansforum.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@SequenceGenerator(name="user_generator", sequenceName = "user_seq", allocationSize=1)
	@Column(name = "user_id")
    private Long id;
	
	@Column(name = "fname")
	private String fname;
	
	@Column(name = "mname")
	private String mname;

	@Column(name = "lname")
    private String lname;
    
	@Column(name = "email")
    private String email;
    
	@Column(name = "login")
    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "pswd")
    private String pswd;
    
    @Column(name = "user_role")
    private String user_role;
    
    @Column(name = "user_access")
    private int user_access;
}
