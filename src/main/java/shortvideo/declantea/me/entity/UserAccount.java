package shortvideo.declantea.me.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UpdateTimestamp;
import shortvideo.declantea.me.security.authority.AuthorityEnum;

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
    private Integer userID;

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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserAccount{");
        sb.append("userID=").append(userID);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", displayName='").append(displayName).append('\'');
        sb.append(", authority=").append(authority);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", orders_size=").append(shortVideos.size());
        sb.append('}');
        return sb.toString();
    }
}
