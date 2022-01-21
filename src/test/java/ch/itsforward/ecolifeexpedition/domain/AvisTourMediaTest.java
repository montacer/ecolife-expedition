package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class AvisTourMediaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvisTourMedia.class);
        AvisTourMedia avisTourMedia1 = new AvisTourMedia();
        avisTourMedia1.setId(1L);
        AvisTourMedia avisTourMedia2 = new AvisTourMedia();
        avisTourMedia2.setId(avisTourMedia1.getId());
        assertThat(avisTourMedia1).isEqualTo(avisTourMedia2);
        avisTourMedia2.setId(2L);
        assertThat(avisTourMedia1).isNotEqualTo(avisTourMedia2);
        avisTourMedia1.setId(null);
        assertThat(avisTourMedia1).isNotEqualTo(avisTourMedia2);
    }
}
