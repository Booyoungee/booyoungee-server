package com.server.booyoungee.domain.BookMark.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.server.booyoungee.domain.BookMark.dao.BookMarkRepository;
import com.server.booyoungee.domain.BookMark.domain.BookMark;
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

	public Object getBookMarks(User user) throws IOException {
		List<BookMark> bookMarkList = bookMarkRepository.findAllByUser(user);
		List<TourInfoBookMarkDto> dto = bookMarkList.stream()
			.map(bookMark -> {
				try {
					return getPlace(bookMark.getPlaceId(), bookMark.getType());
				} catch (IOException e) {
					throw new NotFoundException("해당 북마크를 찾을 수 없습니다.");
				}
			})
			.collect(Collectors.toList());

		return dto;

	}

	public Object getMyBookMarkDetails(User user) throws IOException {
		List<BookMark> bookMarkList = bookMarkRepository.findAllByUser(user);
		List<PlaceDetailsDto> dto = new ArrayList<>();
		for (BookMark bookMark : bookMarkList) {
			dto.add(placeService.getDetails(bookMark.getPlaceId(), bookMark.getType()));
		}

		return dto;
	}

	public void addBookMark(User user, Long placeId, PlaceType type) {
		try {
			placeService.getPlace(placeId, type);
		} catch (Exception e) {

		}
		System.out.println("id: " + placeId + "user: " + user.getUserId() + "type: " + type);
		if (user == null || placeId == null || type == null) {
			throw new NotFoundException("Movie place with ID " + placeId + " not found.");
		}

		BookMark bookMark = BookMark.builder()
			.userId(user)
			.placeId(placeId)
			.type(type)
			.build();
		bookMarkRepository.save(bookMark);
	}

	public TourInfoBookMarkDto getPlace(Long placeId, PlaceType type) throws IOException {
		return placeService.getPlace(placeId, type);
	}

	public void deleteBookMark(User user, Long bookMarkId) {
		BookMark bookMark = bookMarkRepository.findByBookMarkIdAndUserId(bookMarkId, user);
		bookMarkRepository.delete(bookMark);
	}

	public boolean isMarked(User user, Long placeId) {
		return bookMarkRepository.existsByUserIdAndPlaceId(user, placeId);
	}
}
