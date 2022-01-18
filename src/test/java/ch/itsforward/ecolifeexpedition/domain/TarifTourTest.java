package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class TarifTourTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarifTour.class);
        TarifTour tarifTour1 = new TarifTour();
        tarifTour1.setId(1L);
        TarifTour tarifTour2 = new TarifTour();
        tarifTour2.setId(tarifTour1.getId());
        assertThat(tarifTour1).isEqualTo(tarifTour2);
        tarifTour2.setId(2L);
        assertThat(tarifTour1).isNotEqualTo(tarifTour2);
        tarifTour1.setId(null);
        assertThat(tarifTour1).isNotEqualTo(tarifTour2);
    }
}
