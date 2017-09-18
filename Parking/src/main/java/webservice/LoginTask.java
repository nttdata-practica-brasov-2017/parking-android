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

public class LoginTask extends AsyncTask<String, String, String> implements CredentialInterface {

    private LoginDelegate loginDelegate;
    private String username;
    private String password;

    @Override
    protected String doInBackground(String... params) {
        try {
            return callLoginService();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
       //     Log.e("ERROR", "Failed to login.", e);
            return null;
        }
    }

    private String callLoginService() throws IOException, JSONException {

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

        return result;
    }

    public LoginTask(String username, String password) {

        this.username = username;
        this.password = password;

        Uri uri = Uri.parse(BASE_URL).buildUpon().appendPath("login").build();
        this.execute(uri.toString());
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        String response = String.valueOf(o);

        if (loginDelegate != null){
            loginDelegate.onLoginDone(response);
        }

    }

    public LoginDelegate getDelegate() {
        return loginDelegate;
    }

    public void setLoginDelegate(LoginDelegate loginDelegate) {
        this.loginDelegate = loginDelegate;
    }
}