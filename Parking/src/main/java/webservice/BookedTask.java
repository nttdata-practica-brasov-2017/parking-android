package webservice;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import com.nttdata.parkingmobile.ReleaseActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;


public class BookedTask extends AsyncTask<String, String, String> implements CredentialInterface {
    @Override
    protected String doInBackground(String... params) {
        return null;
    }

   /* private String username;
    private int spotNumber;
    private int floor;
    private Date date;

   // private BookedDelegate delegate;

    @Override
    protected String doInBackground(String... params) {
        try {
            return callBookedService();
        } catch (IOException | JSONException e) {
            Log.e("ERROR", "Failed to booking.", e);
            return null;
        }
    }

    private String callBookedService() throws IOException, JSONException {

        Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath("bookings").build();
        HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");

        JSONObject object = new JSONObject();
        object.put("username", username);
        object.put("spotNumber", spotNumber);
        object.put("floor", floor);
        object.put("date", date);

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(object.toString());
        out.close();

        StringBuilder sb = new StringBuilder();
        int HttpResult = connection.getResponseCode();
        if (HttpResult == HttpURLConnection.HTTP_OK) {
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

    public BookedTask(String username, int spotNumber, int floor, Date date) {
        this.username = username;
        this.spotNumber = spotNumber;
        this.floor = floor;
        this.date = date;

        Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath("bookings").build();

        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        String response = String.valueOf(o);

        if (delegate != null) {
            delegate.onLoginDone(response);
        }
    }

    public BookedDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(BookedDelegate delegate) {
        this.delegate = delegate;
    }

    public void setDelegate(ReleaseActivity releaseActivity) {
    }*/
}
