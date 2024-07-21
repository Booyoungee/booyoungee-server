package com.server.booyoungee.domain.store.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "store")
public class Store {
	@Id
	private Long storeId;

	private String basicAddress;

	private String detailedAddress;

	private String district;

	private String city;

	private String businessName;

	private String contactNumber;

	private String mainBusiness;

	@Column(nullable = false) // Ensure this is not nullable to avoid null values
	private int views = 0;  // Default value set to 0
}
