package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class AvisHebergementMediaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvisHebergementMedia.class);
        AvisHebergementMedia avisHebergementMedia1 = new AvisHebergementMedia();
        avisHebergementMedia1.setId(1L);
        AvisHebergementMedia avisHebergementMedia2 = new AvisHebergementMedia();
        avisHebergementMedia2.setId(avisHebergementMedia1.getId());
        assertThat(avisHebergementMedia1).isEqualTo(avisHebergementMedia2);
        avisHebergementMedia2.setId(2L);
        assertThat(avisHebergementMedia1).isNotEqualTo(avisHebergementMedia2);
        avisHebergementMedia1.setId(null);
        assertThat(avisHebergementMedia1).isNotEqualTo(avisHebergementMedia2);
    }
}
