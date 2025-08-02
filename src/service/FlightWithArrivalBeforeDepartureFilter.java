package service;

import model.Flight;

import java.util.ArrayList;
import java.util.List;

public class FlightWithArrivalBeforeDepartureFilter implements FlightFilter {

    @Override
    public List<Flight> filter(List<Flight> flights) {
        List<Flight> filteredFlights = new ArrayList<Flight>();
        for (Flight flight : flights) {
            if (flight
                    .getSegments()
                    .stream()
                    .noneMatch(s -> s.getArrivalDate().isBefore(s.getDepartureDate()))) {
                filteredFlights.add(flight);
            }
        }
        return filteredFlights;
    }
}
