package service;

import model.Flight;

import java.util.List;

public interface FlightFilter {

    public List<Flight> filter(List<Flight> flights);
}
