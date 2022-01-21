package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class TypeTransfertTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeTransfert.class);
        TypeTransfert typeTransfert1 = new TypeTransfert();
        typeTransfert1.setId(1L);
        TypeTransfert typeTransfert2 = new TypeTransfert();
        typeTransfert2.setId(typeTransfert1.getId());
        assertThat(typeTransfert1).isEqualTo(typeTransfert2);
        typeTransfert2.setId(2L);
        assertThat(typeTransfert1).isNotEqualTo(typeTransfert2);
        typeTransfert1.setId(null);
        assertThat(typeTransfert1).isNotEqualTo(typeTransfert2);
    }
}
