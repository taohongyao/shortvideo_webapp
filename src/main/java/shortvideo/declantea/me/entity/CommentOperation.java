package shortvideo.declantea.me.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import shortvideo.declantea.me.Enum.CommentStateEnum;
import shortvideo.declantea.me.Enum.VideoStateEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "comment_operation_table")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CommentOperation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_operation_id", nullable = false, updatable = false)
    private long commentId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(name = "history_context", nullable = false)
    private String commentContext;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserAccount operator;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "operation_state", nullable = false)
    private CommentStateEnum commentStateEnum;

}
