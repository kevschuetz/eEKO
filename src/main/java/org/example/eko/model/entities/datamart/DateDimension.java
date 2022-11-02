package org.example.eko.model.entities.datamart;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "dt_date", schema= "data_mart")
public class DateDimension {
    @GeneratedValue(
            generator = "data_mart_generator"
    )
    @Id
    private Long id;

    private LocalDate date;
    private Integer month;
    private String theMonth;
    private Integer year;

    public DateDimension(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getTheMonth() {
        return theMonth;
    }

    public void setTheMonth(String theMonth) {
        this.theMonth = theMonth;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
