package com.server.booyoungee.domain.BookMark.application;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.BookMark.dao.BookMarkRepository;
import com.server.booyoungee.domain.BookMark.domain.BookMark;
import com.server.booyoungee.domain.place.application.place.PlaceService;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.user.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookMarkService {

	private final BookMarkRepository bookMarkRepository;
	private final PlaceService placeService;

	public Object getBookMarks(User user) {
		List<BookMark> bookMarkList = bookMarkRepository.findAllByUser(user);
		return bookMarkList;

	}

	public Object getMyBookMarkDetails(User user) {
		List<BookMark> bookMarkList = bookMarkRepository.findAllByUser(user);
		return bookMarkList;
	}

	public void addBookMark(User user, Long placeId, PlaceType type) {
		try {
			placeService.getPlace(placeId, type);
		} catch (Exception e) {

		}
		BookMark bookMark = BookMark.builder()
			.user(user)
			.placeId(placeId)
			.type(type)
			.build();
	}

	public Object getPlace(Long placeId, PlaceType type) throws IOException {
		return placeService.getPlace(placeId, type);
	}
}
