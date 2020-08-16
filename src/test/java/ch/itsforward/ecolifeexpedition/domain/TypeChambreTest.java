package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class TypeChambreTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeChambre.class);
        TypeChambre typeChambre1 = new TypeChambre();
        typeChambre1.setId(1L);
        TypeChambre typeChambre2 = new TypeChambre();
        typeChambre2.setId(typeChambre1.getId());
        assertThat(typeChambre1).isEqualTo(typeChambre2);
        typeChambre2.setId(2L);
        assertThat(typeChambre1).isNotEqualTo(typeChambre2);
        typeChambre1.setId(null);
        assertThat(typeChambre1).isNotEqualTo(typeChambre2);
    }
}
