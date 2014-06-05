package yingke.ita.response;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author Yingke
 *
 */
public class Itinerary {

    private List<String> flights;
    private String arrival;
    private String departure;
    private int duration;
    private List<String> cabins;
    private String destination;
    private String origin;
    private List<String> stops;

    public Itinerary(JsonObject object) {
        this.flights = new ArrayList<String>();
        for (JsonElement flight : object.get("flights").getAsJsonArray()) {
            flights.add(flight.getAsString());
        }
        this.arrival = object.get("arrival").getAsString();
        this.departure = object.get("departure").getAsString();
        this.duration = object.get("duration").getAsInt();
        this.cabins = new ArrayList<String>();
        for (JsonElement flight : object.get("cabins").getAsJsonArray()) {
            cabins.add(flight.getAsString());
        }
        this.destination = object.get("destination").getAsJsonObject().get("code").getAsString();
        this.origin = object.get("origin").getAsJsonObject().get("code").getAsString();
        this.stops = new ArrayList<String>();
        if (object.has("stops")) { 
            for (JsonElement flight : object.get("stops").getAsJsonArray()) {
                stops.add(flight.getAsJsonObject().get("code").getAsString());
            }
        }

    }
    
    public List<String> getFlights() {
        return flights;
    }

    public String getArrival() {
        return arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public int getDuration() {
        return duration;
    }

    public List<String> getCabins() {
        return cabins;
    }

    public String getDestination() {
        return destination;
    }

    public String getOrigin() {
        return origin;
    }

    public List<String> getStops() {
        return stops;
    }

    public String toString() {
        if (stops.size() != 0) {
            return String.format("%s %s-%s via %s", flights, origin, destination, stops);
        }
        return String.format("%s %s-%s", flights, origin, destination);
    }

}

