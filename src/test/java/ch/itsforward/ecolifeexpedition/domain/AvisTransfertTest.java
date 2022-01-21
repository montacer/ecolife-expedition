package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class AvisTransfertTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvisTransfert.class);
        AvisTransfert avisTransfert1 = new AvisTransfert();
        avisTransfert1.setId(1L);
        AvisTransfert avisTransfert2 = new AvisTransfert();
        avisTransfert2.setId(avisTransfert1.getId());
        assertThat(avisTransfert1).isEqualTo(avisTransfert2);
        avisTransfert2.setId(2L);
        assertThat(avisTransfert1).isNotEqualTo(avisTransfert2);
        avisTransfert1.setId(null);
        assertThat(avisTransfert1).isNotEqualTo(avisTransfert2);
    }
}
