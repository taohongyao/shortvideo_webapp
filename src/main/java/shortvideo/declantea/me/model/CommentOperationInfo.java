package shortvideo.declantea.me.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CommentOperationInfo {
    private String commentOperationId;
    private String commentId;
    private String historyCommentContext;
    private String commentCreateDate;

    private String commenterUserID;
    private String commenterUsername;
    private String commenterDisplayName;

    private String operatorUserID;
    private String operatorUsername;
    private String operatorDisplayName;
    private String operationCreateDate;
}
