package com.infovision.canteen.model.delivery;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.infovision.canteen.model.employee.Profile;

@Entity
public class EmployeePosition {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID employeePositionId;
	
	private Long latitude;
	
	private Long longitude;
	
	@OneToOne
	private Profile profile;
	
	protected EmployeePosition() {
		// TODO Auto-generated constructor stub
	}

	// Standard Constructors using Fields
	
	public EmployeePosition(Long latitude, Long longitude, Profile profile) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.profile = profile;
	}
	
	 // Standard Getters and Setters
	
	public UUID getEmployeePositionId() {
		return employeePositionId;
	}

	public void setEmployeePositionId(UUID employeePositionId) {
		this.employeePositionId = employeePositionId;
	}

	public Long getLatitude() {
		return latitude;
	}

	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}

	public Long getLongitude() {
		return longitude;
	}

	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
		
}