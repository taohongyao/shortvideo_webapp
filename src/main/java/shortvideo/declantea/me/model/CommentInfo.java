package shortvideo.declantea.me.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CommentInfo {
    private String commentId;
    private String commentContext;
    private String createDate;
    private String userID;
    private String username;
    private String displayName;
}
