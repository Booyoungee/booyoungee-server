package com.server.booyoungee.domain.place.dao.tour;

import com.server.booyoungee.domain.place.domain.movie.MoviePlace;
import com.server.booyoungee.domain.place.domain.tour.TourPlace;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourPlaceRepository extends JpaRepository<TourPlace, Long> {
    Optional<TourPlace> findByContentId(String contentId);

    Optional<TourPlace> findByContentTypeId(String type);

    @Query("SELECT s FROM TourPlace s where s.viewCount>0 ORDER BY s.viewCount DESC")
    List<TourPlace> top10TourPlace(Pageable pageable);
}
