package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class ServiceSupplementaireTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceSupplementaire.class);
        ServiceSupplementaire serviceSupplementaire1 = new ServiceSupplementaire();
        serviceSupplementaire1.setId(1L);
        ServiceSupplementaire serviceSupplementaire2 = new ServiceSupplementaire();
        serviceSupplementaire2.setId(serviceSupplementaire1.getId());
        assertThat(serviceSupplementaire1).isEqualTo(serviceSupplementaire2);
        serviceSupplementaire2.setId(2L);
        assertThat(serviceSupplementaire1).isNotEqualTo(serviceSupplementaire2);
        serviceSupplementaire1.setId(null);
        assertThat(serviceSupplementaire1).isNotEqualTo(serviceSupplementaire2);
    }
}
