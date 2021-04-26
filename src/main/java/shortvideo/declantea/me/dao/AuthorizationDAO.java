package shortvideo.declantea.me.dao;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import shortvideo.declantea.me.entity.Authorization;

import java.util.Date;

@Repository
public class AuthorizationDAO extends AbstractHibernateDao<Authorization> implements PersistentTokenRepository {

    public Authorization findByUsername(String username) {
        Session session = this.getCurrentSession();
        Criteria crit = session.createCriteria(Authorization.class);
        crit.add(Restrictions.eq("USERNAME", username));
        return (Authorization) crit.uniqueResult();
    }

    public Authorization findBySeries(String series) {
        return this.getCurrentSession().find(Authorization.class, series);
    }

    public void deleteByUsername(String userName) {
        this.delete(this.findByUsername(userName));
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        Authorization rm = new Authorization()
                .setSeries(token.getSeries())
                .setUsername(token.getUsername())
                .setToken(token.getTokenValue())
                .setLastUsed(token.getDate());
        this.create(rm);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        Authorization rememberMe = this.findBySeries(series);
        rememberMe.setToken(tokenValue);
        rememberMe.setLastUsed(lastUsed);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        Authorization rememberMe = this.findBySeries(seriesId);
        if (rememberMe != null) {
            return new PersistentRememberMeToken(rememberMe.getUsername(),
                    rememberMe.getSeries(), rememberMe.getToken(), rememberMe.getLastUsed());
        }
        return null;
    }

    @Override
    public void removeUserTokens(String username) {
        this.deleteByUsername(username);
    }
}
