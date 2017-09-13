package webservice;

/**
 * Created by m09ny on 09/13/17.
 */

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LoginTask extends AsyncTask<String, String, Boolean> implements CredentialInterface {

    private String username;
    private String password;

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            return callLoginService();
        } catch (IOException | JSONException e) {
            Log.e("ERROR", "Failed to login.", e);
            return false;
        }
    }

    private Boolean callLoginService() throws IOException, JSONException {

        Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath("login").build();
        HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();
        connection.setRequestMethod("POST");

        JSONObject object = new JSONObject();
        object.put("username", username);
        object.put("password", password);

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(object.toString());
        out.close();

        Scanner s = new Scanner(connection.getInputStream()).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        return Boolean.valueOf(result);
    }


    public LoginTask(String username, String password) {
        this.username = username;
        this.password = password;
        Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath("login").build();

        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(Boolean o) {
        String response = String.valueOf(o);
        super.onPostExecute(o);

    }
}