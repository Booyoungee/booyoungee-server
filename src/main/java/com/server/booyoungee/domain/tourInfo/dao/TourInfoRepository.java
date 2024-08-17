package com.server.booyoungee.domain.tourInfo.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.tourInfo.domain.TourInfo;
import com.server.booyoungee.domain.tourInfo.domain.etc.TourContentType;

@Repository
public interface TourInfoRepository extends JpaRepository<TourInfo, String> {

	@Query("SELECT t FROM TourInfo t ORDER BY t.contentTypeId ASC, t.views DESC")
	List<TourInfo> findAll();

	@Query("SELECT t FROM TourInfo t WHERE t.contentTypeId = :type ORDER BY t.views DESC")
	List<TourInfo> findAllByTypes(@Param("type") TourContentType type);

	@Query("SELECT t FROM TourInfo t ORDER BY t.views DESC")
	List<TourInfo> findTop10ByOrderByViewsDesc();

	@Query("SELECT t FROM TourInfo t where t.views>0 ORDER BY t.views DESC")
	List<TourInfo> top10tourInfo(Pageable pageable);
}
