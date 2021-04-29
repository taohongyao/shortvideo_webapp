package shortvideo.declantea.me.service;

import shortvideo.declantea.me.entity.Favorite;
import shortvideo.declantea.me.entity.ShortVideo;
import shortvideo.declantea.me.model.FavoriteInfo;

import java.util.List;
import java.util.UUID;

public interface FavoriteService {

    Favorite saveFavorite(FavoriteInfo favoriteInfo);
    Favorite saveFavorite(String videoId, long userId);

    Favorite saveFavorite(String videoId, String userName);

    Favorite getFavoriteByVideoIdAndUserId(String videoId, long userId);

    Favorite getFavoriteByVideoIdAndUserName(String videoId, String userName);

    Favorite cancelFavorite(FavoriteInfo favoriteInfo);
    Favorite cancelFavorite(String videoId, long userId);

    List<Favorite> getFavoritesByUserId(long userId);

    Favorite cancelFavorite(String videoId, String userName);

    FavoriteInfo convertFavorite2FavoriteInfo(Favorite favorite);

    long getFavoriteCounter(String videoId);
    long getFavoriteCounter(ShortVideo shortVideo);

}
