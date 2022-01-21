package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class TypeRegionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeRegion.class);
        TypeRegion typeRegion1 = new TypeRegion();
        typeRegion1.setId(1L);
        TypeRegion typeRegion2 = new TypeRegion();
        typeRegion2.setId(typeRegion1.getId());
        assertThat(typeRegion1).isEqualTo(typeRegion2);
        typeRegion2.setId(2L);
        assertThat(typeRegion1).isNotEqualTo(typeRegion2);
        typeRegion1.setId(null);
        assertThat(typeRegion1).isNotEqualTo(typeRegion2);
    }
}
