package com.server.booyoungee.domain.review.comment.exception;

import com.server.booyoungee.global.exception.CustomException;

public class DuplicateCommentException extends CustomException {
    public DuplicateCommentException() {
        super(CommentExceptionCode.DUPLICATE_COMMENT);
    }
}
