package shortvideo.declantea.me.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "favorite_table")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Favorite implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id", nullable = false, updatable = false)
    private long favoriteId;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "short_video_id")
    private ShortVideo shortVideo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserAccount favoriteUser;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

}
