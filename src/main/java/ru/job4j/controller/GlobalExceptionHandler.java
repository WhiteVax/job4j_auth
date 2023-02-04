package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class.getSimpleName());

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public void handlerExceptionNullPoint(Exception e, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpStatus.BAD_REQUEST.value());
        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", "Some of fields empty.");
            put("details", e.getMessage());
        }}));
        LOGGER.error(e.getMessage());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public void handlerExceptionConstrainViolation(Exception e, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpStatus.CONFLICT.value());
        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", "This login is already exists!");
            put("details", e.getMessage());
        }}));
        LOGGER.error(e.getMessage());
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    public void handlerExceptionNoSuchElement(Exception e, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpStatus.NOT_FOUND.value());
        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", "Not found this person.");
            put("details", e.getMessage());
        }}));
        LOGGER.error(e.getMessage());
    }
}
