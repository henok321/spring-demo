package eu.henok.springdemo.event;

public record MessageCreatedOrUpdated(Long id, String content) {}
