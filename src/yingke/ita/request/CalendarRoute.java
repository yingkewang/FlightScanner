package yingke.ita.request;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yingkewang
 *
 */
public class CalendarRoute {

    // DO NOT CHANGE VARIABLE NAMES
    private List<String> origins;
    private Boolean originPreferCity;
    private List<String> destinations;
    private Boolean destinationPreferCity;
//    private String routeLanguage; // e.g. "airlines BA AF KL LH"; - is this the advanced routing?

    public CalendarRoute(String origin, String destination) {
        this();
        this.origins.add(origin);
        this.destinations.add(destination);
    }

    public CalendarRoute() {
        this.origins = new ArrayList<String>();
        this.originPreferCity = Boolean.FALSE;
        this.destinations = new ArrayList<String>();
        this.destinationPreferCity = Boolean.FALSE;
//        this.routeLanguage = "";
    }

    public List<String> getOrigins() {
        return origins;
    }

    public void setOrigins(List<String> origins) {
        this.origins = origins;
    }
    
    public void addOrigin(String origin) {
        this.origins.add(origin);
    }

    public Boolean getOriginPreferCity() {
        return originPreferCity;
    }

    public void setOriginPreferCity(Boolean originPreferCity) {
        this.originPreferCity = originPreferCity;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<String> destinations) {
        this.destinations = destinations;
    }
    
    public void addDestination(String destination) {
        this.destinations.add(destination);
    }

    public Boolean getDestinationPreferCity() {
        return destinationPreferCity;
    }

    public void setDestinationPreferCity(Boolean destinationPreferCity) {
        this.destinationPreferCity = destinationPreferCity;
    }
//
//    public String getCommandLine() {
//        return routeLanguage;
//    }
//
//    public void setCommandLine(String commandLine) {
//        this.routeLanguage = commandLine;
//    }
    
    public boolean isValid() {
        return origins.size() > 0 && destinations.size() > 0;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", origins, destinations);
    }
}
