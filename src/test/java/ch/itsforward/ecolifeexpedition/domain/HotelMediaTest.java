package ch.itsforward.ecolifeexpedition.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.itsforward.ecolifeexpedition.web.rest.TestUtil;

public class HotelMediaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotelMedia.class);
        HotelMedia hotelMedia1 = new HotelMedia();
        hotelMedia1.setId(1L);
        HotelMedia hotelMedia2 = new HotelMedia();
        hotelMedia2.setId(hotelMedia1.getId());
        assertThat(hotelMedia1).isEqualTo(hotelMedia2);
        hotelMedia2.setId(2L);
        assertThat(hotelMedia1).isNotEqualTo(hotelMedia2);
        hotelMedia1.setId(null);
        assertThat(hotelMedia1).isNotEqualTo(hotelMedia2);
    }
}
