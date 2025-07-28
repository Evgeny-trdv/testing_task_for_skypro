import model.Flight;
import org.junit.Test;
import service.FlightDepartureBeforeCurrentTimeFilter;
import service.FlightFilter;
import service.FlightWithArrivalBeforeDepartureFilter;
import service.FlightWithMoreThanTwoHoursGroundTime;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FilterServiceTest {

    @Test
    public void shouldReturnListOfFlightDepartureBeforeCurrentTimeFilter() {
        LocalDateTime now = LocalDateTime.now();
        Flight flightPast = FlightBuilder.createFlight(now.minusDays(1), now);
        Flight flightFuture = FlightBuilder.createFlight(now.plusDays(1), now.plusDays(1).plusHours(2));

        FlightFilter filter = new FlightDepartureBeforeCurrentTimeFilter(now);
        List<Flight> result = filter.filter(List.of(flightPast, flightFuture));

        assertEquals(1, result.size());
    }

    @Test
    public void shouldReturnListOfFlightWithArrivalBeforeDepartureFilter() {
        LocalDateTime now = LocalDateTime.now();
        Flight flightWithArrivalBeforeDeparture = FlightBuilder.createFlight(now, now.minusHours(1));
        Flight flightWithArrivalAfterDeparture = FlightBuilder.createFlight(now, now.plusHours(2));

        FlightFilter filter = new FlightWithArrivalBeforeDepartureFilter();
        List<Flight> result = filter.filter(List.of(flightWithArrivalBeforeDeparture, flightWithArrivalAfterDeparture));

        assertEquals(1, result.size());
    }

    @Test
    public void shouldReturnListOfFlightWithMoreThanTwoHoursGroundTimeFilter() {
        LocalDateTime now = LocalDateTime.now();
        Flight flightMoreTwoHoursGroundTime = FlightBuilder.createFlight(
                now,
                now.plusHours(2),
                now.plusHours(6),
                now.plusHours(7));
        Flight flightLessTwoHoursGroundTime = FlightBuilder.createFlight(
                now,
                now.plusHours(2),
                now.plusHours(3),
                now.plusHours(5));

        FlightFilter filter = new FlightWithMoreThanTwoHoursGroundTime();
        List<Flight> result = filter.filter(List.of(
                flightMoreTwoHoursGroundTime,
                flightLessTwoHoursGroundTime
        ));

        assertEquals(1, result.size());
    }
}
