package eu.henok.springdemo.dto;

import java.util.UUID;

public record Message(UUID id, String content) {}
