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
        Criteria crit=session.createCriteria(Favorite.class);
        crit.add(Restrictions.eq("favoriteUser",userId));
        return crit.list();
    }

    public long getFavoriteCountByVideoId(String videoId){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("select count(*) from Favorite favorite where favorite.shortVideo.id=:videoId");
        query.setParameter("videoId",videoId);
        return (Long)query.uniqueResult();
    }

}
