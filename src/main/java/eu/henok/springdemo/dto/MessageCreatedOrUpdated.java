package eu.henok.springdemo.dto;

import java.util.UUID;

public record MessageCreatedOrUpdated(UUID id, String content) {}
