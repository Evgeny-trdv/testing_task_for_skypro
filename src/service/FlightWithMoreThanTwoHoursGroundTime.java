package service;

import model.Flight;
import model.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class FlightWithMoreThanTwoHoursGroundTime implements FlightFilter{
    private final Duration GROUND_TIME = Duration.ofHours(2);

    @Override
    public List<Flight> filter(List<Flight> flights) {
            return flights
                    .stream()
                    .filter(flight -> {
                        List<Segment> segments = flight.getSegments();
                        if (segments.size() <= 1) {
                            return true;
                        }

                        Duration totalGroundTime = Duration.ZERO;
                        for (int i = 1; i < segments.size(); i++) {
                            LocalDateTime previousArrival = segments.get(i-1).getArrivalDate();
                            LocalDateTime currentDeparture = segments.get(i).getDepartureDate();
                            Duration currentGroundTime = Duration.between(previousArrival, currentDeparture);

                            totalGroundTime = totalGroundTime.plus(currentGroundTime);
                            if (totalGroundTime.compareTo(GROUND_TIME) > 0) {
                                return false;
                            }
                        }
                        return true;
                    })
                    .toList();
    }
}
