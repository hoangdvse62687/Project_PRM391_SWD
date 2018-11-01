package com.example.dell.booking_cyber.Model;

import android.os.StrictMode;

import com.example.dell.booking_cyber.Constant.LocaleData;
import com.example.dell.booking_cyber.DTO.AccountDTO;
import com.example.dell.booking_cyber.DTO.CyberGamingDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class CybercoreManager {
    Gson gson = new Gson();
    public List<CyberGamingDTO> getAllCybercore(){
        List<CyberGamingDTO> data = new ArrayList<>();
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(LocaleData.CYBER_GETALL_URL);

            HttpResponse response = httpclient.execute(httpGet);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String json = reader.readLine();
            TypeToken<List<CyberGamingDTO>> typeToken = new TypeToken<List<CyberGamingDTO>>(){};
            data = gson.fromJson(json,typeToken.getType());

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return data;
    }
}
