package com.softedge.solution.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="user_tbl")
@Data
public class UserRegistration {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="email_id")
	private String username;
	private String password;
	private boolean enabled;


	private String name;

	private String category;

	private String dob;
	private String gender;

	@Column(name = "nationality")
	private String nationality;


	private String photo;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;

	@OneToMany(cascade=CascadeType.ALL)
	private List<Authorities> authorities;

	@Column(name = "force_password_change")
	private boolean forcePasswordChange;
	@Column(name = "profile_completed")
	private boolean profileCompleted;
	@Column(name = "ipv_completed")
	private boolean ipvCompleted;
	private Long phone;





}
