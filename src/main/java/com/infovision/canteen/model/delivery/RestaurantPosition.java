package com.infovision.canteen.model.delivery;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.infovision.canteen.model.restaurant.RestaurantProfile;

@Entity
public class RestaurantPosition {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private UUID restaurantPositionId;
  private Long latitude;
  private Long longitude;

  @OneToOne
  private RestaurantProfile restaurantProfile;

  protected RestaurantPosition() {}

  public RestaurantPosition(long latitude, long longitude, RestaurantProfile restaurantProfile) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.restaurantProfile = restaurantProfile;
  }

  // Standard Getters and Setters


public UUID getRestaurantPositionId() {
	return restaurantPositionId;
}

public void setRestaurantPositionId(UUID restaurantPositionId) {
	this.restaurantPositionId = restaurantPositionId;
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

public RestaurantProfile getRestaurantProfile() {
	return restaurantProfile;
}

public void setRestaurantProfile(RestaurantProfile restaurantProfile) {
	this.restaurantProfile = restaurantProfile;
}


}