package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class DonneurOrdreTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonneurOrdre.class);
        DonneurOrdre donneurOrdre1 = new DonneurOrdre();
        donneurOrdre1.setId(1L);
        DonneurOrdre donneurOrdre2 = new DonneurOrdre();
        donneurOrdre2.setId(donneurOrdre1.getId());
        assertThat(donneurOrdre1).isEqualTo(donneurOrdre2);
        donneurOrdre2.setId(2L);
        assertThat(donneurOrdre1).isNotEqualTo(donneurOrdre2);
        donneurOrdre1.setId(null);
        assertThat(donneurOrdre1).isNotEqualTo(donneurOrdre2);
    }
}
