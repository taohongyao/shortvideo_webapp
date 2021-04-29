package shortvideo.declantea.me.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UpdateTimestamp;
import shortvideo.declantea.me.Enum.AuthorityEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "user_account_table")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userID;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "authority", nullable = false)
    private AuthorityEnum authority = AuthorityEnum.Customer;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private Date modifyDate;

    @OneToMany(mappedBy = "userAccount")
    private Set<ShortVideo> shortVideos;

    @OneToMany(mappedBy = "posterUser")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "favoriteUser")
    private Set<Favorite> favorites;

}
