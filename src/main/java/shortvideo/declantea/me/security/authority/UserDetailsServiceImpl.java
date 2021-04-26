package shortvideo.declantea.me.security.authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shortvideo.declantea.me.dao.UserAccountDAO;
import shortvideo.declantea.me.entity.UserAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserAccountDAO userAccountDAO;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setUserDAO(UserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        BCryptPasswordEncoder encoder = passwordEncoder;
        UserAccount userAccount = Optional.ofNullable(this.userAccountDAO.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException(username + " not exist"));
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
//        System.out.println(userAccount);
        grantedAuthorityList.add(userAccount.getAuthority());
        User user = new User(userAccount.getUsername(), encoder.encode(userAccount.getPassword()), grantedAuthorityList);
//        System.out.println(user);
        return user;
    }


}
