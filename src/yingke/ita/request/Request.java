package yingke.ita.request;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.UUID;

/**
 * @author yingkewang
 * 
 */
public abstract class Request {

    public enum Cabin {COACH, BUSINESS, FIRST} // TODO: y+ how?

    public static final String BASE_URL = "http://matrix.itasoftware.com/xhr/shop/search?";

    public abstract String generateRequestURL();
    public abstract boolean isValid();
    public abstract void processResponse(String response);
    public abstract String prettyPrint();

    public void doRequest() {
        try{
            String uid = UUID.randomUUID().toString();
            String outputFile = "resources/"+uid; // TODO: do logging properly
            File outputfile = new File(outputFile);
            outputfile.createNewFile();
            FileWriter writer = new FileWriter(outputfile); 

            if (!isValid()) {
                // something went wrong in the request build - bail
                System.err.println("Invalid input: ");
                System.err.println(prettyPrint());
//                writer.write("Invalid input:");
//                writer.write(prettyPrint());
                writer.flush();
                writer.close();
                return;
            }

            System.out.println(prettyPrint());
//            writer.write(prettyPrint());

            CookieHandler.setDefault(new CookieManager());
            ((CookieManager)CookieHandler.getDefault()).setCookiePolicy(new CookiePolicy(){
                @Override
                public boolean shouldAccept(URI uri, HttpCookie hc) {
                    return true;
                }
            });

            URL url = new URL(generateRequestURL());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            System.out.println("Sending request to ITA");
//            writer.write("Sending request to ITA : " + url);
            conn.setDoOutput(true); // send request

            int responseCode = conn.getResponseCode();
            System.out.println(String.format("Response Code : %s Response ID : %s", responseCode, uid));
//            writer.write("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            writer.write(response.toString());
            writer.flush();
            writer.close();

            processResponse(response.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
