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
	private Long storeId; // id

	private String basicAddress; // 기본주소

	private String detailedAddress; // 상세 주소

	private String district; // 군구

	private String city; // 시

	private String businessName; // 상호명

	private String contactNumber; // 연락처

	private String mainBusiness; // 주요업종

	@Column(nullable = false) // Ensure this is not nullable to avoid null values
	private int views = 0;  // Default value set to 0
}
