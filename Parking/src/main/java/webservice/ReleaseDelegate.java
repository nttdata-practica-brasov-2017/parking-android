package webservice;

public interface ReleaseDelegate {
    public void onReleaseDone(String result);

    void onReleaseError(String response);
}
