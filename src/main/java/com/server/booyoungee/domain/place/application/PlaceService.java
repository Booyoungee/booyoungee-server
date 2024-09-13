package com.server.booyoungee.domain.place.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import com.server.booyoungee.domain.bookmark.dao.BookMarkRepository;
import com.server.booyoungee.domain.bookmark.dto.response.BookMarkResponse;
import com.server.booyoungee.domain.like.dao.LikeRepository;
import com.server.booyoungee.domain.movie.application.TmdbApiService;
import com.server.booyoungee.domain.movie.dto.request.MovieImagesDto;
import com.server.booyoungee.domain.place.application.movie.MoviePlaceService;
import com.server.booyoungee.domain.place.application.store.StorePlaceService;
import com.server.booyoungee.domain.place.application.tour.TourInfoOpenApiService;
import com.server.booyoungee.domain.place.application.tour.TourPlaceService;
import com.server.booyoungee.domain.place.dao.PlaceRepository;
import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.place.domain.movie.MoviePlace;
import com.server.booyoungee.domain.place.dto.response.PlaceDetailsResponse;
import com.server.booyoungee.domain.place.dto.response.UserMeResponse;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlacePageResponse;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlaceResponse;
import com.server.booyoungee.domain.place.dto.response.store.StorePlaceResponse;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoDetailsResponseDto;
import com.server.booyoungee.domain.place.exception.NotFoundPlaceException;
import com.server.booyoungee.domain.place.exception.movie.NotFoundMoviePlaceException;
import com.server.booyoungee.domain.place.exception.store.NotFoundStorePlaceException;
import com.server.booyoungee.domain.review.comment.dao.CommentRepository;
import com.server.booyoungee.domain.review.comment.domain.Comment;
import com.server.booyoungee.domain.review.stars.domain.Stars;
import com.server.booyoungee.domain.stamp.dao.StampRepository;
import com.server.booyoungee.domain.user.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlaceService {
	private final TourPlaceService placeService;
	private final PlaceRepository placeRepository;
	private final MoviePlaceService moviePlaceService;
	private final StorePlaceService storePlaceService;
	private final TmdbApiService movieService;
	private final TourInfoOpenApiService tourInfoOpenApiService;

	private final StampRepository stampRepository;
	private final BookMarkRepository bookMarkRepository;
	private final LikeRepository likeRepository;
	private final CommentRepository commentRepository;

	@Transactional
	public BookMarkResponse getPlace(Long id, Long placeId, PlaceType type) throws
		IOException,
		NotFoundException {

		if (type.getKey().equals("movie")) {
			MoviePlaceResponse movie = moviePlaceService.getMoviePlace(placeId);
			if (movie == null) {
				throw new NotFoundMoviePlaceException();
			}
			return BookMarkResponse.of(id, placeId, null, movie.name(), movie.mapX(), movie.mapY(),
				type, type.getKey());

		} else if (type.getKey().equals("store")) {
			StorePlaceResponse store = storePlaceService.getStore(placeId);
			if (store == null) {
				throw new NotFoundStorePlaceException();
			}
			return BookMarkResponse.of(id, placeId, null, store.name(), store.mapX(), store.mapY(),
				type, type.getKey());

		} else {

			TourInfoDetailsResponseDto tourInfo = placeService.getTour(placeId);
			return BookMarkResponse.of(id, placeId, tourInfo.contentid(), tourInfo.title(), tourInfo.mapx(),
				tourInfo.mapy(),
				type, tourInfo.contenttypeid());
		}
	}

	@Transactional
	public List<String> placeImageList(TourInfoDetailsResponseDto image) throws IOException {

		List<String> imageList = new ArrayList<>();
		if (image.firstimage() != null && !image.firstimage().equals("")) {
			imageList.add(image.firstimage());
			if (image.firstimage2() != null && !image.firstimage2().equals("")) {
				imageList.add(image.firstimage2());
			}
		}
		return imageList;
	}

	private List<String> getMovieList(MoviePlaceResponse moviePlace) {
		MoviePlacePageResponse<MoviePlace> places = moviePlaceService.getMoviePlacesByKeyword(moviePlace.name(), 1, 3);
		List<String> movieList = new ArrayList<>();
		for (MoviePlaceResponse movieTag : places.contents()) {
			movieList.add(movieTag.movieName());
		}
		if (movieList.isEmpty()) {
			movieList.add(moviePlace.movieName());
		}
		return movieList;
	}

	private List<String> moviePosterList(MoviePlaceResponse moviePlace) {
		List<String> posterUrl = new ArrayList<>();
		List<MovieImagesDto.BackDrops> backdrops = movieService.getMovie(moviePlace.movieName()).getBackdrops();
		for (MovieImagesDto.BackDrops backdrop : backdrops) {
			posterUrl.add(backdrop.getFilePath());
		}
		return posterUrl;
	}

	@Transactional
	public UserMeResponse getUserMe(Place place, User user) {
		boolean hasStamp = stampRepository.existsByUserAndPlace(user, place);
		boolean hasLike = likeRepository.existsByUserAndPlace(user, place);
		boolean hasBookmark = bookMarkRepository.existsByUserIdAndPlaceId(user, place);
		return UserMeResponse.of(hasStamp, hasLike, hasBookmark);

	}

	@Transactional
	public PlaceDetailsResponse getDetails(Long placeId, PlaceType type, User me) throws IOException {

		if (!isMatchType(placeId, type.getKey())) {
			throw new NotFoundPlaceException();
		}

		PlaceDetailsResponse dto;
		TourInfoDetailsResponseDto tourInfo = null;
		List<String> imageList = new ArrayList<>();
		List<String> posterUrl = null;
		List<String> movieList = null;
		String tel = null;

		Place place = getByPlaceId(placeId);

		UserMeResponse userMe = getUserMe(place, me);

		List<Stars> stars = commentRepository.findAllByPlaceId(placeId)
			.stream()
			.map(Comment::getStars)
			.collect(Collectors.toList());

		// Check the type of place
		if (type.getKey().equals("tour")) {
			tourInfo = placeService.getTour(placeId);
			imageList = placeImageList(tourInfo);

			dto = PlaceDetailsResponse.of(placeId + "", tourInfo.title(), tourInfo.addr1(), tourInfo.tel(),
				imageList, type, movieList,
				posterUrl, place.getLikes().size(),
				0, place.getStamps().size(), place.getComments().size(), place.getBookmarks().size(), stars, userMe);

		} else {
			List<TourInfoDetailsResponseDto> tourInfoList = tourInfoOpenApiService.getTourInfoByKeyword(
				place.getName());
			// Use Optional to handle potential null or empty list
			tourInfo = tourInfoList != null && !tourInfoList.isEmpty() ? tourInfoList.get(0) : null;
			if (tourInfo != null) {
				tel = tourInfo.tel();
				imageList = placeImageList(tourInfo);
			}

			if (type.getKey().equals("movie")) {
				MoviePlaceResponse moviePlace = moviePlaceService.getMoviePlace(placeId);
				movieList = getMovieList(moviePlace);
				posterUrl = moviePosterList(moviePlace);
			}

			dto = PlaceDetailsResponse.from(place, type, tel, imageList, movieList, posterUrl, stars, userMe);

		}
		// Rest of your logic goes here...
		return dto;

	}

	//조회할 때 place 가져올 때
	public Place getByPlaceId(Long placeId) {
		return placeRepository.findById(placeId)
			.orElseThrow(NotFoundPlaceException::new);
	}

	public boolean isMatchType(Long placeId, String type) {
		String types = placeRepository.findDiscriminatorTypeById(placeId).toLowerCase();
		return types.equals(type);
	}

	//등록할 때 place 가져올 때
	public Place getByPlaceId(Long placeId, String type) {

		if (!isMatchType(placeId, type)) {
			throw new NotFoundPlaceException();
		}

		if (type.equals("tour")) {
			Place place = placeRepository.findById(placeId)
				.orElse(null);
			if (place == null) {
				place = placeService.saveTourPlace(placeId);
				return place;
			}
			return place;

		} else {
			Place place = placeRepository.findById(placeId)
				.orElseThrow(NotFoundPlaceException::new);
			return place;
		}
	}

	public Page<Place> getTop10Place() {
		Pageable pageable = PageRequest.of(0, 10);
		return placeRepository.findTop10ByOrderByViewCountDesc(pageable);
	}
}
