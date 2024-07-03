package com.server.booyoungee.domain.tourInfo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.tourInfo.domain.TourInfo;

@Repository
public interface TourInfoRepository extends JpaRepository<TourInfo, String>{
}
