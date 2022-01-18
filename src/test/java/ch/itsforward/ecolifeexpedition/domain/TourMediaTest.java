package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class TourMediaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TourMedia.class);
        TourMedia tourMedia1 = new TourMedia();
        tourMedia1.setId(1L);
        TourMedia tourMedia2 = new TourMedia();
        tourMedia2.setId(tourMedia1.getId());
        assertThat(tourMedia1).isEqualTo(tourMedia2);
        tourMedia2.setId(2L);
        assertThat(tourMedia1).isNotEqualTo(tourMedia2);
        tourMedia1.setId(null);
        assertThat(tourMedia1).isNotEqualTo(tourMedia2);
    }
}
