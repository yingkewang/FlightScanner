package yingke;

import yingke.ita.request.CalendarRequest;
import yingke.ita.request.CalendarRoute;
import yingke.ita.request.DateRequest;
import yingke.ita.request.RequestRoute;

/**
 * @author yingkewang
 *
 */
public class Tester {

    public static void main(String[] args) {
        DateRequest req = new DateRequest();
        req.setCheckAvailability(false);
        RequestRoute requestRoute1 = new RequestRoute("ARN", "LAX", "2014-09-20");
        requestRoute1.setCommandLine("airlines BA");
        req.addRequestRoute(requestRoute1);
        RequestRoute requestRoute2 = new RequestRoute("ONT", "CPH", "2014-10-04");
        requestRoute1.setCommandLine("airlines BA");
        req.addRequestRoute(requestRoute2);
        req.doRequest();
        
//        CalendarRequest req = new CalendarRequest();
//        req.setCheckAvailability(true);
//        req.setStartDate("2014-07-01");
//        req.setEndDate("2014-08-01");
//        req.addCalendarRoute(new CalendarRoute("LON", "HKG"));
//        req.addCalendarRoute(new CalendarRoute("HKG", "LON"));
//        req.doRequest();
    }

}
