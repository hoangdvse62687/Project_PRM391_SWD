package com.example.dell.booking_cyber.Constant;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

public class LocaleData {
    public static final String ROLE_USER = "user";

    public static final String AUTHENCATION_CHECKLOGIN_URL = "https://swd-backend-lamtt.herokuapp.com/authencation/checkLogin";

    public static final String ACCOUNT_CREATE_URL = "https://swd-backend-lamtt.herokuapp.com/account";
    public static final String ACCOUNT_GETALL_URL = "https://swd-backend-lamtt.herokuapp.com/account";

    public static final String CUSTOMER_GETBYACCOUNTID_URL = "https://swd-backend-lamtt.herokuapp.com/customer/getByAccountId?accountId=";
    public static final String CUSTOMER_CREATE_URL = "https://swd-backend-lamtt.herokuapp.com/customer";

    public static final String CYBER_GETALL_URL = "https://swd-backend-lamtt.herokuapp.com/cyber";
    public static final String CYBER_GETCYBERBYID_URL = "https://swd-backend-lamtt.herokuapp.com/cyber/";

    public static final String CONFIGURATION_GETBYCYBERID_URL = "https://swd-backend-lamtt.herokuapp.com/configuration/getByCyberId/";

    public static final String IMAGE_GETBYCYBERID_URL = "https://swd-backend-lamtt.herokuapp.com/image/getByCyberId?cyberId=";

    public static final String SERVICEREQUEST_GETBYACCOUNTID_URL = "https://swd-backend-lamtt.herokuapp.com/serviceRequest/getByAccountRequestId/";
    public static final String SERVICEREQUEST_GETBYID_URL = "https://swd-backend-lamtt.herokuapp.com/serviceRequest/";
    public static final String SERVICEREQUEST_UPDATE_URL = "https://swd-backend-lamtt.herokuapp.com/serviceRequest";

    public static int TIME_ZONE = 7;

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

    public static Date addHours(Date currentTime,int number){
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(currentTime); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, number); // adds one hour
        return cal.getTime();
    }

    public static Bitmap renderQRCode(String barcode_content)throws WriterException{
        if(barcode_content == null && barcode_content.isEmpty()){
            return null;
        }
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(barcode_content, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,200, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
