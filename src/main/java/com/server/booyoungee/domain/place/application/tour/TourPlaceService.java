package com.server.booyoungee.domain.place.application.tour;

import com.server.booyoungee.domain.place.dao.tour.TourPlaceRepository;
import com.server.booyoungee.domain.place.domain.movie.MoviePlace;
import com.server.booyoungee.domain.place.domain.store.StorePlace;
import com.server.booyoungee.domain.place.domain.tour.TourPlace;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlaceResponse;
import com.server.booyoungee.domain.place.dto.response.store.StorePlaceResponse;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoDetailsResponseDto;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoResponseDto;
import com.server.booyoungee.domain.place.dto.response.tour.TourPlaceResponseDto;
import com.server.booyoungee.domain.place.exception.movie.NotFoundMoviePlaceException;
import com.server.booyoungee.domain.place.exception.store.NotFoundStorePlaceException;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoBookMarkResponse;
import com.server.booyoungee.domain.place.exception.tour.NotFoundTourPlaceException;
import com.server.booyoungee.domain.tourInfo.domain.TourInfo;
import com.server.booyoungee.domain.tourInfo.domain.etc.TourContentType;
import com.server.booyoungee.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.server.booyoungee.domain.place.exception.tour.TourInfoExceptionCode.NOT_FOUND_TOUR_PLACE_BY_ID;

@Service
@RequiredArgsConstructor
public class TourPlaceService {

    private final TourPlaceRepository tourPlaceRepository;
    private final TourInfoOpenApiService tourInfoOpenApiService;
@Transactional
    public TourInfoDetailsResponseDto getTour(Long placeId) throws IOException {
        TourPlace tourPlace =tourPlaceRepository.findByContentId(placeId.toString())
                .orElse(null);

        List<TourInfoDetailsResponseDto> tourInfoList = tourInfoOpenApiService.getCommonInfoByContentId(placeId.toString());
        if(tourInfoList.isEmpty())
            throw new CustomException(NOT_FOUND_TOUR_PLACE_BY_ID);


        TourInfoDetailsResponseDto tourInfo = tourInfoList.get(0);
        if (tourPlace == null) {
            tourPlace = TourPlace.builder()
                    .contentId(placeId.toString())
                    .contentTypeId(tourInfo.contenttypeid())
                    .build();
        }
        tourPlace.increaseViewCount();
        tourPlaceRepository.save(tourPlace);

        return tourInfo;
    }



    public List<TourPlaceResponseDto> getTourInfoListByType(TourContentType contentId) {

        String type = TourContentType.fromCode(contentId.getCode()).getDescription();
        List<TourPlaceResponseDto>  tourInfoList = tourPlaceRepository.findByContentTypeId(type).stream()
                .map(tourInfo -> TourPlaceResponseDto.from(tourInfo))
                .collect(Collectors.toList());
          return tourInfoList;
    }

    public List<TourPlaceResponseDto> getTourInfoList() {
        List<TourPlaceResponseDto>  tourInfoList = tourPlaceRepository.findAll().stream()
                .map(tourInfo -> TourPlaceResponseDto.from(tourInfo))
                .collect(Collectors.toList());
        return tourInfoList;
    }


    public List<TourPlace> getTop10TourInfo() {
        List<TourPlace> top10tourInfo = tourPlaceRepository.top10TourPlace(PageRequest.of(0, 10));
        return top10tourInfo;
    }




}
