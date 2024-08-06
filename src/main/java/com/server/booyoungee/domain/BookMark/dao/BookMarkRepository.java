package com.server.booyoungee.domain.BookMark.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.BookMark.domain.BookMark;
import com.server.booyoungee.domain.user.domain.User;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

	List<BookMark> findAllByUser(User user);
}
