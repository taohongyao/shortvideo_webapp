package shortvideo.declantea.me.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class URLRequest {

    public static String postRequest(Map<String,String> parameters, String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("?");
        int size=parameters.size();
        int index=0;
        for(String key: parameters.keySet()){
            stringBuffer.append(key).append("=").append(parameters.get(key));
            if(index!=size-1){
                stringBuffer.append("&");
            }
            index++;
        }
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setConnectTimeout(1000);
        con.setReadTimeout(1000);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(stringBuffer.toString());
        out.flush();
        out.close();
        StringBuffer response=null;
        int responseCode=con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        }
        return response.toString();
    }
}
