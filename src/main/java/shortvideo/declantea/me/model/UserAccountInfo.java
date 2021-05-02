package shortvideo.declantea.me.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import shortvideo.declantea.me.validationannotation.ValidNameInput;
import shortvideo.declantea.me.validationannotation.ValidPassword;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountInfo {
    @ValidNameInput
    private String username;
    @ValidPassword
    private String password;
    private String retypePassword;
    @ValidNameInput
    private String displayName;

    private String gRecaptchaResponse;
    private String action;

}
