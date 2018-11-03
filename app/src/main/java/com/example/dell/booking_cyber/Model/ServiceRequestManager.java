package com.example.dell.booking_cyber.Model;

import android.os.StrictMode;

import com.example.dell.booking_cyber.Constant.LocaleData;
import com.example.dell.booking_cyber.DTO.ConfigurationDTO;
import com.example.dell.booking_cyber.DTO.ServiceRequestDetailDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class ServiceRequestManager {
    Gson gson = new Gson();

    public List<ServiceRequestDetailDTO> getConfigurationByCyberId(Integer customerID){
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(LocaleData.SERVICEREQUEST_GETBYACCOUNTID_URL+customerID);

            HttpResponse response = httpclient.execute(httpGet);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String json = reader.readLine();
            if(LocaleData.HandleErrorMessageResponse(response.getStatusLine().getStatusCode())){
                if(json != null){
                    TypeToken<List<ServiceRequestDetailDTO>> typeToken = new TypeToken<List<ServiceRequestDetailDTO>>(){};
                    List<ServiceRequestDetailDTO> data = gson.fromJson(json,typeToken.getType());
                    return data;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
