package com.server.booyoungee.domain.review.comment.application;

import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.review.comment.dao.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
}
