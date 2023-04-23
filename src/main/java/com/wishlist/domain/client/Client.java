package com.wishlist.domain.client;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Document (collection = "clients")
public class Client {

    @Id
    private String id;
    private String name;

}
