package com.imcs.documentcollection.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employees")
@NoArgsConstructor
@Getter
@Setter
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonProperty("id")
	private int id;
	@Column(name = "email")
	@JsonProperty("email")
	private String email;

	@Column(name = "first_name")
	@JsonProperty("first_name")
	private String firstName;

	@Column(name = "last_name")
	@JsonProperty("last_name")
	private String lastName;

	@Column(name = "middle_name")
	@JsonProperty("middle_name")
	private String middleName;

	@Column(name = "phone_number")
	@JsonProperty("phone_number")
	private String phoneNumber;

	@Column(name="ssn_number")
	@JsonProperty("ssn_number")
	private int ssnNumber;

	@Column(name = "uid")
	@JsonProperty("uid")
	private long uid;

	@Column(name = "created_date")
	@JsonProperty("created_date")
	private Date createdDate;

	@Column(name = "expiry_date")
	@JsonProperty("expiry_date")
	private Date expiryDate;

}
