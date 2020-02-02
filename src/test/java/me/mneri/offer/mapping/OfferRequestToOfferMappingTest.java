package me.mneri.offer.mapping;

import lombok.val;
import me.mneri.offer.dto.OfferRequest;
import me.mneri.offer.entity.Offer;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Date;

import static java.lang.System.currentTimeMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for {@link OfferRequest} to {@link Offer} mapping.
 *
 * @author mneri
 */
@ActiveProfiles("test")
@SpringBootTest
class OfferRequestToOfferMappingTest {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Create a test {@link OfferRequest}.
     *
     * @return The offer.
     */
    private OfferRequest createTestOfferPostRequest() {
        OfferRequest request = new OfferRequest();
        request.setTitle("Bazinga");
        request.setDescription("Awesome");
        request.setPrice(new BigDecimal("100.00"));
        request.setCurrency("GBP");
        request.setEnd(new Date(currentTimeMillis() + 30 * 24 * 60 * 60 * 1000L));
        return request;
    }

    /**
     * Test the correct initialization of all the fields after the mapping.
     */
    @Test
    void givenOfferPostRequest_whenOfferPostRequestIsMappedToOffer_thenAllFieldsAreCorrectlyInitialized() {
        // Given
        val request = createTestOfferPostRequest();

        // When
        val offer = modelMapper.map(request, Offer.class);

        // Then
        assertNotNull(offer.getId());
        assertEquals(request.getTitle(), offer.getTitle());
        assertEquals(request.getDescription(), offer.getDescription());
        assertEquals(request.getPrice(), offer.getPrice());
        assertEquals(request.getCurrency(), offer.getCurrency());
        assertEquals(request.getEnd(), offer.getEnd());
    }
}