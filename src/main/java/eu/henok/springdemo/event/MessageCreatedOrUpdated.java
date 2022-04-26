package eu.henok.springdemo.event;

import java.io.Serializable;

public record MessageCreatedOrUpdated(Long id, String content) implements Serializable {}
