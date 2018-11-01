package com.example.dell.booking_cyber.Model;

import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.example.dell.booking_cyber.Constant.LocaleData;
import com.example.dell.booking_cyber.DTO.AccountDTO;
import com.example.dell.booking_cyber.DTO.UserDTO;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AccountManager extends Activity {
    Gson gson = new Gson();
    AccountDTO account = new AccountDTO();
    public UserDTO userDTO = new UserDTO();
    final String fileDirectory = "Token.txt";
    private static final int READ_BLOCK_SIZE = 500;

    final Integer INDEX_EMAIL = 0;
    final Integer INDEX_PASSWORD = 1;
    final Integer INDEX_ROLE = 2;
    final Integer INDEX_NAME = 3;
    final Integer INDEX_PHONE = 4;
    final Integer INDEX_GENDER = 5;

    final String JSON_USERNAME_RETURN_KEY = "username";
    final String JSON_PASSWORD_RETURN_KEY = "password";
    final String JSON_ROLE_RETURN_KEY = "role";
    Context context;

    public AccountManager(Context context) {
        this.context = context;
    }

    public boolean checkLogin(String email, String password) {
        if (!postDataCheckLogin(email, password))
            return false;
            // role admin cannot login
        else if (account != null && account.getRole().equals(LocaleData.ROLE_USER)) {
            //Get Detail account
            if (getDataUserDTOByAccountId(account.getId())) {
                if (writeFileToken(account, userDTO))
                    return true;
            }
        }
        return false;
    }

    public boolean isLogin() {
        //check login in cache data
        if (readFileToken()) {
            if (account != null) {
                //matching data
                if (postDataCheckLogin(account.getUsername(), account.getPassword())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void logout() {
        try {
            File dir = context.getFilesDir();
            File file = new File(dir, fileDirectory);
            file.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean signup(AccountDTO accountDTO, UserDTO userDetail) {
        //Insert data to database here
        if(createAccount(accountDTO,userDetail)){
            //Write data into token
            return writeFileToken(accountDTO, userDetail);
        }
        return false;
    }

    public AccountDTO getAccount() {
        if (readFileToken())
            return account;
        else
            return null;
    }

    private boolean readFileToken() {
        FileInputStream fis = null;
        InputStreamReader isr = null;

        try {
            fis = context.openFileInput(fileDirectory);
            isr = new InputStreamReader(fis);
            char[] buffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;
            while ((charRead = isr.read(buffer)) > 0) {
                String reading = String.copyValueOf(buffer, 0, charRead);
                s += reading;
                buffer = new char[READ_BLOCK_SIZE];
            }
            String[] data = s.split("-");
            if (data.length == 6) {
                account = new AccountDTO(data[INDEX_EMAIL], data[INDEX_PASSWORD], data[INDEX_ROLE], true, false);
                userDTO = new UserDTO(data[INDEX_NAME], "", data[INDEX_EMAIL], data[INDEX_PHONE]
                        , Double.parseDouble(data[INDEX_GENDER]), true, false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fis != null)
                    fis.close();
            } catch (Exception ex) {

            }
        }
        return true;
    }

    private boolean writeFileToken(AccountDTO data, UserDTO detailUser) {
        if (data == null)
            return false;
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            File file = new File(context.getApplicationContext().getFilesDir(), fileDirectory);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = context.openFileOutput(fileDirectory, MODE_PRIVATE);
            osw = new OutputStreamWriter(fos);
            osw.write(data.getUsername() + "-");
            osw.write(data.getPassword() + "-");
            osw.write(data.getRole() + "-");
            osw.write(detailUser.getName() + "-");
            osw.write(detailUser.getPhone() + "-");
            osw.write(detailUser.getSex().toString() + "-");
            osw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (osw != null)
                    osw.close();
                if (fos != null)
                    fos.close();
            } catch (Exception ex) {

            }
        }
        return true;
    }

    private boolean postDataCheckLogin(String username, String password) {
        // Create a new HttpClient and Post Header
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(LocaleData.AUTHENCATION_CHECKLOGIN_URL);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair(JSON_PASSWORD_RETURN_KEY, password));
            nameValuePairs.add(new BasicNameValuePair(JSON_USERNAME_RETURN_KEY, username));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String data = reader.readLine();
            if (data == null) {
                return false;
            }
            account = gson.fromJson(data, AccountDTO.class);
            return true;
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    private boolean getDataUserDTOByAccountId(Integer Id) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String link = LocaleData.CUSTOMER_GETBYACCOUNTID_URL + Id;
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(link);
            HttpResponse response = httpclient.execute(httpGet);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String data = reader.readLine();
            if (data != null) {
                userDTO = gson.fromJson(data, UserDTO.class);
                return true;
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean createAccount(AccountDTO data, UserDTO userDetail) {
        if (data == null || userDetail == null) {
            return false;
        }
        if (postCreateAccount(data)) {
            //not yet implement post data userDetail
            if(putCreateCustomer(userDTO,account.getId()))
                return writeFileToken(account,userDTO);
        }
        return false;
    }

    private boolean postCreateAccount(AccountDTO data) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(LocaleData.ACCOUNT_CREATE_URL);

            JSONObject json = new JSONObject();
            json.put(JSON_USERNAME_RETURN_KEY, data.getUsername());
            json.put(JSON_PASSWORD_RETURN_KEY, data.getPassword());
            json.put(JSON_ROLE_RETURN_KEY, data.getRole());
            json.put("active", true);
            json.put("deleted", false);

            StringEntity se = new StringEntity( json.toString());
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httppost.setEntity(se);
            HttpResponse response = httpclient.execute(httppost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String returnJson = reader.readLine();
            if (data == null) {
                return false;
            }
            account = gson.fromJson(returnJson, AccountDTO.class);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean putCreateCustomer(UserDTO userDTO,Integer accountId){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut(LocaleData.CUSTOMER_CREATE_URL);

            JSONObject json = new JSONObject();
            json.put("accountId",accountId);
            json.put("active",true);
            json.put("avatar","");
            json.put("deleted",false);
            json.put("email",userDTO.getEmail());
            json.put("name",userDTO.getName());
            json.put("phone",userDTO.getPhone());
            json.put("sex",1);

            StringEntity se = new StringEntity( json.toString());
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPut.setEntity(se);
            HttpResponse response = httpclient.execute(httpPut);
            if(response!=null){
                InputStream in = response.getEntity().getContent();
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
