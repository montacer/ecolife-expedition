package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class ReservationTourTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReservationTour.class);
        ReservationTour reservationTour1 = new ReservationTour();
        reservationTour1.setId(1L);
        ReservationTour reservationTour2 = new ReservationTour();
        reservationTour2.setId(reservationTour1.getId());
        assertThat(reservationTour1).isEqualTo(reservationTour2);
        reservationTour2.setId(2L);
        assertThat(reservationTour1).isNotEqualTo(reservationTour2);
        reservationTour1.setId(null);
        assertThat(reservationTour1).isNotEqualTo(reservationTour2);
    }
}
