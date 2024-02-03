package com.softedge.solution.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="country_mtb")
@Data
public class Country {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String countryName;
    private String countryCode;
    private String countryLogo;


}
