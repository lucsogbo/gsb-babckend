package bj.dgi.GSBBackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken  implements Serializable {

    private static final int EXPIRATION = 72;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = bj.dgi.GSBBackend.entities.User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expiryDate ;

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, EXPIRATION);
        this.expiryDate = calendar.getTime();

    }

    public PasswordResetToken(String token, bj.dgi.GSBBackend.entities.User user) {
        this.token = token;
        this.user = user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordResetToken that = (PasswordResetToken) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(token, that.token) &&
                Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, expiryDate);
    }
}
