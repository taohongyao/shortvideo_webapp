package shortvideo.declantea.me.service;


import shortvideo.declantea.me.entity.UserAccount;
import shortvideo.declantea.me.model.UserAccountInfo;
import shortvideo.declantea.me.security.authority.AuthorityEnum;

import java.util.List;

public interface UserService {
    UserAccount register(UserAccount userAccount);

    UserAccount registerCustomer(UserAccountInfo userAccountInfo);

    UserAccount registerProductManager(UserAccountInfo userAccountInfo);

    UserAccount getUserAccountByUsername(String username);

    String getDisplayNameByUsername(String username);

    UserAccount updateDisplayName(String username, String newDisplayName);

    UserAccount changePassword(String username, String password);

    List<UserAccount> getAllByAuthority(AuthorityEnum authorityEnum);

    List<String> getAllProductManagerUsername();

    void deleteUserAccount(UserAccount userAccount);

    Boolean testPassword(String username, String password);
}
