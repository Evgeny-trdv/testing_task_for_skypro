import service.FlightDepartureBeforeCurrentTimeFilter;
import service.FlightFilter;
import service.FlightWithArrivalBeforeDepartureFilter;
import service.FlightWithMoreThanTwoHoursGroundTime;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        //все перелеты
        System.out.println(FlightBuilder.createFlights());

        //фильтр исключающий перелеты до настоящего времени
        FlightFilter flightDepartureBeforeCurrentTimeFilter = new FlightDepartureBeforeCurrentTimeFilter(LocalDateTime.now().minusDays(2));
        System.out.println(flightDepartureBeforeCurrentTimeFilter.filter(FlightBuilder.createFlights()));

        //фильтр исключающий перелеты, где время прилета раньше времени вылета
        FlightFilter flightWithArrivalBeforeDepartureFilter = new FlightWithArrivalBeforeDepartureFilter();
        System.out.println(flightWithArrivalBeforeDepartureFilter.filter(FlightBuilder.createFlights()));

        //фильтр исключающий перелеты, где время пересадки (на земле) больше двух часов
        FlightFilter flightWithMoreThanTwoHoursGroundTime = new FlightWithMoreThanTwoHoursGroundTime();
        System.out.println(flightWithMoreThanTwoHoursGroundTime.filter(FlightBuilder.createFlights()));
    }
}