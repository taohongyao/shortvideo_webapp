package shortvideo.declantea.me.service;

import shortvideo.declantea.me.entity.Comment;
import shortvideo.declantea.me.entity.ShortVideo;
import shortvideo.declantea.me.model.CommentInfo;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    Comment saveComment(CommentInfo comment);

    CommentInfo convertComment2CommentInfo(Comment comment);

    Comment getComment(Comment comment);
    Comment getCommentByCommentId(long commentId);

    List<Comment> getCommentsByVideoId(String uuid);
    List<Comment> getCommentsByUserId(long userId);

    List<Comment> getActiveCommentsByVideoId(String uuid);

    List<Comment> getActiveCommentsByUserId(long userId);

    Comment makeCommentDeleteState(Comment comment);
    Comment makeCommentDeleteStateById(long commentId);

    Comment makeCommentDeleteStateByIdWithSameUser(long commentId, long userId);

    Comment makeCommentDeleteStateByIdWithUserIdRecord(long commentId, long userId);

    Comment makeCommentDeleteStateByIdWithSameUser(long commentId, String userName);

    Comment makeCommentDeleteStateByIdWithUserIdRecord(long commentId, String userName);

    long getCommentCounterByVideoId(String uuid);
    long getCommentCounterByVideoId(ShortVideo shortVideo);

    long getActiveCommentCounterByVideoId(String uuid);

    long getActiveCommentCounterByVideoId(ShortVideo shortVideo);
}
