package me.mneri.offer.repository;

import me.mneri.offer.entity.Offer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends CrudRepository<Offer, String>, JpaSpecificationExecutor<Offer> {
}
