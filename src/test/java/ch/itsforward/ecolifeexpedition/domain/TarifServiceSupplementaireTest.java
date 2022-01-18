package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class TarifServiceSupplementaireTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarifServiceSupplementaire.class);
        TarifServiceSupplementaire tarifServiceSupplementaire1 = new TarifServiceSupplementaire();
        tarifServiceSupplementaire1.setId(1L);
        TarifServiceSupplementaire tarifServiceSupplementaire2 = new TarifServiceSupplementaire();
        tarifServiceSupplementaire2.setId(tarifServiceSupplementaire1.getId());
        assertThat(tarifServiceSupplementaire1).isEqualTo(tarifServiceSupplementaire2);
        tarifServiceSupplementaire2.setId(2L);
        assertThat(tarifServiceSupplementaire1).isNotEqualTo(tarifServiceSupplementaire2);
        tarifServiceSupplementaire1.setId(null);
        assertThat(tarifServiceSupplementaire1).isNotEqualTo(tarifServiceSupplementaire2);
    }
}
