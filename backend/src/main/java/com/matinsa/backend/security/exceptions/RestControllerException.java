package com.matinsa.backend.security.exceptions;

import com.matinsa.backend.security.dto.Mensaje;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestControllerException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Mensaje> handleException(Exception e){
        return ResponseEntity.internalServerError()
                .body(new Mensaje(e.getMessage()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Mensaje> handleCustomException(CustomException e){

        return ResponseEntity.status(e.getStatus())
                .body(new Mensaje(e.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Mensaje> handleBadCredentialsException(BadCredentialsException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Mensaje(e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Mensaje> handleAccessDeniedException(AccessDeniedException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Mensaje("Acceso denegado"));
    }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Mensaje> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
            List<String> mensajes = new ArrayList<>();

            for (ObjectError error : e.getBindingResult().getAllErrors()) {
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError) error;
                    mensajes.add(fieldError.getField() + ": " + error.getDefaultMessage());
                } else {
                    mensajes.add(error.getDefaultMessage());
                }
            }

            String mensajeConcatenado = String.join(",", mensajes);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Mensaje(mensajeConcatenado));
        }

    @ExceptionHandler(value = {MalformedJwtException.class, UnsupportedJwtException.class, IllegalArgumentException.class, SignatureException.class})
    public ResponseEntity<Mensaje> jwtException(JwtException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Mensaje(e.getMessage()));
    }
}
