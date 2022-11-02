package org.example.eko.model.entities.staging;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(
        name = "date", schema = "staging_area"
)
public class DateEntity implements Comparable {
    @GeneratedValue(
            generator = "staging_area_generator"
    )
    @SequenceGenerator(
            name = "staging_area_generator",
            sequenceName = "staging_area_sequence",
            initialValue = 1
    )
    @Id
    private Long id;

    @Column(
            name = "date",
            nullable = false
    )
    private LocalDate date;

    @Column(
            name = "month_of_year"
    )
    private Integer monthOfYear;
    @Column(
            name = "the_year"
    )
    private Integer year;
    @Column(
            name = "the_month"
    )
    private String month;

    @Column(
            name = "quarter_of_year"
    )
    private Integer quarterOfYear;


    @Column(
            name = "day_of_month"
    )
    private Integer dayOfMonth;

    public DateEntity() {
    }

    public DateEntity(LocalDate date) {
        this.date = date;
        this.month = date.getMonth().toString();
        this.year = date.getYear();
        this.monthOfYear = date.getMonthValue();
        this.quarterOfYear = this.computeQuarterOfYear(this.monthOfYear + 1);
        this.dayOfMonth = date.getDayOfMonth();
    }

    private int computeQuarterOfYear(int i) {
        if (i <= 3) {
            return 1;
        } else if (i <= 6) {
            return 2;
        } else {
            return i <= 9 ? 3 : 4;
        }
    }

    public Long getId() {
        return this.id;
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

    public int getMonthOfYear() {
        return this.monthOfYear;
    }

    public void setMonthOfYear(int monthOfYear) {
        this.monthOfYear = monthOfYear;
    }


    public int getYear() {
        return this.year;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getQuarterOfYear() {
        return this.quarterOfYear;
    }

    public void setQuarterOfYear(int quarterOfYear) {
        this.quarterOfYear = quarterOfYear;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DateEntity dateTime = (DateEntity) o;
        return Objects.equals(monthOfYear, dateTime.monthOfYear) && Objects.equals(year, dateTime.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(monthOfYear, year);
    }

    @Override
    public int compareTo(Object o) {
        if (o == null || getClass() != o.getClass()) return 1;
        DateEntity dateTime = (DateEntity) o;
        return this.year.compareTo(((DateEntity) o).getYear()) != 0 ? this.year.compareTo(dateTime.year) : this.monthOfYear.compareTo(dateTime.monthOfYear) == 0 ? null : null;
    }
}