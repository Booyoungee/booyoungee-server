package com.server.booyoungee.domain.bookmark.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.server.booyoungee.domain.bookmark.exception.DuplicateBookMarkException;
import com.server.booyoungee.domain.bookmark.exception.NotFoundBookMarkException;
import com.server.booyoungee.domain.place.exception.movie.NotFoundMoviePlaceException;
import com.server.booyoungee.domain.place.exception.store.NotFoundStorePlaceException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.server.booyoungee.domain.bookmark.dao.BookMarkRepository;
import com.server.booyoungee.domain.bookmark.domain.BookMark;
import com.server.booyoungee.domain.place.application.place.PlaceService;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.place.dto.response.PlaceDetailsDto;
import com.server.booyoungee.domain.tourInfo.dto.response.bookmark.TourInfoBookMarkDto;
import com.server.booyoungee.domain.user.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookMarkService {

	private final BookMarkRepository bookMarkRepository;
	private final PlaceService placeService;

	public List<TourInfoBookMarkDto> getBookMarks(User user) {
		List<BookMark> bookMarkList = bookMarkRepository.findAllByUser(user);
		List<TourInfoBookMarkDto> dto = bookMarkList.stream()
			.map(bookMark -> {
				try {
					return getPlace(bookMark.getBookMarkId(),bookMark.getPlaceId(), bookMark.getType());
				} catch (IOException e) {
					throw new NotFoundBookMarkException();
				}
			})
			.collect(Collectors.toList());

		return dto;

	}

	public List<PlaceDetailsDto> getMyBookMarkDetails(User user) throws IOException {
		List<BookMark> bookMarkList = bookMarkRepository.findAllByUser(user);
		List<PlaceDetailsDto> dto = new ArrayList<>();
		for (BookMark bookMark : bookMarkList) {
			dto.add(placeService.getDetails(bookMark.getPlaceId(), bookMark.getType()));
		}
		return dto;
	}

	public void addBookMark(User user, Long placeId, PlaceType type) {
		try {
			TourInfoBookMarkDto dto = placeService.getPlace(1L,placeId, type);
		} catch (Exception e) {
			if(type.equals(PlaceType.movie))
			{
				throw new NotFoundMoviePlaceException();
			}else if(type.equals(PlaceType.store))
			{
				throw new NotFoundStorePlaceException();
			}
			else{
				throw new NotFoundException("contentID : " + placeId + " not found.");
			}

		}
		if(bookMarkRepository.existsByUserIdAndPlaceIdAndType(user, placeId,type)){
			throw new DuplicateBookMarkException();
		}


		BookMark bookMark = BookMark.builder()
			.userId(user)
			.placeId(placeId)
			.type(type)
			.build();
		bookMarkRepository.save(bookMark);
	}

	public TourInfoBookMarkDto getPlace(Long id,Long placeId, PlaceType type) throws IOException {
		return placeService.getPlace(id,placeId, type);
	}

	public void deleteBookMark(User user, Long bookMarkId) {
		BookMark bookMark = bookMarkRepository.findByBookMarkIdAndUserId(bookMarkId, user);
		if (bookMark == null) {
			throw new NotFoundBookMarkException();
		}
		bookMarkRepository.delete(bookMark);
	}

	public boolean isMarked(User user, Long placeId) {
		return bookMarkRepository.existsByUserIdAndPlaceId(user, placeId);
	}
}
