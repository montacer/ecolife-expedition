package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class AvisHebergementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvisHebergement.class);
        AvisHebergement avisHebergement1 = new AvisHebergement();
        avisHebergement1.setId(1L);
        AvisHebergement avisHebergement2 = new AvisHebergement();
        avisHebergement2.setId(avisHebergement1.getId());
        assertThat(avisHebergement1).isEqualTo(avisHebergement2);
        avisHebergement2.setId(2L);
        assertThat(avisHebergement1).isNotEqualTo(avisHebergement2);
        avisHebergement1.setId(null);
        assertThat(avisHebergement1).isNotEqualTo(avisHebergement2);
    }
}
