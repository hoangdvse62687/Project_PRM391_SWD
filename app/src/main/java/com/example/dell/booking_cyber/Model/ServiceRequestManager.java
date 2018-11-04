package com.example.dell.booking_cyber.Model;

import android.os.StrictMode;

import com.example.dell.booking_cyber.Constant.LocaleData;
import com.example.dell.booking_cyber.DTO.ConfigurationDTO;
import com.example.dell.booking_cyber.DTO.CustomerDTO;
import com.example.dell.booking_cyber.DTO.ServiceRequestDTO;
import com.example.dell.booking_cyber.DTO.ServiceRequestDetailDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class ServiceRequestManager {
    Gson gson = new Gson();

    public List<ServiceRequestDetailDTO> getServiceRequestByCustomerId(Integer customerID){
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

    public ServiceRequestDetailDTO getServiceRequestById(Integer Id){
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(LocaleData.SERVICEREQUEST_GETBYID_URL+Id);

            HttpResponse response = httpclient.execute(httpGet);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String json = reader.readLine();
            if(LocaleData.HandleErrorMessageResponse(response.getStatusLine().getStatusCode())){
                if(json != null){
                    ServiceRequestDetailDTO data = gson.fromJson(json,ServiceRequestDetailDTO.class);
                    return data;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public boolean updateServiceRequest(ServiceRequestDTO detail){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut(LocaleData.SERVICEREQUEST_UPDATE_URL);

            JSONObject json = new JSONObject();
            json.put("id",detail.getId());
            json.put("active",detail.getActive());
            json.put("approved",detail.getApproved());
            json.put("code",detail.getCode());
            json.put("configurationId",detail.getConfigurationId());
            json.put("cyberGamingId", detail.getCyberGamingId());
            json.put("dateRequest", detail.getDateRequest());
            json.put("deleted", detail.getDeleted());
            json.put("done",detail.getDone());
            json.put("duration",detail.getDuration());
            json.put("evaluation",detail.getEvaluation());
            json.put("goingDate",detail.getGoingDate());
            json.put("latitude",detail.getLatitude());
            json.put("longitude",detail.getLongitude());
            json.put("numberOfServiceSlot",detail.getNumberOfServiceSlot());
            json.put("paid",detail.getPaid());
            json.put("paidDate",detail.getPaidDate());
            json.put("roomId",detail.getRoomId());
            json.put("star",detail.getStar());
            json.put("totalPrice",detail.getTotalPrice());
            json.put("userId",detail.getUserId());

            StringEntity se = new StringEntity( json.toString());
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPut.setEntity(se);
            HttpResponse response = httpclient.execute(httpPut);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String returnJson = reader.readLine();
            if(LocaleData.HandleErrorMessageResponse(response.getStatusLine().getStatusCode())){
                if(returnJson != null){
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
