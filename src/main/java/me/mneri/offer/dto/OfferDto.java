/*
 * Copyright 2020 Massimo Neri <hello@mneri.me>
 *
 * This file is part of mneri/offer-service.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.mneri.offer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import me.mneri.offer.entity.Offer;
import me.mneri.offer.validator.Constants;
import me.mneri.offer.validator.Description;
import me.mneri.offer.validator.Title;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO for {@link Offer} objects.
 *
 * @author mneri
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@Schema(name = "Offer")
@ToString
public class OfferDto {
    @NonNull
    @Schema(description = "Offer's unique identifier.",
            example = "123e4567-e89b-12d3-a456-556642440000",
            required = true)
    private String id;

    @NonNull
    @Schema(description = "Offer's title.",
            example = "Buy 1 get 1 for free.",
            maxLength = Constants.TITLE_MAX_LENGTH,
            minLength = Constants.TITLE_MIN_LENGTH,
            required = true)
    @Title
    private String title;

    @Description
    @NonNull
    @Schema(description = "Offer's description.",
            example = "Order 1 bag of coffee and get one free.",
            maxLength = Constants.DESCRIPTION_MAX_LENGTH,
            minLength = Constants.DESCRIPTION_MIN_LENGTH,
            required = true)
    private String description;

    @NonNull
    @Schema(description = "Offer's price.",
            example = "100.00",
            required = true)
    private BigDecimal price;

    @NonNull
    @Schema(description = "The currency of the offer's price.",
            example = "GBP",
            required = true)
    private String currency;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @NonNull
    @Schema(description = "The offer's creation time.",
            example = "2020-12-32 00:00:00.000",
            required = true)
    private Date createTime;

    @Schema(description = "The offer's time to live in milliseconds.",
            example = "60000",
            required = true)
    private long ttl;
}
