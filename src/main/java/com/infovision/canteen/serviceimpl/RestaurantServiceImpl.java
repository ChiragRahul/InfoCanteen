package com.infovision.canteen.serviceimpl;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infovision.canteen.dto.restaurant.RestaurantProfileDto;
import com.infovision.canteen.exception.RestaurantException;
import com.infovision.canteen.model.restaurant.Restaurant;
import com.infovision.canteen.model.restaurant.RestaurantItem;
import com.infovision.canteen.model.restaurant.RestaurantProfile;
import com.infovision.canteen.model.restaurant.Status;
import com.infovision.canteen.repository.RestaurantItemRepository;
import com.infovision.canteen.repository.RestaurantProfileRepository;
import com.infovision.canteen.repository.RestaurantRepository;
import com.infovision.canteen.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private RestaurantProfileRepository restaurantProfileRepository;

	@Autowired
	private RestaurantItemRepository restaurantItemRepository;

	@Override
	public RestaurantProfileDto addRestaurant(RestaurantProfileDto restaurantProfileDto)
			throws RestaurantException, IOException {
		// TODO Auto-generated method stub

		List<Restaurant> restaurants = restaurantRepository.findAll();

		for (Restaurant restaurant : restaurants) {
			if (restaurant.getRestaurantProfile().getRestaurantName().equals(restaurantProfileDto.getRestaurantName())
					&& restaurant.getRestaurantProfile().getCity().equals(restaurantProfileDto.getCity())) {
				throw new RestaurantException(
						"Restaurant already exists with this name " + restaurantProfileDto.getRestaurantName());
			}
		}

		Restaurant restaurant = new Restaurant();

		RestaurantProfile restaurantProfile = new RestaurantProfile();

		restaurantProfile.setRestaurantName(restaurantProfileDto.getRestaurantName());
		restaurantProfile.setEmail(restaurantProfile.getEmail());
		restaurantProfile.setMobileNumber(restaurantProfile.getMobileNumber());
		restaurantProfile.setPassword(restaurantProfile.getPassword());
		restaurantProfile.setCountry(restaurantProfile.getCountry());
		restaurantProfile.setState(restaurantProfile.getState());
		restaurantProfile.setCity(restaurantProfile.getCity());
		restaurantProfile.setImageUrl(restaurantProfileDto.getImageUrl());

		restaurant.setRestaurantStatus(Status.ACTIVE);
		restaurant.setRestaurantProfile(restaurantProfile);

		restaurantProfileRepository.saveAndFlush(restaurantProfile);

		restaurantRepository.save(restaurant);

		editRestaurant(restaurantProfileDto, restaurant.getRestaurantid());

		return restaurantProfileDto;
	}

	@Override
	public RestaurantProfileDto editRestaurant(RestaurantProfileDto restaurantProfileDto, UUID id) {
		// TODO Auto-generated method stub

		if (restaurantRepository.existsById(id)) {
			Restaurant restaurant = restaurantRepository.getOne(id);

			restaurant.getRestaurantProfile().setCity(restaurantProfileDto.getCity());
			restaurant.getRestaurantProfile().setCountry(restaurantProfileDto.getCountry());
			restaurant.getRestaurantProfile().setEmail(restaurantProfileDto.getEmail());
			restaurant.getRestaurantProfile().setMobileNumber(restaurantProfileDto.getMobileNumber());
			restaurant.getRestaurantProfile().setPassword(restaurantProfileDto.getPassword());
			restaurant.getRestaurantProfile().setRestaurantName(restaurantProfileDto.getRestaurantName());
			restaurant.getRestaurantProfile().setState(restaurantProfileDto.getState());
			restaurant.getRestaurantProfile().setImageUrl(restaurantProfileDto.getImageUrl());

			restaurantRepository.save(restaurant);

		}

		return restaurantProfileDto;
	}

	@Override
	public Restaurant viewRestaurant(String restaurantName) throws RestaurantException {
		// TODO Auto-generated method stub

		Restaurant restaurant = restaurantRepository.findByName(restaurantName);

		if (restaurant == null)
			throw new RestaurantException("Restaurant Details are not found");

		return restaurant;
	}

	@Override
	public String deleteRestaurant(String restaurantName) throws RestaurantException {
		// TODO Auto-generated method stub

		Restaurant restaurant = restaurantRepository.findByName(restaurantName);

		if (restaurant == null)
			throw new RestaurantException("Restaurant Details are not found");

		List<RestaurantItem> restaurantItems = restaurantItemRepository.findByRestaurant(restaurant.getRestaurantid());

		restaurantItemRepository.deleteAll();

		restaurantRepository.delete(restaurant);

		return "Restaurant is deleted";
	}

	@Override
	public List<Restaurant> getAllRestaurants() throws RestaurantException {
		// TODO Auto-generated method stub

		List<Restaurant> restaurants = restaurantRepository.findAll();

		if (restaurants.isEmpty())
			throw new RestaurantException("Restaurants List is empty");

		return restaurants;
	}

	@Override
	public Restaurant restaurantstatus(Status status, String restaurantName) throws RestaurantException {
		// TODO Auto-generated method stub

		Restaurant restaurant = restaurantRepository.findByName(restaurantName);

		if (restaurant == null)
			throw new RestaurantException("Restaurant Details are not found");

		restaurant.setRestaurantStatus(status);

		return restaurant;
	}

	@Override
	public Status restaurantstatus(String restaurantName) throws RestaurantException {
		// TODO Auto-generated method stub

		Restaurant restaurant = restaurantRepository.findByName(restaurantName);

		if (restaurant == null)
			throw new RestaurantException("Restaurant Details are not found");

		return restaurant.getRestaurantStatus();
	}

	@Override
	public List<Restaurant> getAllRestaurants(String location) throws RestaurantException {
		// TODO Auto-generated method stub
		List<Restaurant> restaurants = restaurantRepository.findByLocation(location);

		if (restaurants.isEmpty())
			throw new RestaurantException("Restaurants List is empty");

		return restaurants;
	}

	@Override
	public String allRestaurantstatus(String location,Status status) throws RestaurantException {
		// TODO Auto-generated method stub

		List<Restaurant> restaurants = restaurantRepository.findByLocation(location);

		if (restaurants.isEmpty())
			throw new RestaurantException("Restaurants List is empty");

		
		for(Restaurant restaurant:restaurants)
		{
			restaurant.setRestaurantStatus(status);
			restaurantRepository.save(restaurant);
			
		}
		String s = "All Restaurants Status are setted as "+status;
		
		return s;
	}
	
	
	
	//View all Restaurant
		@Override
		public Object getRestaurant() {
			List<RestaurantProfile> restaurantProfileList = restaurantProfileRepository.findAll();
			List<RestaurantProfileDto> restaurantProfileDtos = new ArrayList<>();
			for(RestaurantProfile profile : restaurantProfileList) {
				restaurantProfileDtos.add(getRestaurantProfileDto(profile));
			}
			return restaurantProfileDtos;
		}


	//Delete a restaurant by id
		@Override
		public String removeRestaurant(UUID restaurantProfileId) throws Exception {
			Optional<RestaurantProfile> optionalRestaurantProfile = restaurantProfileRepository.findById(restaurantProfileId);
			if(!optionalRestaurantProfile.isPresent())
				throw new Exception("Restaurant profile is not available");
			RestaurantProfile restaurantProfile = optionalRestaurantProfile.get();
			restaurantProfileRepository.delete(restaurantProfile);
			return "Restaurant profile has been deleted";
		}
		
		
		
		
	//Function to get RestaurantProfileDto for view all RestaurantProfile
		public RestaurantProfileDto getRestaurantProfileDto(RestaurantProfile restaurantProfile) {
			RestaurantProfileDto restaurantProfileDto = new RestaurantProfileDto();
			restaurantProfileDto.setRestaurantName(restaurantProfile.getRestaurantName());
			restaurantProfileDto.setImageUrl(restaurantProfile.getImageUrl());
			restaurantProfileDto.setEmail(restaurantProfile.getEmail());
			restaurantProfileDto.setMobileNumber(restaurantProfile.getMobileNumber());
			restaurantProfileDto.setPassword(restaurantProfile.getPassword());
			restaurantProfileDto.setCountry(restaurantProfile.getCountry());
			restaurantProfileDto.setState(restaurantProfile.getState());
			restaurantProfileDto.setCity(restaurantProfile.getCity());
			return restaurantProfileDto;
		}

}
