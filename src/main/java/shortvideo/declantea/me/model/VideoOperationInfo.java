package shortvideo.declantea.me.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class VideoOperationInfo {
    private String videoOperationId;

    private String videoId;
    private String videoState;
    private String historyVideoTitle;
    private String historyVideoDescription;
    private String historyVideoFileName;
    private String videoPath;
    private String videoCoverPath;
    private int favoriteCounter;
    private int commentCounter;

    private String createrUserID;
    private String createrUsername;
    private String createrDisplayName;

    private String operatorUserID;
    private String operatorUsername;
    private String operatorDisplayName;
    private String operationCreateDate;
}
