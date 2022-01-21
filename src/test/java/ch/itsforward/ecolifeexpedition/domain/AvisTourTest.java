package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class AvisTourTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvisTour.class);
        AvisTour avisTour1 = new AvisTour();
        avisTour1.setId(1L);
        AvisTour avisTour2 = new AvisTour();
        avisTour2.setId(avisTour1.getId());
        assertThat(avisTour1).isEqualTo(avisTour2);
        avisTour2.setId(2L);
        assertThat(avisTour1).isNotEqualTo(avisTour2);
        avisTour1.setId(null);
        assertThat(avisTour1).isNotEqualTo(avisTour2);
    }
}
