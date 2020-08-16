package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class TypeCircuitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeCircuit.class);
        TypeCircuit typeCircuit1 = new TypeCircuit();
        typeCircuit1.setId(1L);
        TypeCircuit typeCircuit2 = new TypeCircuit();
        typeCircuit2.setId(typeCircuit1.getId());
        assertThat(typeCircuit1).isEqualTo(typeCircuit2);
        typeCircuit2.setId(2L);
        assertThat(typeCircuit1).isNotEqualTo(typeCircuit2);
        typeCircuit1.setId(null);
        assertThat(typeCircuit1).isNotEqualTo(typeCircuit2);
    }
}
