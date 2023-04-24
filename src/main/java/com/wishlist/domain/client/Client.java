package com.wishlist.domain.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@ToString
@Document (collection = "clients")
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    private String id;
    private String name;

}
