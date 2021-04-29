package shortvideo.declantea.me.serviceimpl;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shortvideo.declantea.me.dao.FavoriteDAO;
import shortvideo.declantea.me.dao.ShortVideoDAO;
import shortvideo.declantea.me.dao.UserAccountDAO;
import shortvideo.declantea.me.entity.Comment;
import shortvideo.declantea.me.entity.Favorite;
import shortvideo.declantea.me.entity.ShortVideo;
import shortvideo.declantea.me.entity.UserAccount;
import shortvideo.declantea.me.model.CommentInfo;
import shortvideo.declantea.me.model.FavoriteInfo;
import shortvideo.declantea.me.service.FavoriteService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Getter(AccessLevel.PROTECTED)
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteDAO favoriteDAO;
    @Autowired
    private UserAccountDAO userAccountDAO;
    @Autowired
    private ShortVideoDAO shortVideoDAO;

    @Override
    public List<Favorite> getFavoritesByUserId(long userId) {
        return favoriteDAO.getFavoriteListByUserId(userId);
    }

    @Override
    public Favorite saveFavorite(FavoriteInfo favoriteInfo) {
        return null;
    }

    @Override
    public Favorite saveFavorite(String videoId, long userId) {
        Favorite favorite=favoriteDAO.getFavoriteListByVideoIdAndUserId(videoId,userId);
        if(favorite==null){
            favorite=new Favorite()
                    .setShortVideo(shortVideoDAO.findOne(videoId))
                    .setFavoriteUser(userAccountDAO.findOne(userId));
            favorite=favoriteDAO.create(favorite);
        }
        return favorite;
    }

    @Override
    public Favorite saveFavorite(String videoId, String userName) {
        UserAccount userAccount=userAccountDAO.findByUsername(userName);
        Favorite favorite=favoriteDAO.getFavoriteListByVideoIdAndUserId(videoId,userAccount.getUserID());
        if(favorite==null){
            favorite=new Favorite()
                    .setShortVideo(shortVideoDAO.findOne(videoId))
                    .setFavoriteUser(userAccount);
            favorite=favoriteDAO.create(favorite);
        }
        return favorite;
    }
    @Override
    public Favorite getFavoriteByVideoIdAndUserId(String videoId, long userId){
        return favoriteDAO.getFavoriteListByVideoIdAndUserId(videoId,userId);
    }

    @Override
    public Favorite getFavoriteByVideoIdAndUserName(String videoId, String userName){
        return favoriteDAO.getFavoriteListByVideoIdAndUserId(videoId,userAccountDAO.findByUsername(userName).getUserID());
    }

    @Override
    public Favorite cancelFavorite(FavoriteInfo favoriteInfo) {
        return cancelFavorite(favoriteInfo.getShortVideoId(),favoriteInfo.getUserId());
    }

    @Override
    public Favorite cancelFavorite(String videoId, long userId) {
        Favorite favorite=favoriteDAO.getFavoriteListByVideoIdAndUserId(videoId,userId);
        if(favorite!=null){
            favorite=favoriteDAO.deleteFavoriteByVideoIdAndUserId(videoId,userId);
        }
        return favorite;
    }

    @Override
    public Favorite cancelFavorite(String videoId, String userName) {
        UserAccount userAccount=userAccountDAO.findByUsername(userName);
        return cancelFavorite(videoId,userAccount.getUserID());
    }

    @Override
    public FavoriteInfo convertFavorite2FavoriteInfo(Favorite favorite){
        if(favorite==null){
            return new FavoriteInfo();
        }
        return new FavoriteInfo().setUserId(favorite.getFavoriteUser().getUserID())
                .setShortVideoId(favorite.getShortVideo().getVideoId());
    }

    @Override
    public long getFavoriteCounter(String videoId) {
        return favoriteDAO.getFavoriteCountByVideoId(videoId);
    }

    @Override
    public long getFavoriteCounter(ShortVideo shortVideo) {
        return getFavoriteCounter(shortVideo.getVideoId());
    }
}
