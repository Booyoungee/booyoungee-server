package com.server.booyoungee.domain.user.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserId(Long userId);

	boolean existsBySerialId(String s);

	Optional<User> findBySerialId(String s);

	boolean existsByName(String name);

	@Query("SELECT u FROM User u WHERE u.serialId = :serialId AND u.name IS NOT NULL AND TRIM(u.name) <> ''")
	Optional<User> findBySerialIdAndName(@Param("serialId") String serialId);

}
