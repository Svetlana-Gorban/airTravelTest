package com.gridnine.testing;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlaneTimeService {

    public void filterFlights(List<Flight> flights) {
        filterFlightsBeforeCurrentTime(flights);
        filterFlightsWithSegmentsArrivalBeforeDeparture(flights);
        filterFlightsWithExcessiveGroundTime(flights);
    }


    public void filterFlightsBeforeCurrentTime(List<Flight> flights) {
        System.out.println("Вылет до текущего момента времени:");
        LocalDateTime currentTime = LocalDateTime.now();
        flights.removeIf(flight -> flight.getSegments().stream()
                .anyMatch(segment -> segment.getDepartureDate().isBefore(currentTime)));
        printFlights(flights);
    }

    public void filterFlightsWithSegmentsArrivalBeforeDeparture(List<Flight> flights) {
        System.out.println("Дата прилёта раньше даты вылета:");
        flights.removeIf(flight -> flight.getSegments().stream()
                .anyMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate())));
        printFlights(flights);
    }

    public void filterFlightsWithExcessiveGroundTime(List<Flight> flights) {
        System.out.println("Перелёты с общим временем более двух часов:");
        flights.removeIf(flight -> calculateTotalTime(flight.getSegments()).toHours() > 2);
        printFlights(flights);
    }

    public Duration calculateTotalTime(List<Segment> segments) {
        Duration totalTime = Duration.ZERO;
        LocalDateTime previousArrival = null;
        for (Segment segment : segments) {
            if (previousArrival != null) {
                totalTime = totalTime.plus(Duration.between(previousArrival, segment.getDepartureDate()));
            }
            previousArrival = segment.getArrivalDate();
        }
        return totalTime;
    }

    public void printFlights(List<Flight> flights) {
        if (flights.isEmpty()) {
            System.out.println("Нет перелётов");
        } else {
            System.out.println(flights);
        }
    }
    }
