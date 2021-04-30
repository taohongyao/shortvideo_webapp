package shortvideo.declantea.me.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import shortvideo.declantea.me.Enum.CommentStateEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "comment_table")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false, updatable = false)
    private Long commentId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "short_video_id")
    private ShortVideo shortVideo;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserAccount posterUser;

    @Column(name = "context", nullable = false,length = 65535, columnDefinition="TEXT")
    @Type(type="text")
    private String commentContext;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "comment_state", nullable = false)
    private CommentStateEnum commentState;

}
