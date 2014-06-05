package yingke.ita.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yingke.Util;
import yingke.ita.response.Itinerary;
import yingke.ita.response.Price;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * @author yingkewang
 *
 */
public class DateRequest extends Request {

    // DO NOT CHANGE VARIABLE NAMES
    private List<RequestRoute> slices;
    private Map<String, Integer> pax;
    private Cabin cabin;
    private Integer maxStopCount;
    private Boolean changeOfAirport;
    private Boolean checkAvailability;
    private Map<String, Integer> page;
    private String sorts;

    public DateRequest() {
        this.slices = new ArrayList<RequestRoute>();
        this.pax = new HashMap<String, Integer>();
        this.pax.put("adults", Integer.valueOf(1));
        this.cabin = Cabin.COACH;
        this.maxStopCount = Integer.valueOf(2);
        this.changeOfAirport = Boolean.TRUE;
        this.checkAvailability = Boolean.FALSE;
        this.page = new HashMap<String, Integer>();
        this.page.put("size", Integer.valueOf(10));
        this.sorts = "default";
    }

    @Override
    public String generateRequestURL() {
        Gson gson = new GsonBuilder().create();
        String requestString = gson.toJson(this, DateRequest.class);

        String urlParam = "name=specificDates&summarizers=carrierStopMatrix,"
                + "currencyNotice,solutionList,itineraryPriceSlider,"
                + "itineraryCarrierList,itineraryDepartureTimeRanges,itineraryArrivalTimeRanges,"
                + "durationSliderItinerary,itineraryOrigins,itineraryDestinations,"
                + "itineraryStopCountList,warningsItinerary&format=JSON&inputs=";

        return BASE_URL + urlParam + Util.escapeJson(requestString);
    }

    @Override
    public boolean isValid() {
        boolean isValid = slices.size() > 0;
        for (RequestRoute rr : slices) {
            isValid &= rr.isValid();
            if (!isValid) {
                break;
            }
        }
        return isValid;
    }

    @Override
    public void processResponse(String response) {
        try {
            Gson gson = new GsonBuilder().create();
            response = response.substring(4); // bah
            JsonObject jsonObject = gson.fromJson(response, JsonElement.class).getAsJsonObject();

            if (jsonObject.has("error")) {
                System.err.println(jsonObject.get("error").getAsJsonObject().get("message").getAsString());
                return;
            } else if (jsonObject.has("result")) {
                JsonObject solutonListJsonObject = jsonObject.get("result").getAsJsonObject().get("solutionList").getAsJsonObject();
    //            Price minPrice = new Price(solutonListJsonObject.get("minPrice").getAsString());
    //            System.out.println(String.format("Minimum routing price : %s", minPrice.toString()));
        
                int i = 1;
                JsonArray resultArray = solutonListJsonObject.get("solutions").getAsJsonArray();
                for (JsonElement resultElement : resultArray) {
                    JsonObject resultObject = resultElement.getAsJsonObject();
                    JsonObject resultExtObject = resultObject.get("ext").getAsJsonObject();
    //                Price pricePerMile = new Price(resultExtObject.get("pricePerMile").getAsString());
                    Price totalPrice = new Price(resultExtObject.get("totalPrice").getAsString());
        
                    JsonObject resultItineraryObject = resultObject.get("itinerary").getAsJsonObject();
                    JsonObject singleAirlineJsonObject = resultItineraryObject.get("singleCarrier").getAsJsonObject();
                    String carrierCode = singleAirlineJsonObject.get("code").getAsString();
        
                    System.out.println(String.format("\nItinerary %s: Price: %s Carrier:%s ", i, totalPrice, carrierCode));
        
                    JsonArray flightSliceArray = resultItineraryObject.get("slices").getAsJsonArray();
                    for (JsonElement flightSliceElem : flightSliceArray) {
                        Itinerary flight = new Itinerary(flightSliceElem.getAsJsonObject());
                        System.out.println("\t"+flight);
                    }
                    i++;
                }
            } else { // dunno what this is yet.
                System.err.println("Unexpected response.  Dumping contents.");
                Util.printJsonElement(jsonObject, 0);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
    }

    public List<RequestRoute> getRequestRoutes() {
        return slices;
    }

    public void setRequestRoutes(List<RequestRoute> requestRoutes) {
        this.slices = requestRoutes;
    }
    
    public void addRequestRoute(RequestRoute requestRoute) {
        this.slices.add(requestRoute);
    }

    public Map<String, Integer> getPax() {
        return pax;
    }

    public void setPax(Map<String, Integer> pax) {
        this.pax = pax;
    }

    public Cabin getCabin() {
        return cabin;
    }

    public void setCabin(Cabin cabin) {
        this.cabin = cabin;
    }

    public Integer getMaxStopCount() {
        return maxStopCount;
    }

    public void setMaxStopCount(Integer maxStopCount) {
        this.maxStopCount = maxStopCount;
    }

    public Boolean getChangeOfAirport() {
        return changeOfAirport;
    }

    public void setChangeOfAirport(Boolean changeOfAirport) {
        this.changeOfAirport = changeOfAirport;
    }

    public Boolean getCheckAvailability() {
        return checkAvailability;
    }

    public void setCheckAvailability(Boolean checkAvailability) {
        this.checkAvailability = checkAvailability;
    }

    public Map<String, Integer> getPage() {
        return page;
    }

    public void setPage(Map<String, Integer> page) {
        this.page = page;
    }

    public String getSorts() {
        return sorts;
    }

    public void setSorts(String sorts) {
        this.sorts = sorts;
    }

    @Override
    public String prettyPrint() {
        StringBuffer sb = new StringBuffer();
        sb.append("Cabin : ").append(cabin);
        sb.append(" Check Seats : ").append(checkAvailability);
        sb.append(" Max Stops : ").append(maxStopCount);
        sb.append("\n");
        for (RequestRoute rr : slices) {
            sb.append(rr.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
        DateRequest req = new DateRequest();
        req.addRequestRoute(new RequestRoute("ZRH", "PEK", "2014-07-15"));
        req.addRequestRoute(new RequestRoute("PEK", "MXP", "2014-07-25"));
        String requestString = gson1.toJson(req, DateRequest.class);
        System.out.println(req.prettyPrint());
        System.out.println(requestString);
    }

}
