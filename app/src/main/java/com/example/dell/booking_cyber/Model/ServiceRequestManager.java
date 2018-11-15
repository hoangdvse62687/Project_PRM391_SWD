package com.example.dell.booking_cyber.Model;

import android.os.StrictMode;

import com.example.dell.booking_cyber.Constant.LocaleData;
import com.example.dell.booking_cyber.DTO.ServiceRequestDTO;
import com.example.dell.booking_cyber.DTO.ServiceRequestDetailDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

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

    public boolean updateDoneStatus(Integer requestServiceId){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut(LocaleData.SERVICEREQUEST_UPDATE_COMPLATE_URL+requestServiceId);

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

    public boolean ratingServiceRequest(ServiceRequestDTO dto) {
        Gson gson = new Gson();
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut(LocaleData.SERVICEREQUEST_UPDATE);

            // Add params to put request
            StringEntity params = new StringEntity(gson.toJson(dto));
            httpPut.setHeader("Content-type", "application/json");
            httpPut.setEntity(params);

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

    public ServiceRequestDTO createServiceRequest(ServiceRequestDTO dto) {
        boolean result = false;
        Gson gson = new GsonBuilder().setDateFormat(LocaleData.DATE_FORMAT).create();
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(LocaleData.SERVICEREQUEST_CREATE);

            // Add params to post request
            StringEntity params = new StringEntity(gson.toJson(dto));
            gson.toJson(dto);
            params.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(params);

            HttpResponse response = httpclient.execute(httpPost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String returnJson = reader.readLine();
            if(LocaleData.HandleErrorMessageResponse(response.getStatusLine().getStatusCode())){
                if(returnJson != null){
                    ServiceRequestDTO data = gson.fromJson(returnJson,ServiceRequestDTO.class);
                    return data;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
        return null;
    }

    public boolean updateServiceRequest(ServiceRequestDTO dto) {
        boolean result = false;
        Gson gson = new GsonBuilder().setDateFormat(LocaleData.DATE_FORMAT).create();
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut(LocaleData.SERVICEREQUEST_UPDATE);

            // Add params to put request
            StringEntity params = new StringEntity(gson.toJson(dto));
            gson.toJson(dto);
            params.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPut.setEntity(params);

            HttpResponse response = httpclient.execute(httpPut);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String returnJson = reader.readLine();
            if(LocaleData.HandleErrorMessageResponse(response.getStatusLine().getStatusCode())){
                if(returnJson != null){
                    result = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return result;
        }
    }

    public boolean deleteServiceRequestByid(Integer serviceRequestId) {
        boolean result = false;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpClient httpclient = new DefaultHttpClient();
            HttpDelete httpDelete = new HttpDelete(LocaleData.SERVICEREQUEST_DELETE + serviceRequestId);

            HttpResponse response = httpclient.execute(httpDelete);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String returnJson = reader.readLine();
            if(LocaleData.HandleErrorMessageResponse(response.getStatusLine().getStatusCode())){
                if(returnJson != null){
                    result = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return result;
        }
    }
}
