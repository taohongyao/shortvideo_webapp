package shortvideo.declantea.me.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import shortvideo.declantea.me.entity.Favorite;

import java.util.List;

@Repository
public class FavoriteDAO extends AbstractHibernateDao<Favorite,Long>{

    public List<Favorite> getFavoriteListByUserId(long userId){
        Session session=this.getCurrentSession();
        Query query=session.createQuery("From Favorite f where f.favoriteUser=:user");
        query.setParameter("user",userId);
        return query.list();
    }

    public List<Favorite> getFavoriteListByVideoId(String videoId){
        Session session=this.getCurrentSession();
        Query query=session.createQuery("From Favorite f where f.shortVideo=:shortVideo");
        query.setParameter("shortVideo",videoId);
        return query.list();
    }

    public Favorite getFavoriteListByVideoIdAndUserId(String videoId,long userId){
        Session session=this.getCurrentSession();
        Query query=session.createQuery("From Favorite f where f.shortVideo.videoId=:shortVideo and f.favoriteUser.userID=:user");
        query.setParameter("shortVideo",videoId);
        query.setParameter("user",userId);
        return (Favorite) query.uniqueResult();
    }

    public Favorite deleteFavoriteByVideoIdAndUserId(String videoId,long userId){
        Session session=this.getCurrentSession();
        Query query=session.createQuery("From Favorite f where f.shortVideo.videoId=:shortVideo and f.favoriteUser.userID=:user");
        query.setParameter("shortVideo",videoId);
        query.setParameter("user",userId);
        Favorite favorite= (Favorite) query.uniqueResult();
        session.delete(favorite);
        return favorite;
    }

    public long getFavoriteCountByVideoId(String videoId){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("select count(*) from Favorite favorite where favorite.shortVideo.id=:videoId");
        query.setParameter("videoId",videoId);
        return (Long)query.uniqueResult();
    }

}
