package com.imcs.documentcollection.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class UserProfile {

	public UserProfile(String password, String email, boolean enabled, String firstName, String middleName, String lastName, String phoneNumber,String assassinNumber) {
		this.password = new BCryptPasswordEncoder().encode(password);
		this.email = email;
		this.middleName = middleName;
		this.enabled = enabled;
		this.firstName=firstName;
		this.lastName=lastName;
		this.phoneNumber = phoneNumber;
		this.assassinNumber=assassinNumber;
	}

	public UserProfile(String email, boolean enabled, String firstName, String middleName, String lastName,String phoneNumber, String assassinNumber) {
		this.email = email;
		this.middleName = middleName;
		this.enabled = enabled;
		this.firstName=firstName;
		this.lastName=lastName;
		this.phoneNumber = phoneNumber;
	}

	@Id
	@Column(name = "email")
	@JsonProperty("email")
	private String email;

	@Column(name = "password")
	@JsonIgnore
	private String password;

	@Column(name = "first_name")
	@JsonProperty("first_name")
	private String firstName;

	@Column(name = "last_name")
	@JsonProperty("last_name")
	private String lastName;

	@Column(name = "middle_name")
	@JsonProperty("middle_name")
	private String middleName;

	@Column(name = "enabled")
	@JsonProperty("enabled")
	private boolean enabled;

	@Column(name = "phone_number")
	@JsonProperty("phone_number")
	private String phoneNumber;

	@Column(name="assassin_number")
	@JsonProperty("assassin_number")
	private String assassinNumber;
	public String getPassword() {
		return password;
	}

	public void setPassword(String pwd) {
		password = new BCryptPasswordEncoder().encode(pwd);

	}

	public boolean matchPassword(String testPwd) {
		return new BCryptPasswordEncoder().matches(testPwd, password);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserProfile that = (UserProfile) o;
		return enabled == that.enabled && email.equals(that.email) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(middleName, that.middleName) && Objects.equals(phoneNumber, that.phoneNumber);
	}

	@Override
	public int hashCode() {
		int result = email.hashCode();
		result = 31 * result + phoneNumber.hashCode();
		result = 31 * result + middleName.hashCode();
		result = 31 * result + firstName.hashCode();
		result = 31 * result + lastName.hashCode();
		result = 31 * result + (enabled ? 1 : 0);
		return result;
	}
}
