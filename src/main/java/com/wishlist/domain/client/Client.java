package com.wishlist.domain.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@ToString
@Document (collection = "clients")
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    private String id;
    private String name;

    public static Client of(String name) {
        return new Client(null, name);
    }
}
