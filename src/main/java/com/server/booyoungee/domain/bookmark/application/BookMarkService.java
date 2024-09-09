package com.server.booyoungee.domain.bookmark.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.booyoungee.domain.bookmark.dao.BookMarkRepository;
import com.server.booyoungee.domain.bookmark.domain.BookMark;
import com.server.booyoungee.domain.bookmark.dto.response.BookMarkListResponse;
import com.server.booyoungee.domain.bookmark.dto.response.BookMarkPersistResponse;
import com.server.booyoungee.domain.bookmark.dto.response.BookMarkResponse;
import com.server.booyoungee.domain.bookmark.exception.DuplicateBookMarkException;
import com.server.booyoungee.domain.bookmark.exception.NotFoundBookMarkException;
import com.server.booyoungee.domain.place.application.PlaceService;
import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.place.dto.response.PlaceDetailsResponse;
import com.server.booyoungee.domain.user.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookMarkService {

	private final BookMarkRepository bookMarkRepository;
	private final PlaceService placeService;

	public BookMarkListResponse getBookMarks(User user) {
		List<BookMark> bookMarkList = bookMarkRepository.findAllByUser(user);
		List<BookMarkResponse> dto = bookMarkList.stream()
			.map(bookMark -> {
				try {
					return getPlace(bookMark.getBookMarkId(), bookMark.getPlaceId().getId(), bookMark.getType());
				} catch (IOException e) {
					throw new NotFoundBookMarkException();
				}
			})
			.collect(Collectors.toList());
		return BookMarkListResponse.of(dto);
	}

	public List<PlaceDetailsResponse> getMyBookMarkDetails(User user) throws IOException {

		List<BookMark> bookMarkList = bookMarkRepository.findAllByUser(user);
		System.out.println("getMyBookMarkDetails bookMarkList" + bookMarkList.size());
		List<PlaceDetailsResponse> dto = new ArrayList<>();
		for (BookMark bookMark : bookMarkList) {
			dto.add(placeService.getDetails(bookMark.getPlaceId().getId(), bookMark.getType()));
		}
		return dto;
	}

	@Transactional
	public BookMarkPersistResponse addBookMark(User user, Long placeId, PlaceType type) {

		Place place = placeService.getByPlaceId(placeId, type.getKey());

		if (bookMarkRepository.existsByUserIdAndPlaceIdAndType(user, place, type)) {
			throw new DuplicateBookMarkException();
		}
		BookMark bookMark = BookMark.of(user, place, type);
		bookMarkRepository.save(bookMark);
		return BookMarkPersistResponse.from(bookMark);
	}

	public BookMarkResponse getPlace(Long id, Long placeId, PlaceType type) throws IOException {
		return placeService.getPlace(id, placeId, type);
	}

	public BookMarkPersistResponse deleteBookMark(User user, Long bookMarkId) {
		BookMark bookMark = bookMarkRepository.findByBookMarkIdAndUserId(bookMarkId, user);
		if (bookMark == null) {
			throw new NotFoundBookMarkException();
		}
		bookMarkRepository.delete(bookMark);
		return BookMarkPersistResponse.from(bookMark);
	}

}
