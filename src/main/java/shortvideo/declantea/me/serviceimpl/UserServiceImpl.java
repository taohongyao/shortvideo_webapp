package shortvideo.declantea.me.serviceimpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shortvideo.declantea.me.dao.UserAccountDAO;
import shortvideo.declantea.me.entity.UserAccount;
import shortvideo.declantea.me.exception.NoSuchUsername;
import shortvideo.declantea.me.exception.UsernameAlreadyExistException;
import shortvideo.declantea.me.model.UserAccountInfo;
import shortvideo.declantea.me.Enum.AuthorityEnum;
import shortvideo.declantea.me.model.UserInfo;
import shortvideo.declantea.me.service.UserService;
import shortvideo.declantea.me.util.URLRequest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Getter(AccessLevel.PROTECTED)
public class UserServiceImpl implements UserService {

    private UserAccountDAO userAccountDAO;

    private PasswordEncoder passwordEncoder;


    private String gRecaptchaCheckUrl="https://www.google.com/recaptcha/api/siteverify";
    @Value("${RECAPTCHA_SECRET}")
    private String recaptChaSecrete;

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserAccountDAO(UserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    @Override
    public UserAccount register(UserAccount userAccount) throws UsernameAlreadyExistException {
        if (this.userAccountDAO.findAll().stream().map(UserAccount::getUsername).collect(Collectors.toSet()).contains(userAccount.getUsername()))
            throw new UsernameAlreadyExistException();
        return this.getUserAccountDAO().create(userAccount);
    }

    @Override
    public boolean checkGRecaptchaResponse(UserAccountInfo userAccountInfo) throws IOException {
        String response=userAccountInfo.getGRecaptchaResponse();
        if(response!=null){
            Map<String,String> parameters=new HashMap<>();
            parameters.put("secret",recaptChaSecrete);
            parameters.put("response",response);
            parameters.put("remoteip", InetAddress.getLocalHost().getHostAddress());
            String data=URLRequest.postRequest(parameters,gRecaptchaCheckUrl);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(data);
            boolean success=actualObj.get("success").asBoolean();
            if(success){
                return true;
            }else {
                throw new RuntimeException(actualObj.get("error-codes").asText());
            }
        }
        return false;
    }

    @Override
    public UserInfo convertUserAccount2UserInfo(UserAccount userAccount){
        return new UserInfo().setUserID(userAccount.getUserID())
                .setUsername(userAccount.getUsername())
                .setDisplayName(userAccount.getDisplayName());
    }

    @Override
    public UserAccount registerCustomer(UserAccountInfo userAccountInfo) {
        UserAccount userAccount = new UserAccount().setUsername(userAccountInfo.getUsername())
                .setPassword(passwordEncoder.encode(userAccountInfo.getPassword()))
                .setDisplayName(userAccountInfo.getDisplayName()).setAuthority(AuthorityEnum.Customer);
        return register(userAccount);
    }

    @Override
    public UserAccount registerProductManager(UserAccountInfo userAccountInfo) {
        UserAccount userAccount = new UserAccount().setUsername(userAccountInfo.getUsername())
                .setPassword(userAccountInfo.getPassword())
                .setDisplayName(userAccountInfo.getDisplayName()).setAuthority(AuthorityEnum.Manager);
        return register(userAccount);
    }

    @Override
    public UserAccount getUserAccountByUsername(String username) {
        return this.getUserAccountDAO().findByUsername(username);
    }

    @Override
    public String getDisplayNameByUsername(String username) {
        return Optional.ofNullable(this.getUserAccountDAO().findByUsername(username))
                .map(UserAccount::getDisplayName)
                .orElse(null);
    }

    @Override
    public UserAccount updateDisplayName(String username, String newDisplayName) {
        return Optional.ofNullable(this.userAccountDAO.findByUsername(username)).orElseThrow(NoSuchUsername::new).setDisplayName(newDisplayName);
    }

    @Override
    public UserAccount changePassword(String username, String password) {
        return Optional.ofNullable(this.userAccountDAO.findByUsername(username)).map((userAccount -> userAccount.setPassword(password))).orElse(null);
    }

    @Override
    public List<UserAccount> getAllByAuthority(AuthorityEnum authorityEnum) {
        return this.getUserAccountDAO().findAllByAuthority(authorityEnum);
    }


    @Override
    public List<String> getAllProductManagerUsername() {
        return this.getAllByAuthority(AuthorityEnum.Manager).stream().map(UserAccount::getUsername).collect(Collectors.toList());
    }

    @Override
    public void deleteUserAccount(UserAccount userAccount) {
        this.getUserAccountDAO().delete(userAccount);
    }

    @Override
    public Boolean testPassword(String username, String password) {
        return Optional.ofNullable(this.userAccountDAO.findByUsername(username))
                .map(userAccount -> userAccount.getPassword().equals(password))
                .orElse(false);
    }
}
