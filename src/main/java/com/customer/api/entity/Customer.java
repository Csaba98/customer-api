package com.customer.api.entity;

import com.customer.api.annotation.ExistsId;
import com.customer.api.annotation.UniqueId;
import com.customer.api.converter.PasswordConverter;
import com.customer.api.dto.OnCreate;
import com.customer.api.dto.OnUpdate;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Customer {

	private static final String REQUIRED_STR = "Must be entered!";
	private static final String MIN_STR = "Must be at least {value}!";
	private static final String MAX_STR = "Must be at most {value}!";
	private static final String MIN_CHAR_STR = "Must be at least {min} character(s)!";
	private static final String MAX_CHAR_STR = "Must be at most {max} character(s)!";
	private static final String VALID_EMAIL_STR = "Must be valid email!";
	private static final String INVALID_PHONE_FORMAT_STR = "Invalid phone format! Must be +36(20|30|31|50|70)1234567 format!";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@UniqueId(groups = { OnCreate.class })
	@ExistsId(groups = { OnUpdate.class })
	private Long id;

	@NotNull(message = REQUIRED_STR, groups = { OnCreate.class, OnUpdate.class })
	@NotBlank(message = REQUIRED_STR, groups = { OnCreate.class, OnUpdate.class })
	@Size(max = 50, message = MAX_CHAR_STR, groups = { OnCreate.class, OnUpdate.class })
	private String firstname;

	@NotNull(message = REQUIRED_STR, groups = { OnCreate.class, OnUpdate.class })
	@NotBlank(message = REQUIRED_STR, groups = { OnCreate.class, OnUpdate.class })
	@Size(max = 100, message = MAX_CHAR_STR, groups = { OnCreate.class, OnUpdate.class })
	private String lastname;

	@NotNull(message = REQUIRED_STR, groups = { OnCreate.class, OnUpdate.class })
	@Min(value = 1, message = MIN_STR, groups = { OnCreate.class, OnUpdate.class })
	@Max(value = 127, message = MAX_STR, groups = { OnCreate.class, OnUpdate.class })
	private Short age;

	@NotNull(message = REQUIRED_STR, groups = { OnCreate.class, OnUpdate.class })
	@NotBlank(message = REQUIRED_STR, groups = { OnCreate.class, OnUpdate.class })
	@Pattern(regexp = "[\\+](36)(20|30|31|50|70)\\d{7}", message = INVALID_PHONE_FORMAT_STR, groups = { OnCreate.class, OnUpdate.class })
	private String phone;

	@NotNull(message = REQUIRED_STR, groups = { OnCreate.class, OnUpdate.class })
	@NotBlank(message = REQUIRED_STR, groups = { OnCreate.class, OnUpdate.class })
	@Email(message = VALID_EMAIL_STR, groups = { OnCreate.class, OnUpdate.class })
	private String email;

	@NotNull(message = REQUIRED_STR, groups = { OnCreate.class, OnUpdate.class })
	@NotBlank(message = REQUIRED_STR, groups = { OnCreate.class, OnUpdate.class })
	@Size(max = 25, message = MAX_CHAR_STR, groups = { OnCreate.class, OnUpdate.class })
	private String username;

	@Convert(converter = PasswordConverter.class)
	@NotNull(message = REQUIRED_STR, groups = { OnCreate.class, OnUpdate.class })
	@NotBlank(message = REQUIRED_STR, groups = { OnCreate.class, OnUpdate.class })
	@Size(min = 5, message = MIN_CHAR_STR, groups = { OnCreate.class, OnUpdate.class })
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Short getAge() {
		return age;
	}

	public void setAge(Short age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", age=" + age
				+ ", phone=" + phone + ", email=" + email + ", username=" + username + ", password=" + password + "]";
	}

}