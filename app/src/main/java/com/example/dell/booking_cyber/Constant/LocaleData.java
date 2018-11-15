package com.example.dell.booking_cyber.Constant;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

public class LocaleData {
    public static final String ROLE_USER = "user";

    public static final String AUTHENCATION_CHECKLOGIN_URL = "https://swd-backend-lamtt.herokuapp.com/authencation/checkLogin";

    public static final String ACCOUNT_CREATE_URL = "https://swd-backend-lamtt.herokuapp.com/accounts";
    public static final String ACCOUNT_GETALL_URL = "https://swd-backend-lamtt.herokuapp.com/accounts";

    public static final String CUSTOMER_GETBYACCOUNTID_URL = "https://swd-backend-lamtt.herokuapp.com/customers/getByAccountId?accountId=";
    public static final String CUSTOMER_CREATE_URL = "https://swd-backend-lamtt.herokuapp.com/customers";

    public static final String CYBER_GETALL_URL = "https://swd-backend-lamtt.herokuapp.com/cybers";
    public static final String CYBER_GETCYBERBYID_URL = "https://swd-backend-lamtt.herokuapp.com/cybers/";

    public static final String CONFIGURATION_GETBYCYBERID_URL = "https://swd-backend-lamtt.herokuapp.com/configurations/getByCyberId/";

    public static final String IMAGE_GETBYCYBERID_URL = "https://swd-backend-lamtt.herokuapp.com/images/getByCyberId?cyberId=";

    public static final String SERVICEREQUEST_GETBYACCOUNTID_URL = "https://swd-backend-lamtt.herokuapp.com/serviceRequests/getByAccountRequestId/";
    public static final String SERVICEREQUEST_GETBYID_URL = "https://swd-backend-lamtt.herokuapp.com/serviceRequests/";
    public static final String SERVICEREQUEST_UPDATE_COMPLATE_URL = "https://swd-backend-lamtt.herokuapp.com/serviceRequests/complete/";

    public static final String SERVICEREQUEST_UPDATE = "https://swd-backend-lamtt.herokuapp.com/serviceRequests/";
    public static final String SERVICEREQUEST_CREATE = "https://swd-backend-lamtt.herokuapp.com/serviceRequests/";
    public static final String SERVICEREQUEST_DELETE = "https://swd-backend-lamtt.herokuapp.com/serviceRequests/";

    public static final String ROOM_GETBY_CYBER_ID = "https://swd-backend-lamtt.herokuapp.com/rooms/getByCyberId/";

    public static final String VIETNAMESE_LANGUAGE = "vi";
    public static final String VIETNAMESE_COUNTRY = "VI";
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";
    public static final String HOUR_AND_MINUTE_FORMAT = "HH:mm";
    public static final String VIETNAMEESE_DATE_FORMAT = "dd/MM/yyyy";
    public static final String YOUR_EVALUATION = "Đánh giá của bạn";
    public static final String NOT_EVALUATED = "Bạn chưa đánh giá";
    public static final String HOUR = " giờ";
    public static final String MINUTE = " phút";
    public static final String ARRIVED = "Đã đến";
    public static final String NOT_COMPLETE = "Không hoàn thành";
    public static final String NOT_YET_ARRIVED = "Chưa đến";
    public static final String WAITING_TO_APPROVED = "Đang chờ kiểm duyệt";
    public static final String EVALUATE_REQUIRED = "Xin hãy nhập đánh giá của bạn";
    public static final String LONG_EVALUATION = "Đánh giá của bạn quá dài";
    public static final String STAR_NOT_RATING = "Hãy chấm sao xếp hạng của bạn";
    public static final String FIELD_EMPTY = "Xin hãy cung cấp đầy đủ thông tin";
    public static final String BOOKING_ERROR = "Đã xảy ra lỗi, vui lòng thử lại sau!";
    public static final String BOOKING_SUCCESS = "Đặt chỗ thành công";
    public static final String DELETE_COMFIRMATION = "Bạn có chắc chắn muốn xóa lịch đặt máy?";
    public static final String DELETE_SUCCESS = "Xóa thành công";
    public static final String DELETE_FAILED = "Hiện tại không thể xóa, vui lòng thử lại sau!";
    public static final String YES = "Có";
    public static final String NO = "Không";
    public static final String OK = "Đồng ý";
    public static final String FINISH = "Xong";

    public static final String GOING_DATE = "GOING_DATE";
    public static final String GOING_TIME = "GOING_TIME";
    public static final String DURATION = "DURATION";

    public static final String NO_OPTION = "...";
    public static final double PRICE_PER_MINUTE = 50;
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
            BitMatrix bitMatrix = multiFormatWriter.encode(barcode_content, BarcodeFormat.QR_CODE,100,100);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap getImageFromUrl(String... urls) {
        String urldisplay = urls[0];
        Bitmap bitmapResult = null;
        if (urldisplay != null) {
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bitmapResult = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmapResult;
    }
}
