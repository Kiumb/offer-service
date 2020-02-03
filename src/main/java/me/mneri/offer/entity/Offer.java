package me.mneri.offer.entity;

import lombok.*;
import me.mneri.offer.validator.Description;
import me.mneri.offer.validator.Title;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

/**
 * ORM for {@code offer} table.
 * <p>
 * The id is immutable and is assigned upon creation. ORM objects are compared by their id and not their state.
 *
 * @author mneri
 */
@Data
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = PROTECTED)
@ToString
public class Offer {
    @Builder
    private Offer(String title, String description, BigDecimal price, String currency, long ttl, User publisher) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.createTime = new Date();
        this.endTime = new Date(createTime.getTime() + ttl);
        this.publisher = publisher;
    }

    @Transient
    public long getTtl() {
        return endTime.getTime() - createTime.getTime();
    }

    @Transient
    public void setTtl(long ttl) {
        endTime = new Date(createTime.getTime() + ttl);
    }

    @Id
    @NonNull
    @NotBlank
    @Setter(PROTECTED)
    private String id;

    @Column
    @NonNull
    @Title
    private String title;

    @Column
    @NonNull
    @Description
    private String description;

    @Column(precision = 16, scale = 2)
    @NonNull
    private BigDecimal price;

    @Column
    @NonNull
    private String currency;

    @Column(name = "create_time")
    @NonNull
    @Setter(PROTECTED)
    private Date createTime;

    @Column(name = "end_time")
    @NonNull
    @Setter(PROTECTED)
    private Date endTime;

    @Column
    private boolean canceled;

    @ManyToOne(fetch = LAZY)
    @NonNull
    private User publisher;
}
