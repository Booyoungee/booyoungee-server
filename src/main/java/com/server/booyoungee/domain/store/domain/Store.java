package com.server.booyoungee.domain.store.domain;

import jakarta.persistence.*;

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
}
