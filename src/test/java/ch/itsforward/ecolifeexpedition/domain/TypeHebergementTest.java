package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class TypeHebergementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeHebergement.class);
        TypeHebergement typeHebergement1 = new TypeHebergement();
        typeHebergement1.setId(1L);
        TypeHebergement typeHebergement2 = new TypeHebergement();
        typeHebergement2.setId(typeHebergement1.getId());
        assertThat(typeHebergement1).isEqualTo(typeHebergement2);
        typeHebergement2.setId(2L);
        assertThat(typeHebergement1).isNotEqualTo(typeHebergement2);
        typeHebergement1.setId(null);
        assertThat(typeHebergement1).isNotEqualTo(typeHebergement2);
    }
}
