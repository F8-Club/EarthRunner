package nl.first8.dc.earthrunnerapp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.util.Log;

public class UpdateServerTask extends TimerTask {
    
    private String host = "192.168.1.228"; 
    private static final int PORT = 8080;
    private static final String SERVER_PATH = "EarthRunnerServer/update";
    private static final String SCHEME = "http";
    private static final String PARAM_UUID = "uuid";
    private static final String PARAM_SPEED = "speed";
    private StepService service;
    private HttpClient httpClient; 
    
    public UpdateServerTask(StepService service)  {
        this.service = service;
        httpClient = new DefaultHttpClient();
    }


    @Override
    public void run() {
        float speed = service.getCurrentSpeed();
        String uuid = service.getServerConnectionString();
        updateServer(speed, uuid);
    }


    private void updateServer(float speed, String uuid) {
        if (uuid == null) {
            Log.d("UpdateServerTask", "No server is known, skipping update");
            return;
        }
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(PARAM_SPEED, Float.valueOf(speed).toString()));
        params.add(new BasicNameValuePair(PARAM_UUID,uuid));
        String query = URLEncodedUtils.format(params, HTTP.UTF_8);
        try {
            URI uri = URIUtils.createURI(SCHEME, service.getServerConnectionString(), PORT, SERVER_PATH, query, null);
            HttpGet httpGet = new HttpGet(uri);
            HttpResponse response = httpClient.execute(httpGet);
            response.getEntity().consumeContent();
        } catch (URISyntaxException e) {
            Log.e("UpdateServerTask URISyntaxException", e.getMessage());
        } catch (IOException e) {
            Log.e("UpdateServerTask IOException", e.getMessage());
        } 
        
    }

}
