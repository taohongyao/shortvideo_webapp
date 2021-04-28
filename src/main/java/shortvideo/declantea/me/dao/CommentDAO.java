package shortvideo.declantea.me.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
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

}
