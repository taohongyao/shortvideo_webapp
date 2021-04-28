package shortvideo.declantea.me.service;

import shortvideo.declantea.me.entity.Comment;
import shortvideo.declantea.me.entity.ShortVideo;
import shortvideo.declantea.me.model.CommentInfo;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    Comment saveComment(CommentInfo comment);
    Comment getComment(Comment comment);
    Comment getCommentByCommentId(long commentId);

    List<Comment> getCommentsByVideoId(String uuid);
    List<Comment> getCommentsByUserId(long userId);

    List<Comment> getNotDeletedCommentsByVideoId(String uuid);
    List<Comment> getNotDeletedCommentsByUserId(long userId);

    Comment makeCommentDeleteState(Comment comment);
    Comment makeCommentDeleteStateById(long commentId);

    long getCommentCounterByVideoId(String uuid);
    long getCommentCounterByVideoId(ShortVideo shortVideo);

}
