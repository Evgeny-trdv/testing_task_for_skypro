package service;

import model.Flight;
import model.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class FlightWithMoreThanTwoHoursGroundTime implements FlightFilter{
    private final long GROUND_TIME = 2;

    @Override
    public List<Flight> filter(List<Flight> flights) {
            return flights
                    .stream()
                    .filter(flight -> {
                        List<Segment> segments = flight.getSegments();
                        if (segments.size() <= 1) {
                            return true;
                        }

                        long totalGroundTime = 0;
                        for (int i = 1; i < segments.size(); i++) {
                            LocalDateTime previousArrival = segments.get(i-1).getArrivalDate();
                            LocalDateTime currentDeparture = segments.get(i).getDepartureDate();
                            totalGroundTime += Duration.between(previousArrival, currentDeparture).toHours();
                            if (totalGroundTime >= GROUND_TIME) {
                                return false;
                            }
                        }
                        return true;
                    })
                    .toList();
        }
    }
