package webservice;


import java.io.UnsupportedEncodingException;

public interface LoginDelegate {

    public void onLoginDone(String result) throws UnsupportedEncodingException;
}
