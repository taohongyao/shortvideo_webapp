package shortvideo.declantea.me.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import shortvideo.declantea.me.validationannotation.NoXSSString;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CommentInfo {
    private String commentId;
    @NoXSSString
    @NotNull
    private String commentContext;
    private String createDate;
    private String videoId;
    private String userID;
    private String username;
    private String displayName;
    private String commentState;
}
