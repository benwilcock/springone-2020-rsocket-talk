package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String message; // Hold a message
    private long created = Instant.now().getEpochSecond(); // Something instance specific

    /**
     * Allow creation from a message.
     * @param message
     */
    public Message(String message) {
        this.message = message;
    }
}
