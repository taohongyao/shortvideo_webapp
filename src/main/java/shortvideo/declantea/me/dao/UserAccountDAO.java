package shortvideo.declantea.me.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import shortvideo.declantea.me.entity.UserAccount;
import shortvideo.declantea.me.Enum.AuthorityEnum;

import java.util.List;

@Repository
public class UserAccountDAO extends AbstractHibernateDao<UserAccount, Long> {
    public UserAccount findByUsername(String username) {
        Session session = this.getCurrentSession();
        Query query=session.createQuery("From UserAccount  u where u.username=:username");
        query.setParameter("username",username);
        return (UserAccount) query.uniqueResult();
    }

    public List<UserAccount> findAllByAuthority(AuthorityEnum authorityEnum) {
        Session session = this.getCurrentSession();
        Query query=session.createQuery("From UserAccount  u where u.authority=:authority");
        query.setParameter("authority",authorityEnum);
        return query.list();
    }
}
