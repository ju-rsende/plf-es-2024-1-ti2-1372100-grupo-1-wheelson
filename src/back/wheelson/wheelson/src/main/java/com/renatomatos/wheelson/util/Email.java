package com.renatomatos.wheelson.util;

import org.hibernate.validator.constraints.ru.INN;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor

public class Email {
    
    
    private String to;
    
    private String subject;
    
    private String body;

}
