package shortvideo.declantea.me.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import shortvideo.declantea.me.Enum.VideoStateEnum;
import shortvideo.declantea.me.entity.ShortVideo;

import java.util.List;

@Repository
public class ShortVideoDAO extends AbstractHibernateDao<ShortVideo,String>{

    public List<ShortVideo> findAllShortVideoListByUserId(long userId){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("From ShortVideo  shortvideo where shortvideo.userAccount.id=:userId and shortvideo.videoState=:videoState");
        query.setParameter("userId",userId);
        return query.list();
    }


    public List<ShortVideo> findAllApprovedShortVideoListByKeyword(String keyword){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("From ShortVideo  shortvideo where shortvideo.videoTitle like :keyWord and shortvideo.videoState=:videoState");
        query.setParameter("keyWord","%"+keyword+"%");
        query.setParameter("videoState",VideoStateEnum.Approve);
        return query.list();
    }

    public List<ShortVideo> findNotDeletedShortVideoListByUserId(long userId){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("From ShortVideo  shortvideo where shortvideo.userAccount.id=:userId and shortvideo.videoState=:videoState");
        query.setParameter("userId",userId);
        query.setParameter("videoState",VideoStateEnum.Delete);
        return query.list();
    }

    public List<ShortVideo> findPendingShortVideoListByUserId(long userId){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("From ShortVideo  shortvideo where shortvideo.userAccount.id=:userId and shortvideo.videoState=:videoState");
        query.setParameter("userId",userId);
        query.setParameter("videoState",VideoStateEnum.Pending);
        return query.list();
    }

    public List<ShortVideo> findNotApproveShortVideoListByUserId(long userId){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("From ShortVideo  shortvideo where shortvideo.userAccount.id=:userId and shortvideo.videoState=:videoState");
        query.setParameter("userId",userId);
        query.setParameter("videoState",VideoStateEnum.NotApprove);
        return query.list();
    }

    public List<ShortVideo> findApproveShortVideoListByUserId(long userId){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("From ShortVideo  shortvideo where shortvideo.userAccount.id=:userId and shortvideo.videoState=:videoState");
        query.setParameter("userId",userId);
        query.setParameter("videoState",VideoStateEnum.Approve);
        return query.list();
    }

    public List<ShortVideo> findAllNotDeletedShortVideo(){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("From ShortVideo s where s.videoState!=:state");
        query.setParameter("state",VideoStateEnum.Delete);
        return query.list();
    }


    public List<ShortVideo> findAllApproveShortVideo(){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("From ShortVideo s where s.videoState=:state");
        query.setParameter("state",VideoStateEnum.Approve);
        return query.list();
    }

    public List<ShortVideo> findAllNotApproveShortVideo(){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("From ShortVideo s where s.videoState=:state");
        query.setParameter("state",VideoStateEnum.NotApprove);
        return query.list();
    }

    public List<ShortVideo> findAllPendingShortVideo(){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("From ShortVideo s where s.videoState=:state");
        query.setParameter("state",VideoStateEnum.Pending);
        return query.list();
    }

    public List<ShortVideo> findAllDeletedShortVideo(){
        Session session = this.getCurrentSession();
        Query query=session.createQuery("From ShortVideo s where s.videoState=:state");
        query.setParameter("state",VideoStateEnum.Delete);
        return query.list();
    }

    public ShortVideo makeVideoDeleteState(String videoId){
        ShortVideo shortVideo=findOne(videoId);
        shortVideo.setVideoState(VideoStateEnum.Delete);
        return shortVideo;
    }
    public ShortVideo makeVideoApprovedState(String videoId){
        ShortVideo shortVideo=findOne(videoId);
        shortVideo.setVideoState(VideoStateEnum.Approve);
        return shortVideo;
    }
    public ShortVideo makeVideoNotApprovedState(String videoId){
        ShortVideo shortVideo=findOne(videoId);
        shortVideo.setVideoState(VideoStateEnum.NotApprove);
        return shortVideo;
    }

    public ShortVideo makeVideoPendingState(String videoId){
        ShortVideo shortVideo=findOne(videoId);
        shortVideo.setVideoState(VideoStateEnum.Pending);
        return shortVideo;
    }
}
