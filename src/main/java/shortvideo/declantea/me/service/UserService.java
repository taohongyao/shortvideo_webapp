package shortvideo.declantea.me.service;


import shortvideo.declantea.me.entity.UserAccount;
import shortvideo.declantea.me.model.UserAccountInfo;
import shortvideo.declantea.me.Enum.AuthorityEnum;
import shortvideo.declantea.me.model.UserInfo;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserAccount register(UserAccount userAccount);

    boolean checkGRecaptchaResponse(UserAccountInfo userAccountInfo) throws IOException;

    UserInfo convertUserAccount2UserInfo(UserAccount userAccount);

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
