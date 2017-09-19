package webservice;

/**
 * Created by m09ny on 09/15/17.
 */

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class VacanciesTask  extends AsyncTask<String, String, String> implements CredentialInterface{

    private VacanciesDelegate vacanciesDelegate;

    @Override
    protected String doInBackground(String... params) {
        try {
            return callVacanciesService();
        } catch (IOException | JSONException e) {
            Log.e("ERROR", "Failed to get the vacancies.", e);
            return null;
        }
    }

    private String callVacanciesService() throws IOException, JSONException {

        Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath("vacancies").build();
        HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();
        connection.setRequestMethod("GET");

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


    public VacanciesTask() {

        Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath("vacancies").build();
        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        String response = String.valueOf(o);

        if (vacanciesDelegate != null){
            vacanciesDelegate.onVacanciesDone(response);
        }

    }

    public VacanciesDelegate getDelegate() {
        return vacanciesDelegate;
    }

    public void setVacanciesDelegate(VacanciesDelegate vacanciesDelegate) {
        this.vacanciesDelegate = vacanciesDelegate;
    }
}
