package com.example.shortener.link;

import java.util.UUID;

class UUIDRandomIdGenerator {
    private static final int ID_SIZE = 10;

    public static String generateId() {
        UUID uuid = UUID.randomUUID();
        String uuidWithoutDash = uuid.toString().replace("-", "");
        return uuidWithoutDash.substring(0, ID_SIZE);
    }
}