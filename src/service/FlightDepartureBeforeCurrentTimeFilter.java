package service;

import model.Flight;

import java.time.LocalDateTime;
import java.util.List;

public class FlightDepartureBeforeCurrentTimeFilter implements FlightFilter{

    private final LocalDateTime currentTime;

    public FlightDepartureBeforeCurrentTimeFilter(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights
                .stream()
                .filter(flight -> flight.getSegments()
                        .stream()
                        .noneMatch(segment -> segment.getDepartureDate().isBefore(currentTime)))
                .toList();
    }
}
