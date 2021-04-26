package shortvideo.declantea.me.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "authorization")
@Data
@Accessors(chain = true)
public class Authorization implements Serializable {
    @Id
    @Column(name = "SERIES")
    private String series;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "TOKEN", nullable = false)
    private String token;

    @Column(name = "LAST_USED", nullable = false)
    private Date lastUsed;
}
