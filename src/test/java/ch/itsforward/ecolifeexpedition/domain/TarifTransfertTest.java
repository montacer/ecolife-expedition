package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class TarifTransfertTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarifTransfert.class);
        TarifTransfert tarifTransfert1 = new TarifTransfert();
        tarifTransfert1.setId(1L);
        TarifTransfert tarifTransfert2 = new TarifTransfert();
        tarifTransfert2.setId(tarifTransfert1.getId());
        assertThat(tarifTransfert1).isEqualTo(tarifTransfert2);
        tarifTransfert2.setId(2L);
        assertThat(tarifTransfert1).isNotEqualTo(tarifTransfert2);
        tarifTransfert1.setId(null);
        assertThat(tarifTransfert1).isNotEqualTo(tarifTransfert2);
    }
}
