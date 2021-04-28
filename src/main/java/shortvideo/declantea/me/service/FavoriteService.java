package shortvideo.declantea.me.service;

import shortvideo.declantea.me.entity.Favorite;
import shortvideo.declantea.me.entity.ShortVideo;
import shortvideo.declantea.me.model.FavoriteInfo;

import java.util.List;
import java.util.UUID;

public interface FavoriteService {

    Favorite saveFavorite(FavoriteInfo favoriteInfo);
    Favorite saveFavorite(UUID videoId, String userId);

    Favorite cancelFavorite(FavoriteInfo favoriteInfo);
    Favorite cancelFavorite(UUID videoId, String userId);

    List<Favorite> getFavoritesByUserId(long userId);

    long getFavoriteCounter(UUID videoId);
    long getFavoriteCounter(ShortVideo shortVideo);

}
