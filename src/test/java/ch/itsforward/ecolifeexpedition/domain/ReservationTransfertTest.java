package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class ReservationTransfertTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReservationTransfert.class);
        ReservationTransfert reservationTransfert1 = new ReservationTransfert();
        reservationTransfert1.setId(1L);
        ReservationTransfert reservationTransfert2 = new ReservationTransfert();
        reservationTransfert2.setId(reservationTransfert1.getId());
        assertThat(reservationTransfert1).isEqualTo(reservationTransfert2);
        reservationTransfert2.setId(2L);
        assertThat(reservationTransfert1).isNotEqualTo(reservationTransfert2);
        reservationTransfert1.setId(null);
        assertThat(reservationTransfert1).isNotEqualTo(reservationTransfert2);
    }
}
