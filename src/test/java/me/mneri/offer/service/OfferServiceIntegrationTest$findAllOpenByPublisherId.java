package me.mneri.offer.service;

import lombok.SneakyThrows;
import lombok.val;
import me.mneri.offer.entity.User;
import me.mneri.offer.exception.UserIdNotFoundException;
import me.mneri.offer.repository.OfferRepository;
import me.mneri.offer.repository.UserRepository;
import me.mneri.offer.specification.OfferSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static me.mneri.offer.service.OfferServiceTestUtil.createClosedTestOffers;
import static me.mneri.offer.service.OfferServiceTestUtil.createNonExpiredTestOffer;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the {@link OfferService#findAllOpenByPublisherId(String)} method.<br/>
 * We test 5 different cases:
 * <ul>
 *     <li>Empty repository;</li>
 *     <li>Repository containing only closed offers by the specified user;</li>
 *     <li>Repository containing only closed offers by another user;</li>
 *     <li>Repository containing a single open offer by the specified user;</li>
 *     <li>Repository containing a single open offer by another user.</li>
 * </ul>
 *
 * @author mneri
 */
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class OfferServiceIntegrationTest$findAllOpenByPublisherId {
    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OfferService offerService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Test the method {@link OfferService#findAllOpenByPublisherId(String)} against an repository containing only
     * closed offers published by the specified user.
     */
    @SneakyThrows
    @Test
    void givenClosedOffersPublishedByUser_whenFindAllOpenByPublisherIdIsCalled_thenNoOfferIsReturned() {
        // Given
        val publisher = new User("user", "secret", passwordEncoder);
        val offers = createClosedTestOffers(publisher);

        userRepository.save(publisher);

        for (val offer : offers) {
            offerRepository.save(offer);
        }

        // When
        val returned = offerService.findAllOpenByPublisherId(publisher.getId());

        // Then
        assertTrue(returned.isEmpty());
    }

    /**
     * Test the method {@link OfferService#findAllOpenByPublisherId(String)} against an repository containing only
     * closed offers published by another user.
     */
    @SneakyThrows
    @Test
    void givenClosedOffersPublishedByAnotherUser_whenFindAllOpenByPublisherIdIsCalled_thenNoOfferIsReturned() {
        // Given
        val publisher = new User("user", "secret", passwordEncoder);
        val other = new User("other", "secret", passwordEncoder);
        val offers = createClosedTestOffers(other);

        userRepository.save(other);

        for (val offer : offers) {
            offerRepository.save(offer);
        }

        // When/Then
        assertThrows(UserIdNotFoundException.class, () -> offerService.findAllOpenByPublisherId(publisher.getId()));
    }

    /**
     * Test the method {@link OfferService#findAllOpenByPublisherId(String)} against an empty repository.
     */
    @SneakyThrows
    @Test
    void givenEmptyRepository_whenFindAllOpenByPublisherIdIsCalled_thenNoOfferIsReturned() {
        // Given
        val publisher = new User("user", "secret", passwordEncoder);

        // When/Then
        assertThrows(UserIdNotFoundException.class, () -> offerService.findAllOpenByPublisherId(publisher.getId()));
    }

    /**
     * Test the method {@link OfferService#findAllOpenByPublisherId(String)} against an repository containing a single
     * open offers published by another user.
     */
    @SneakyThrows
    @Test
    void givenOpenOfferPublishedByAnotherUser_whenFindAllOpenByPublisherIdIsCalled_thenNoOfferIsReturned() {
        // Given
        val publisher = new User("user", "secret", passwordEncoder);
        val other = new User("other", "secret", passwordEncoder);
        val offer = createNonExpiredTestOffer(other);

        userRepository.save(other);
        offerRepository.save(offer);

        // When/Then
        assertThrows(UserIdNotFoundException.class, () -> offerService.findAllOpenByPublisherId(publisher.getId()));
    }

    /**
     * Test the method {@link OfferService#findAllOpenByPublisherId(String)} against an repository containing a single
     * open offers published by the specified user.
     */
    @SneakyThrows
    @Test
    void givenOpenOfferPublishedByUser_whenFindAllOpenByPublisherIdIsCalled_thenOfferIsReturned() {
        // Given
        val publisher = new User("user", "secret", passwordEncoder);
        val offer = createNonExpiredTestOffer(publisher);

        userRepository.save(publisher);
        offerRepository.save(offer);

        // When
        val returned = offerService.findAllOpenByPublisherId(publisher.getId());

        // Then
        assertEquals(1, returned.size());
        assertTrue(returned.contains(offer));
    }
}
