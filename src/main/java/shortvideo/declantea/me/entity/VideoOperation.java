package shortvideo.declantea.me.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import shortvideo.declantea.me.Enum.VideoStateEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "video_operation_table")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class VideoOperation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_operation_id", nullable = false, updatable = false)
    private long commentId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "short_video_id")
    private ShortVideo shortVideo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserAccount operator;

    @Column(name = "history_video_title", nullable = false)
    private String videoTitle;

    @Column(name = "history_video_description", nullable = false)
    private String videoDescription;

    @Column(name = "history_video_path", nullable = false)
    private String videoPath;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "operation_state", nullable = false)
    private VideoStateEnum videoStateEnum;

}
