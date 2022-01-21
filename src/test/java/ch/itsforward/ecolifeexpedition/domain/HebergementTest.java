package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class HebergementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hebergement.class);
        Hebergement hebergement1 = new Hebergement();
        hebergement1.setId(1L);
        Hebergement hebergement2 = new Hebergement();
        hebergement2.setId(hebergement1.getId());
        assertThat(hebergement1).isEqualTo(hebergement2);
        hebergement2.setId(2L);
        assertThat(hebergement1).isNotEqualTo(hebergement2);
        hebergement1.setId(null);
        assertThat(hebergement1).isNotEqualTo(hebergement2);
    }
}
