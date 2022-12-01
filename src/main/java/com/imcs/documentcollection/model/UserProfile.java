package com.imcs.documentcollection.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class UserProfile {

	public UserProfile(String username, String password, String email, String fullName, boolean enabled, String manager,String firstName,String lastName, String location,String officeLocation) {
		this.username = username;
		this.password = new BCryptPasswordEncoder().encode(password);
		this.email = email;
		this.fullName = fullName;
		this.enabled = enabled;
		this.manager = manager;
		this.firstName=firstName;
		this.lastName=lastName;
		this.location = location;
		this.officeLocation=officeLocation;
	}

	public UserProfile(String username, String email, String fullName, boolean enabled, String manager,String firstName,String lastName, String location,String officeLocation) {
		this.username = username;
		this.email = email;
		this.fullName = fullName;
		this.enabled = enabled;
		this.manager = manager;
		this.firstName=firstName;
		this.lastName=lastName;
		this.location = location;
		this.officeLocation=officeLocation;
	}

	@Id
	@Column(name = "username")
	@JsonProperty("username")
	private String username;

	@Column(name = "password")
	@JsonIgnore
	private String password;

	@Column(name = "email")
	@JsonProperty("email")
	private String email;

	@Column(name = "first_name")
	@JsonProperty("first_name")
	private String firstName;

	@Column(name = "last_name")
	@JsonProperty("last_name")
	private String lastName;

	@Column(name = "full_name")
	@JsonProperty("full_name")
	private String fullName;

	@Column(name = "enabled")
	@JsonProperty("enabled")
	private boolean enabled;

	@OneToMany(mappedBy = "username", cascade = CascadeType.ALL)
//	@JoinColumn(name = "username", referencedColumnName = "username")
	private Set<Authority> authorities = new HashSet<>();
	
	@Column(name = "manager")
	@JsonProperty("manager")
	private String manager;

	@Column(name="location")
	@JsonProperty("location")
	private String location;

	@Column(name="office_location")
	@JsonProperty("office_location")
	private String officeLocation;

	public String getPassword() {
		return password;
	}

	public void setPassword(String pwd) {
		password = new BCryptPasswordEncoder().encode(pwd);

	}

	public String getAuthority(){
		return authorities.stream().findFirst().get().getAuthority();
	}

	public void setAuthority(Authority authority){
		this.authorities.add(authority);
	}


	public boolean matchPassword(String testPwd) {
		return new BCryptPasswordEncoder().matches(testPwd, password);
	}

//	@Override
//	public boolean equals(Object o) {
//		if (this == o) return true;
//		if (o == null || getClass() != o.getClass()) return false;
//
//		UserProfile that = (UserProfile) o;
//
//		if (enabled != that.enabled) return false;
//		if (!username.equals(that.username)) return false;
//		if (!email.equals(that.email)) return false;
//		if (!fullName.equals(that.fullName)) return false;
//		if (!firstName.equals(that.firstName)) return false;
//		if (!lastName.equals(that.lastName)) return false;
//		return Objects.equals(manager, that.manager);
//	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserProfile that = (UserProfile) o;
		return enabled == that.enabled && username.equals(that.username) && Objects.equals(email, that.email) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(fullName, that.fullName) && Objects.equals(manager, that.manager) && Objects.equals(location, that.location) && Objects.equals(officeLocation, that.officeLocation);
	}

	@Override
	public int hashCode() {
		int result = username.hashCode();
		result = 31 * result + email.hashCode();
		result = 31 * result + fullName.hashCode();
		result = 31 * result + firstName.hashCode();
		result = 31 * result + lastName.hashCode();
		result = 31 * result + (enabled ? 1 : 0);
		result = 31 * result + (manager != null ? manager.hashCode() : 0);
		return result;
	}
}
