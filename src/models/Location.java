package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author fayshaluddin
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    public String name;
    public String formatted_address;
    public String place_id;
    public double rating;
    public int user_ratings_total;
    public Geometry geometry;
    public String business_status;
}
