package webservice;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Raluca on 9/22/2017.
 */

public class ClaimTask extends AsyncTask<String, String, String> implements CredentialInterface {

    private ClaimDelegate claimDelegate;
    private String username;
    private int spotNumber;
    private int floor;
    private Date date;
    private String dateTime;

    @Override
    protected String doInBackground(String... params) {
        try {
            return callClaimService();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String callClaimService() throws IOException, JSONException {

        dateTime = new SimpleDateFormat("yyyy-MM-dd").format(date);

       /* Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath("bookings").
                appendQueryParameter("date", dateTime).
                appendQueryParameter("spotNumber", String.valueOf(spotNumber)).
                appendQueryParameter("floor", String.valueOf(floor)).build();*/
        //  http://parking-parking.193b.starter-ca-central-1.openshiftapps.com/parking-backend/raluca/bookings/spots/6?date=2017-09-20&floor=0

        String modelString = BASE_URL + username + "/bookings/spots/" + spotNumber + "?date=" + dateTime + "&floor=" + floor;
        Uri uri = Uri.parse(modelString).buildUpon().build();
        HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Accept-Encoding", "identity");
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(1000000);
        connection.setReadTimeout(1000000);

        JSONObject object = new JSONObject();
        object.put("username", username);
        object.put("spotNumber", spotNumber);
        object.put("floor", floor);
        object.put("date", dateTime);

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(object.toString());
        out.close();

        StringBuilder sb = new StringBuilder();
        int httpResult = connection.getResponseCode();
        if (httpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            System.out.println("" + sb.toString());
        } else {
            System.out.println(connection.getResponseMessage());
        }
        return sb.toString();
    }

    public ClaimTask(String username, int spotNumber, int floor, Date date) {

        this.username = username;
        this.spotNumber = spotNumber;
        this.floor = floor;
        this.date = date;


        // Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath("bookings").build();

        dateTime = new SimpleDateFormat("yyyy-MM-dd").format(this.date);

        String modelString = BASE_URL + username + "/bookings/spots/" + spotNumber + "?date=" + dateTime + "&floor=" + floor;
        Uri uri = Uri.parse(modelString).buildUpon().build();
        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        String response = String.valueOf(o);

        if (claimDelegate != null) {
            claimDelegate.onClaimDone(response);
        }
    }

    public ClaimDelegate getDelegate() {
        return claimDelegate;
    }

    public void setClaimDelegate(ClaimDelegate claimDelegate) {
        this.claimDelegate = claimDelegate;
    }
}
