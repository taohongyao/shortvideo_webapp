package shortvideo.declantea.me.serviceimpl;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shortvideo.declantea.me.dao.FavoriteDAO;
import shortvideo.declantea.me.entity.Favorite;
import shortvideo.declantea.me.entity.ShortVideo;
import shortvideo.declantea.me.model.FavoriteInfo;
import shortvideo.declantea.me.service.FavoriteService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Getter(AccessLevel.PROTECTED)
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteDAO favoriteDAO;


    @Override
    public List<Favorite> getFavoritesByUserId(long userId) {
        return favoriteDAO.getFavoriteListByUserId(userId);
    }

    @Override
    public Favorite saveFavorite(FavoriteInfo favoriteInfo) {
        return null;
    }

    @Override
    public Favorite saveFavorite(UUID videoId, String userId) {
        return null;
    }

    @Override
    public Favorite cancelFavorite(FavoriteInfo favoriteInfo) {
        return null;
    }

    @Override
    public Favorite cancelFavorite(UUID videoId, String userId) {
        return null;
    }

    @Override
    public long getFavoriteCounter(UUID videoId) {
        return 0;
    }

    @Override
    public long getFavoriteCounter(ShortVideo shortVideo) {
        return favoriteDAO.getFavoriteCountByVideoId(shortVideo.getVideoId().toString());
    }
}
