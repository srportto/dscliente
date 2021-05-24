package com.devsuperior.dscliente.resources.exceptions;

import com.devsuperior.dscliente.services.exceptions.DatabaseException;
import com.devsuperior.dscliente.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class) //intercepta o lancamento de execoes desse tipo
    public ResponseEntity<LayoutErrorsProject> entityNotFound(ResourceNotFoundException error, HttpServletRequest req){

        LayoutErrorsProject layoutError = new LayoutErrorsProject();

        layoutError.setTimestamp(Instant.now());
        layoutError.setStatus(HttpStatus.NOT_FOUND.value());
        layoutError.setError("Recurso nao encontrado");
        layoutError.setMessage(error.getMessage());
        layoutError.setPath(req.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(layoutError);
    }

    @ExceptionHandler(DatabaseException.class) //intercepta o lancamento de execoes desse tipo
    public ResponseEntity<LayoutErrorsProject> database(DatabaseException error, HttpServletRequest req){

        LayoutErrorsProject layoutError = new LayoutErrorsProject();

        layoutError.setTimestamp(Instant.now());
        layoutError.setStatus(HttpStatus.BAD_REQUEST.value());
        layoutError.setError("database exception");
        layoutError.setMessage(error.getMessage());
        layoutError.setPath(req.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(layoutError);
    }
}
