package com.server.booyoungee.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.tourInfo.dao.TourInfoRepository;
import com.server.booyoungee.domain.tourInfo.domain.TourInfo;
import com.server.booyoungee.domain.tourInfo.domain.etc.TourContentType;

@Repository
@SuppressWarnings("deprecation")
public class FakeTourInfoRepository implements TourInfoRepository {

	private final Map<String, TourInfo> database = new HashMap<>();

	@Override
	public Optional<TourInfo> findById(String contentId) {
		return Optional.ofNullable(database.get(contentId));
	}

	@Override
	public boolean existsById(String s) {
		return false;
	}

	@Override
	public <S extends TourInfo> List<S> saveAll(Iterable<S> entities) {
		return null;
	}

	@Override
	public List<TourInfo> findAll() {
		return new ArrayList<>(database.values());
	}

	@Override
	public List<TourInfo> findAllByTypes(TourContentType type) {
		return null;
	}

	@Override
	public List<TourInfo> findTop10ByOrderByViewsDesc() {
		return null;
	}

	@Override
	public List<TourInfo> top10tourInfo(Pageable pageable) {
		return null;
	}

	@Override
	public List<TourInfo> findAllById(Iterable<String> strings) {
		return null;
	}

	@Override
	public long count() {
		return 0;
	}

	@Override
	public void deleteById(String s) {

	}

	@Override
	public void delete(TourInfo entity) {

	}

	@Override
	public void deleteAllById(Iterable<? extends String> strings) {

	}

	@Override
	public void deleteAll(Iterable<? extends TourInfo> entities) {

	}

	@Override
	public void deleteAll() {

	}

	@Override
	public <S extends TourInfo> S save(S entity) {
		database.put(entity.getContentId(), entity);
		return entity;
	}

	@Override
	public void flush() {

	}

	@Override
	public <S extends TourInfo> S saveAndFlush(S entity) {
		return null;
	}

	@Override
	public <S extends TourInfo> List<S> saveAllAndFlush(Iterable<S> entities) {
		return null;
	}

	@Override
	public void deleteAllInBatch(Iterable<TourInfo> entities) {

	}

	@Override
	public void deleteAllByIdInBatch(Iterable<String> strings) {

	}

	@Override
	public void deleteAllInBatch() {

	}

	@Override
	public TourInfo getOne(String s) {
		return null;
	}

	@Override
	public TourInfo getById(String s) {
		return null;
	}

	@Override
	public TourInfo getReferenceById(String s) {
		return null;
	}

	@Override
	public <S extends TourInfo> Optional<S> findOne(Example<S> example) {
		return Optional.empty();
	}

	@Override
	public <S extends TourInfo> List<S> findAll(Example<S> example) {
		return null;
	}

	@Override
	public <S extends TourInfo> List<S> findAll(Example<S> example, Sort sort) {
		return null;
	}

	@Override
	public <S extends TourInfo> Page<S> findAll(Example<S> example, Pageable pageable) {
		return null;
	}

	@Override
	public <S extends TourInfo> long count(Example<S> example) {
		return 0;
	}

	@Override
	public <S extends TourInfo> boolean exists(Example<S> example) {
		return false;
	}

	@Override
	public <S extends TourInfo, R> R findBy(Example<S> example,
		Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
		return null;
	}

	@Override
	public List<TourInfo> findAll(Sort sort) {
		return null;
	}

	@Override
	public Page<TourInfo> findAll(Pageable pageable) {
		return null;
	}
}
