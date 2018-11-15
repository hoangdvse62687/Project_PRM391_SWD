package com.example.dell.booking_cyber.Model;

import android.os.StrictMode;

import com.example.dell.booking_cyber.Constant.LocaleData;
import com.example.dell.booking_cyber.DTO.ConfigurationDTO;
import com.example.dell.booking_cyber.DTO.RoomDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class RoomManager {
  private Gson gson = new Gson();

  public List<RoomDTO> getRoomsByCyberId(Integer cyberId) {
    List<RoomDTO> data = null;
    try {
      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
      StrictMode.setThreadPolicy(policy);
      HttpClient httpclient = new DefaultHttpClient();
      HttpGet httpGet = new HttpGet(LocaleData.ROOM_GETBY_CYBER_ID+cyberId);

      HttpResponse response = httpclient.execute(httpGet);
      BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
      String json = reader.readLine();
      if(LocaleData.HandleErrorMessageResponse(response.getStatusLine().getStatusCode())){
        if(json != null){
          TypeToken<List<RoomDTO>> typeToken = new TypeToken<List<RoomDTO>>(){};
          data = gson.fromJson(json,typeToken.getType());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      return data;
    }
  }
}
