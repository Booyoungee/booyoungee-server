package com.server.booyoungee.domain.place.application.hotPlace;

import com.server.booyoungee.domain.place.dao.hotPlace.HotPlaceRepository;
import com.server.booyoungee.domain.place.domain.HotPlace;
import com.server.booyoungee.domain.place.dto.response.hotPlace.HotPlaceResponseDto;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotPlaceService {
    private final HotPlaceRepository hotPlaceRepository;



    public List<HotPlaceResponseDto> getHotPlaces(){
        // Retrieve all hot places from the repository
        List<HotPlace> hotPlaces = hotPlaceRepository.findAll();

        // Transform each HotPlace entity into a HotPlaceResponseDto
        List<HotPlaceResponseDto> dto = hotPlaces.stream()
                .map(hotPlace -> HotPlaceResponseDto.builder()
                        .placeId(hotPlace.getPlaceId())
                        .type(hotPlace.getType())
                        .name(hotPlace.getName())
                        .updatedAt(hotPlace.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        return dto;
    }







}
