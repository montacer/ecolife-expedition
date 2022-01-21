package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class ReservationHebergementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReservationHebergement.class);
        ReservationHebergement reservationHebergement1 = new ReservationHebergement();
        reservationHebergement1.setId(1L);
        ReservationHebergement reservationHebergement2 = new ReservationHebergement();
        reservationHebergement2.setId(reservationHebergement1.getId());
        assertThat(reservationHebergement1).isEqualTo(reservationHebergement2);
        reservationHebergement2.setId(2L);
        assertThat(reservationHebergement1).isNotEqualTo(reservationHebergement2);
        reservationHebergement1.setId(null);
        assertThat(reservationHebergement1).isNotEqualTo(reservationHebergement2);
    }
}
