package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class TarifHebergmentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarifHebergment.class);
        TarifHebergment tarifHebergment1 = new TarifHebergment();
        tarifHebergment1.setId(1L);
        TarifHebergment tarifHebergment2 = new TarifHebergment();
        tarifHebergment2.setId(tarifHebergment1.getId());
        assertThat(tarifHebergment1).isEqualTo(tarifHebergment2);
        tarifHebergment2.setId(2L);
        assertThat(tarifHebergment1).isNotEqualTo(tarifHebergment2);
        tarifHebergment1.setId(null);
        assertThat(tarifHebergment1).isNotEqualTo(tarifHebergment2);
    }
}
