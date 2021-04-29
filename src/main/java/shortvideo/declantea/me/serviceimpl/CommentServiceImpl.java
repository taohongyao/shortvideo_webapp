package shortvideo.declantea.me.serviceimpl;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shortvideo.declantea.me.Enum.CommentStateEnum;
import shortvideo.declantea.me.dao.CommentDAO;
import shortvideo.declantea.me.dao.ShortVideoDAO;
import shortvideo.declantea.me.dao.UserAccountDAO;
import shortvideo.declantea.me.entity.Comment;
import shortvideo.declantea.me.entity.ShortVideo;
import shortvideo.declantea.me.entity.UserAccount;
import shortvideo.declantea.me.exception.NotAuthorizedDeleteOperation;
import shortvideo.declantea.me.model.CommentInfo;
import shortvideo.declantea.me.model.VideoInfo;
import shortvideo.declantea.me.service.CommentService;
import shortvideo.declantea.me.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Getter(AccessLevel.PROTECTED)
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private UserAccountDAO userAccountDAO;
    @Autowired
    private ShortVideoDAO shortVideoDAO;

    @Override
    public Comment saveComment(CommentInfo comment) {
        Comment commentEntity=new Comment()
                .setCommentContext(comment.getCommentContext())
                .setPosterUser(userAccountDAO.findByUsername(comment.getUsername()))
                .setShortVideo(shortVideoDAO.findOne(comment.getVideoId()))
                .setCommentState(CommentStateEnum.Active);
        commentEntity=commentDAO.create(commentEntity);
        return commentEntity;
    }

    @Override
    public CommentInfo convertComment2CommentInfo(Comment comment){
        return new CommentInfo().setVideoId(comment.getShortVideo().getVideoId())
                .setCommentState(comment.getCommentState().getCommentState())
                .setCommentContext(comment.getCommentContext())
                .setUserID(""+comment.getPosterUser().getUserID())
                .setUsername(comment.getPosterUser().getUsername())
                .setDisplayName(comment.getPosterUser().getDisplayName())
                .setCommentId(""+comment.getCommentId())
                .setCreateDate(Optional.ofNullable(comment.getCreateDate()).orElse(new Date()).toString());
    }

    @Override
    public Comment getComment(Comment comment) {
        return getCommentByCommentId(comment.getCommentId());
    }

    @Override
    public Comment getCommentByCommentId(long commentId) {
        return commentDAO.findOne(commentId);
    }

    @Override
    public List<Comment> getCommentsByVideoId(String uuid) {
        return commentDAO.getAllCommentsByVideoId(uuid);
    }


    @Override
    public List<Comment> getCommentsByUserId(long userId) {
        return commentDAO.getAllCommentsByUserId(userId);
    }

    @Override
    public List<Comment> getActiveCommentsByVideoId(String uuid) {
        return commentDAO.getAllActiveCommentsByVideoId(uuid);
    }

    @Override
    public List<Comment> getActiveCommentsByUserId(long userId) {
        return commentDAO.getAllActiveCommentsByUserId(userId);
    }

    @Override
    public Comment makeCommentDeleteState(Comment comment) {
        comment=commentDAO.findOne(comment.getCommentId()).setCommentState(CommentStateEnum.Delete);
        return comment;
    }

    @Override
    public Comment makeCommentDeleteStateById(long commentId) {
        Comment comment=commentDAO.findOne(commentId).setCommentState(CommentStateEnum.Delete);
        return comment;
    }

    @Override
    public Comment makeCommentDeleteStateByIdWithSameUser(long commentId, long userId){
        Comment comment=getCommentByCommentId(commentId);
        if(comment.getPosterUser().getUserID()!=userId){
            throw new NotAuthorizedDeleteOperation();
        }
        comment.setCommentState(CommentStateEnum.Delete);
        return comment;
    }

    @Override
    public Comment makeCommentDeleteStateByIdWithUserIdRecord(long commentId, long userId){
        Comment comment=getCommentByCommentId(commentId);
        comment.setCommentState(CommentStateEnum.Delete);
        return comment;
    }

    @Override
    public Comment makeCommentDeleteStateByIdWithSameUser(long commentId, String userName){
        UserAccount userAccount=userAccountDAO.findByUsername(userName);
        Comment comment=getCommentByCommentId(commentId);
        if(!comment.getPosterUser().getUserID().equals(userAccount.getUserID())){
            throw new NotAuthorizedDeleteOperation();
        }
        comment.setCommentState(CommentStateEnum.Delete);
        return comment;
    }

    @Override
    public Comment makeCommentDeleteStateByIdWithUserIdRecord(long commentId, String userName){
        Comment comment=getCommentByCommentId(commentId);
        comment.setCommentState(CommentStateEnum.Delete);
        return comment;
    }


    @Override
    public long getCommentCounterByVideoId(String uuid) {
        return commentDAO.getCommentCountByVideoId(uuid);
    }

    @Override
    public long getCommentCounterByVideoId(ShortVideo shortVideo) {
        return getCommentCounterByVideoId(shortVideo.getVideoId());
    }

    @Override
    public long getActiveCommentCounterByVideoId(String uuid) {
        return commentDAO.getActiveCommentCountByVideoId(uuid);
    }

    @Override
    public long getActiveCommentCounterByVideoId(ShortVideo shortVideo) {
        return getActiveCommentCounterByVideoId(shortVideo.getVideoId());
    }
}
