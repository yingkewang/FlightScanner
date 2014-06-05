package yingke.ita.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yingkewang
 *
 */
public class RequestRoute {

    // DO NOT CHANGE VARIABLE NAMES
    private List<String> origins;
    private Boolean originPreferCity;
    private List<String> destinations;
    private Boolean destinationPreferCity;
    private String date;
    private Boolean isArrivalDate;
    private Map<String, Integer> dateModifier;
    private String commandLine; // e.g. "airlines BA AF KL LH"; - is this the advanced routing?

    public RequestRoute(String origin, String destination, String date) {
        this();
        this.origins.add(origin);
        this.destinations.add(destination);
        this.date = date;
    }

    public RequestRoute() {
        this.origins = new ArrayList<String>();
        this.originPreferCity = Boolean.FALSE;
        this.destinations = new ArrayList<String>();
        this.destinationPreferCity = Boolean.FALSE;
        this.date = "";
        this.isArrivalDate = Boolean.FALSE;
        this.dateModifier = new HashMap<String, Integer>();
        this.dateModifier.put("plus", Integer.valueOf(0));
        this.dateModifier.put("minus", Integer.valueOf(0));
        this.commandLine = "";
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getIsArrivalDate() {
        return isArrivalDate;
    }

    public void setIsArrivalDate(Boolean isArrivalDate) {
        this.isArrivalDate = isArrivalDate;
    }

    public Integer getDatePlus() {
        return dateModifier.get("plus");
    }

    public Integer getDateMinus() {
        return dateModifier.get("minus");
    }

    public void setDatePlus(Integer plusOffset) {
        this.dateModifier.put("plus", plusOffset);
    }

    public void setDateMinus(Integer minusOffset) {
        this.dateModifier.put("minus", minusOffset);
    }

    public String getCommandLine() {
        return commandLine;
    }

    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }
    
    public boolean isValid() {
        return origins.size() > 0 && destinations.size() > 0 && !date.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("%s - %s %s", origins, destinations, date);
    }
}
