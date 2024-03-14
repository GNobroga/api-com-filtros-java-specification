package br.gabrielcardoso.filters.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor 
@NoArgsConstructor
@Getter @Setter
public class DateFilterModel {
    
    private String column;
    private Date initialDate;
    private Date finalDate;
}
