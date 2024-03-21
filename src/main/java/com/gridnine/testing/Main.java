package com.gridnine.testing;
import java.util.List;

public class Main{
    private static PlaneTimeService planeTimeService;
    public Main(PlaneTimeService planeTimeService){
        this.planeTimeService=planeTimeService;
    }
   public static void main(String[] args) {
       List<Flight> flights = FlightBuilder.createFlights();
       planeTimeService.filterFlights(flights);
    }
}

