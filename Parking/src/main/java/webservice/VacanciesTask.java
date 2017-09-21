package webservice;

/**
 * Created by Raluca on 16.09.2017.
 */

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.nttdata.parkingmobile.ClaimActivity;

import org.json.JSONException;;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class VacanciesTask extends AsyncTask<String, String, String> implements CredentialInterface {

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

        Scanner s = new Scanner(connection.getInputStream()).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        return result;
    }

    public VacanciesTask() {

        Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath("vacancies").build();
        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        String response = String.valueOf(o);

        if (vacanciesDelegate != null) {
            vacanciesDelegate.onVacanciesDone(response);
        }

    }

    public VacanciesDelegate getDelegate() {
        return vacanciesDelegate;
    }

    public void setVacanciesDelegate(ClaimActivity vacanciesDelegate) {
        this.vacanciesDelegate = vacanciesDelegate;
    }
}


