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

    public final static LocalDateTime NOW = LocalDateTime.now();

    @Test
    public void shouldReturnListOfFlightWhenUsingFlightDepartureBeforeCurrentTimeFilter() {
        Flight flightPast = FlightBuilder.createFlight(NOW.minusDays(1), NOW);
        Flight flightFuture = FlightBuilder.createFlight(NOW.plusDays(1), NOW.plusDays(1).plusHours(2));

        FlightFilter filter = new FlightDepartureBeforeCurrentTimeFilter(NOW);
        List<Flight> result = filter.filter(List.of(flightPast, flightFuture));

        assertEquals(1, result.size());
    }

    @Test
    public void shouldReturnEmptyListWhenUsingFlightDepartureBeforeCurrentTimeFilter() {
        Flight flightPast = FlightBuilder.createFlight(NOW.minusDays(1), NOW.minusDays(1).plusHours(2));
        Flight flightFuture = FlightBuilder.createFlight(NOW.minusHours(6), NOW.plusHours(1));

        FlightFilter filter = new FlightDepartureBeforeCurrentTimeFilter(NOW);
        List<Flight> result = filter.filter(List.of(flightPast, flightFuture));

        assertEquals(0, result.size());
    }

    @Test
    public void shouldReturnListOfFlightWhenUsingFlightWithArrivalBeforeDepartureFilter() {
        Flight flightWithArrivalBeforeDeparture = FlightBuilder.createFlight(NOW, NOW.minusHours(1));
        Flight flightWithArrivalAfterDeparture = FlightBuilder.createFlight(NOW, NOW.plusHours(2));

        FlightFilter filter = new FlightWithArrivalBeforeDepartureFilter();
        List<Flight> result = filter.filter(List.of(flightWithArrivalBeforeDeparture, flightWithArrivalAfterDeparture));

        assertEquals(1, result.size());
    }

    @Test
    public void shouldReturnEmptyListWhenUsingFlightWithArrivalBeforeDepartureFilter() {
        Flight flightWithArrivalBeforeDeparture = FlightBuilder.createFlight(NOW, NOW.minusHours(1));
        Flight flightWithArrivalAfterDeparture = FlightBuilder.createFlight(NOW, NOW.minusHours(3));

        FlightFilter filter = new FlightWithArrivalBeforeDepartureFilter();
        List<Flight> result = filter.filter(List.of(flightWithArrivalBeforeDeparture, flightWithArrivalAfterDeparture));

        assertEquals(0, result.size());
    }

    @Test
    public void shouldReturnListOfFlightWhenUsingFlightWithMoreThanTwoHoursGroundTimeFilter() {
        Flight flightMoreTwoHoursGroundTime = FlightBuilder.createFlight(
                NOW,
                NOW.plusHours(2),
                NOW.plusHours(6),
                NOW.plusHours(7));
        Flight flightLessTwoHoursGroundTime = FlightBuilder.createFlight(
                NOW,
                NOW.plusHours(2),
                NOW.plusHours(3),
                NOW.plusHours(5));

        FlightFilter filter = new FlightWithMoreThanTwoHoursGroundTime();
        List<Flight> result = filter.filter(List.of(
                flightMoreTwoHoursGroundTime,
                flightLessTwoHoursGroundTime
        ));

        assertEquals(1, result.size());
    }

    @Test
    public void shouldReturnEmptyListWhenUsingFlightWithMoreThanTwoHoursGroundTimeFilter() {
        Flight flightMoreTwoHoursGroundTime = FlightBuilder.createFlight(
                NOW,
                NOW.plusHours(2),
                NOW.plusHours(5),
                NOW.plusHours(8));
        Flight flightLessTwoHoursGroundTime = FlightBuilder.createFlight(
                NOW,
                NOW.plusHours(2),
                NOW.plusHours(9),
                NOW.plusHours(10));

        FlightFilter filter = new FlightWithMoreThanTwoHoursGroundTime();
        List<Flight> result = filter.filter(List.of(
                flightMoreTwoHoursGroundTime,
                flightLessTwoHoursGroundTime
        ));

        assertEquals(0, result.size());
    }
}
