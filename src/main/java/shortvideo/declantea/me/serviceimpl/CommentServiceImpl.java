package shortvideo.declantea.me.serviceimpl;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shortvideo.declantea.me.dao.CommentDAO;
import shortvideo.declantea.me.entity.Comment;
import shortvideo.declantea.me.entity.ShortVideo;
import shortvideo.declantea.me.model.CommentInfo;
import shortvideo.declantea.me.service.CommentService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Getter(AccessLevel.PROTECTED)
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDAO commentDAO;
    @Override
    public Comment saveComment(CommentInfo comment) {
        return null;
    }

    @Override
    public Comment getComment(Comment comment) {
        return null;
    }

    @Override
    public Comment getCommentByCommentId(long commentId) {
        return null;
    }

    @Override
    public List<Comment> getCommentsByVideoId(String uuid) {
        return null;
    }


    @Override
    public List<Comment> getCommentsByUserId(long userId) {
        return null;
    }

    @Override
    public List<Comment> getNotDeletedCommentsByVideoId(String uuid) {
        return null;
    }


    @Override
    public List<Comment> getNotDeletedCommentsByUserId(long userId) {
        return null;
    }

    @Override
    public Comment makeCommentDeleteState(Comment comment) {
        return null;
    }

    @Override
    public Comment makeCommentDeleteStateById(long commentId) {
        return null;
    }

    @Override
    public long getCommentCounterByVideoId(String uuid) {
        return commentDAO.getCommentCountByVideoId(uuid);
    }

    @Override
    public long getCommentCounterByVideoId(ShortVideo shortVideo) {
        return commentDAO.getCommentCountByVideoId(shortVideo.getVideoId());
    }
}
