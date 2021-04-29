package shortvideo.declantea.me.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import shortvideo.declantea.me.Enum.CommentStateEnum;
import shortvideo.declantea.me.entity.Comment;
import shortvideo.declantea.me.entity.Favorite;
import shortvideo.declantea.me.entity.UserAccount;

import java.util.List;

@Repository
public class CommentDAO extends AbstractHibernateDao<Comment,Long>{

    public List<Favorite> getCommentListByUserId(long userId){
        return null;
    }

    public long getCommentCountByVideoId(String videoId){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("select count(*) from Comment comment where comment.shortVideo.id=:videoId");
        query.setParameter("videoId",videoId);
        return (Long)query.uniqueResult();
    }

    public long getActiveCommentCountByVideoId(String videoId){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("select count(*) from Comment comment where comment.shortVideo.id=:videoId and comment.commentState=:commentState");
        query.setParameter("videoId",videoId);
        query.setParameter("commentState",CommentStateEnum.Active);
        return (Long)query.uniqueResult();
    }

    public List<Comment> getAllCommentsByVideoId(String videoId){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("From Comment comment where comment.shortVideo.id=:videoId");
        query.setParameter("videoId",videoId);
        return query.list();
    }

    public List<Comment> getAllCommentsByUserId(Long userId){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("From Comment comment where comment.posterUser.id=:userId");
        query.setParameter("userId",userId);
        return query.list();
    }

    public List<Comment> getAllActiveCommentsByVideoId(String videoId){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("From Comment comment where comment.shortVideo.id=:videoId and comment.commentState = :commentState");
        query.setParameter("videoId",videoId);
        query.setParameter("commentState", CommentStateEnum.Active);
        return query.list();
    }

    public List<Comment> getAllActiveCommentsByUserId(Long userId){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("From Comment comment where comment.posterUser.id=:userId and comment.commentState= :commentState");
        query.setParameter("userId",userId);
        query.setParameter("commentState", CommentStateEnum.Active);
        return query.list();
    }

}
