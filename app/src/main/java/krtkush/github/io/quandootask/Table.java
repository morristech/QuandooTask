package krtkush.github.io.quandootask;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by kartikeykushwaha on 17/10/17.
 */

@Entity(nameInDb = "tables")
public class Table {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "customer_id")
    private int customerId;

    @Property(nameInDb = "reservation_status")
    private boolean reservationStatus;

    @Generated(hash = 1377778378)
    public Table(Long id, int customerId, boolean reservationStatus) {
        this.id = id;
        this.customerId = customerId;
        this.reservationStatus = reservationStatus;
    }

    @Generated(hash = 752389689)
    public Table() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public boolean getReservationStatus() {
        return this.reservationStatus;
    }

    public void setReservationStatus(boolean reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
}
