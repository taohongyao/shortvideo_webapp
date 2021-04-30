package shortvideo.declantea.me.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import shortvideo.declantea.me.validationannotation.NoXSSString;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class VideoInfo {
    private String videoId;
    private String videoState;
    @NotNull
    @NoXSSString
    private String videoTitle;
    @NotNull
    @NoXSSString
    private String videoDescription;

    private CommonsMultipartFile videoFile;
    private String videoFilePath;

    private CommonsMultipartFile videoCoverFile;
    private String videoCoverFilePath;

    private String createDate;
    private String userID;
    private String username;
    private String displayName;
    private long favoriteCounter;
    private long commentCounter;
}
