package com.infovision.canteen.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.infovision.canteen.model.credentials.Credentials;
import com.infovision.canteen.model.delivery.Delivery;
@Repository
public interface DeliveryRepository  extends JpaRepository<Delivery, UUID>{

	@Query("SELECT d FROM Delivery d WHERE d.credentials.userName =:userName AND d.credentials.password =:password")
	Delivery findUser(String userName, String password);

	@Query("SELECT d FROM Delivery d WHERE d.deliveryPersonStatus LIKE 'ACTIVE' AND d.workingStatus LIKE 'FREE'")
	List<Delivery> findDeliveryBoys();

	

}
