package com.example.dell.booking_cyber.Constant;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class LocaleData {
    public static final String ROLE_USER = "user";

    public static final String AUTHENCATION_CHECKLOGIN_URL = "https://swd-backend-lamtt.herokuapp.com/authencation/checkLogin";

    public static final String ACCOUNT_CREATE_URL = "https://swd-backend-lamtt.herokuapp.com/account";

    public static final String CUSTOMER_GETBYACCOUNTID_URL = "https://swd-backend-lamtt.herokuapp.com/customer/getByAccountId?accountId=";
    public static final String CUSTOMER_CREATE_URL = "https://swd-backend-lamtt.herokuapp.com/customer";

    public static final String CYBER_GETALL_URL = "https://swd-backend-lamtt.herokuapp.com/cyber";
    public static final String CYBER_GETCYBERBYID_URL = "https://swd-backend-lamtt.herokuapp.com/cyber/";

    public static final String CONFIGURATION_GETBYCYBERID_URL = "https://swd-backend-lamtt.herokuapp.com/configuration/getByCyberId/";

    public static final String IMAGE_GETBYCYBERID_URL = "https://swd-backend-lamtt.herokuapp.com/image/getByCyberId?cyberId=";

    public static boolean HandleErrorMessageResponse(Integer statusResponse){
        switch (statusResponse){
            case 500:
                return false;
            case 401:
                return false;
            case 404:
                return false;
            case 200:
                return true;
        }
        return true;
    }

    public static Bitmap loadBitmap(String url) throws MalformedURLException,IOException{
        Bitmap bit=null;
        bit = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
        return bit;
    }
}
