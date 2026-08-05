package br.senai.meuprojeto.controle;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // pegador de exceções do projeto
public class GerenciadorDeExcecoes {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> tratarErroDeValidacao(MethodArgumentNotValidException ex) {
        Map<String, String> camposComErro = new HashMap<>();

        // Percorre todos os erros de campos detectados pelo Jakarta Validation
        for (FieldError campo : ex.getBindingResult().getFieldErrors()) {
            camposComErro.put(campo.getField(), campo.getDefaultMessage());
        }

        // Retorna o Record padronizado
        return camposComErro;
    }
}