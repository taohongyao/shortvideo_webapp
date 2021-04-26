package shortvideo.declantea.me.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

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
    private Integer commentId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "short_video_id")
    private ShortVideo shortVideo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserAccount posterUser;

    @Column(name = "context", nullable = false)
    private String commentContext;

}
