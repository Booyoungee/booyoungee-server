package com.server.booyoungee.domain.place.dao.hotPlace;

import com.server.booyoungee.domain.place.domain.HotPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotPlaceRepository extends JpaRepository<HotPlace, Long>{
}
