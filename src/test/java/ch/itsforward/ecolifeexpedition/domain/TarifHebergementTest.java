package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class TarifHebergementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarifHebergement.class);
        TarifHebergement tarifHebergement1 = new TarifHebergement();
        tarifHebergement1.setId(1L);
        TarifHebergement tarifHebergement2 = new TarifHebergement();
        tarifHebergement2.setId(tarifHebergement1.getId());
        assertThat(tarifHebergement1).isEqualTo(tarifHebergement2);
        tarifHebergement2.setId(2L);
        assertThat(tarifHebergement1).isNotEqualTo(tarifHebergement2);
        tarifHebergement1.setId(null);
        assertThat(tarifHebergement1).isNotEqualTo(tarifHebergement2);
    }
}
