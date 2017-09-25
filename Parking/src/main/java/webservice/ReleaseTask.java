package webservice;

import android.net.Uri;
import android.os.AsyncTask;

import com.nttdata.parkingmobile.ReleaseActivity;

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

import manager.DataManager;

/**
 * Created by Raluca on 9/21/2017.
 */

public class ReleaseTask extends AsyncTask<String, String, String> implements CredentialInterface {
    private ReleaseDelegate releaseDelegate;
    private String username;
    private int spotNumber;
    private Date date;
    private Date vacatedAt;
    private String dateTime;
    private String vacatedAtString;


    @Override
    protected String doInBackground(String... params) {
        try {
            return callReleaseService();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String callReleaseService() throws IOException, JSONException {

        //aici sa ma uit putin cu dateTime la cale
        // Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath(username + "/vacancies/assigned?from=" + date + "&to=" + vacatedAt).build();
        dateTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
        vacatedAtString = new SimpleDateFormat("yyyy-MM-dd").format(vacatedAt);

        //String modelString = BASE_URL + username + "/vacancies/assigned?from=" + dateTime + "&to=" + vacatedAtString;
        String modelString = BASE_URL + username + "/vacancies/assigned?from=" + dateTime + "&to=" + dateTime;

        Uri uri = Uri.parse(modelString).buildUpon().build();

        HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(1000000);
        connection.setReadTimeout(1000000);

        JSONObject object = new JSONObject();
        object.put("username", username);
        // object.put("spotNumber", spotNumber);

        object.put("date", dateTime);
        object.put("vacatedAt", vacatedAtString);

        connection.addRequestProperty("Authorization", DataManager.getInstance().getBaseAuthStr());

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(object.toString());
        out.close();

        StringBuilder sb = new StringBuilder();
        int httpResult = connection.getResponseCode();
        if (httpResult == HttpURLConnection.HTTP_OK || httpResult==HttpURLConnection.HTTP_CREATED || httpResult==HttpURLConnection.HTTP_CLIENT_TIMEOUT) {
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

    public ReleaseTask(String username, Date date, Date vacatedAt) {

        this.username = username;
        // this.spotNumber = spotNumber;
        this.date = date;
        this.vacatedAt = vacatedAt;

        dateTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
        vacatedAtString = new SimpleDateFormat("yyyy-MM-dd").format(vacatedAt);

        String modelString = BASE_URL + username + "/vacancies/assigned?from=" + dateTime + "&to=" + dateTime;
        //Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath(username + "/vacancies/assigned?from=" + date + "&to=" + vacatedAt).build();
        Uri uri = Uri.parse(modelString).buildUpon().build();

        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        String response = String.valueOf(o);

        if (releaseDelegate != null) {
            releaseDelegate.onReleaseDone(response);
        }
    }

    public ReleaseDelegate getDelegate() {
        return releaseDelegate;
    }


    public void setReleaseDelegate(ReleaseDelegate releaseDelegate) {
        this.releaseDelegate = releaseDelegate;
    }
}
