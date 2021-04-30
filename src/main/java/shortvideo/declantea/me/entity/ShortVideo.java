package shortvideo.declantea.me.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import shortvideo.declantea.me.Enum.VideoStateEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "short_video_table")
@Data
@Accessors(chain = true)
public class ShortVideo implements Serializable {
    @Id
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(generator = "generator")
    @Column(name = "video_id", nullable = false, updatable = false)
    private String videoId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "shortVideo")
    private Set<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shortVideo")
    private Set<Favorite> favorites;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "upload_date")
    private Date modifyDate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "video_state", nullable = false)
    private VideoStateEnum videoState;

    @Column(name = "video_title", nullable = false)
    private String videoTitle;

    @Column(name = "video_description", nullable = false,length = 65535, columnDefinition="TEXT")
    @Type(type="text")
    private String videoDescription;
    @Column(name = "video_path", nullable = false)
    private String videoPath;
    @Column(name = "video_cover_path", nullable = false)
    private String videoCoverPath;

}
