package com.example.dell.booking_cyber;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dell.booking_cyber.Constant.LocaleData;
import com.example.dell.booking_cyber.DTO.CustomerDTO;
import com.example.dell.booking_cyber.DTO.CyberGamingDTO;
import com.example.dell.booking_cyber.DTO.ServiceRequestDetailDTO;
import com.example.dell.booking_cyber.Model.AccountManager;
import com.example.dell.booking_cyber.Model.CybercoreManager;

import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ServiceRequestDetail extends AppCompatActivity {
  private Dialog dialog;

  private TextView txtCybercoreName, txtAddress, txtEvaluation;
  private TextView txtnumOfSeat, txtGoingDate, txtGoingTime, txtDuration, txtPrice, txtStatus, txtBookingDate;
  private ImageView imgUserIcon, imgCybercoreIcon, imgEditBooking, imgDeleteBooking;
  RatingBar ratingStar;

  private Intent intent;
  private Bundle extras;

  private ServiceRequestDetailDTO serviceRequestDetailDTO;
  private CyberGamingDTO cyberGamingDTO;
  private CustomerDTO customerDTO;
  private ImageView imgBack;
  private LinearLayout layoutEvaluation, lEvaluation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_service_request_detail);

    // Get intent & bundle
    intent = getIntent();

    // Get dialog
    dialog = new Dialog(this);

    serviceRequestDetailDTO = (ServiceRequestDetailDTO) intent.getSerializableExtra("serviceRequestDetailDTO");

    // Get cyber_gaming
    CybercoreManager cybercoreManager = new CybercoreManager();
    cyberGamingDTO = cybercoreManager.getCyberById(serviceRequestDetailDTO.getCyberGamingId());

    // Get current customer
    AccountManager accountManager = new AccountManager(ServiceRequestDetail.this);
    accountManager.getAccount();
    customerDTO = accountManager.customerDTO;

    // These variable is used to check the status of service request details
    boolean isPaid = serviceRequestDetailDTO.getPaid();
    boolean isExpired = serviceRequestDetailDTO.getGoingDate().compareTo(new Date()) < 0;
    boolean isApproved = serviceRequestDetailDTO.getApproved();

    identifyElements();
    setElementsShowAndDisplay(isPaid, isExpired, isApproved);
    setElementColor(isPaid, isExpired, isApproved);

    onCloseListener();
    onEditListener();
    onDeleteListener();
    onEvaluateListener(isExpired);
  }

  private void identifyElements() {
    txtCybercoreName = findViewById(R.id.txtCybercoreName);
    txtAddress = findViewById(R.id.txtAddress);
    imgUserIcon = findViewById(R.id.imgUserIcon);
    imgCybercoreIcon = findViewById(R.id.imgCybercoreIcon);
    txtEvaluation = findViewById(R.id.txtEvaluation);
    imgEditBooking = findViewById(R.id.imgEditBooking);
    imgDeleteBooking = findViewById(R.id.imgDeleteBooking);
    txtnumOfSeat = findViewById(R.id.txtnumOfSeat);
    txtGoingDate = findViewById(R.id.txtGoingDate);
    txtGoingTime = findViewById(R.id.txtGoingTime);
    txtDuration = findViewById(R.id.txtDuration);
    txtPrice = findViewById(R.id.txtPrice);
    txtStatus = findViewById(R.id.txtStatus);
    txtBookingDate = findViewById(R.id.txtBookingDate);
    ratingStar = findViewById(R.id.ratingStar);

    imgBack = findViewById(R.id.imgBack);
    layoutEvaluation = findViewById(R.id.layoutEvaluation);
    lEvaluation = findViewById(R.id.lEvaluation);
  }

  private void setElementsShowAndDisplay(boolean isPaid, boolean isExpired, boolean isApproved) {
    String cyberCoreName = serviceRequestDetailDTO.getCyberGamingName();
    String cyberCoreAddress = cyberGamingDTO.getAddress();
    Bitmap userIcon = getImageFromUrl(customerDTO.getAvatar());
    Bitmap cyberCoreIcon = getImageFromUrl(cyberGamingDTO.getLogo());

    String evaluation = serviceRequestDetailDTO.getStar() > 0 ?
            LocaleData.YOUR_EVALUATION
            : LocaleData.NOT_EVALUATED;

    String numberOfSeat = serviceRequestDetailDTO.getNumberOfServiceSlot().toString();

    String hour, minute;

    String goingDate = new SimpleDateFormat(LocaleData.VIETNAMEESE_DATE_FORMAT)
            .format(serviceRequestDetailDTO.getGoingDate());

    String goingTime = new SimpleDateFormat(LocaleData.HOUR_AND_MINUTE_FORMAT)
            .format(serviceRequestDetailDTO.getGoingDate());
    goingTime = goingTime.replace(":", LocaleData.HOUR + " ") + LocaleData.MINUTE;

    hour = String.valueOf((int) (serviceRequestDetailDTO.getDuration() / 60));
    minute = String.valueOf((int) (serviceRequestDetailDTO.getDuration() % 60));

    String duration = (Integer.parseInt(hour) > 0) ?
            hour + LocaleData.HOUR + " " + minute + LocaleData.MINUTE
            : minute + LocaleData.MINUTE;

    String price = NumberFormat.getCurrencyInstance(new Locale(LocaleData.VIETNAMESE_LANGUAGE, LocaleData.VIETNAMESE_COUNTRY))
            .format(serviceRequestDetailDTO.getTotalPrice()).replace("US$", "");

    String status = isPaid ?
            LocaleData.ARRIVED
            : isExpired ?
            LocaleData.NOT_COMPLETE
            : isApproved ?
            LocaleData.NOT_YET_ARRIVED
            : LocaleData.WAITING_TO_APPROVED;

    String bookingDate = new SimpleDateFormat(LocaleData.VIETNAMEESE_DATE_FORMAT)
            .format(serviceRequestDetailDTO.getDateRequest());

    int stars = serviceRequestDetailDTO.getStar();


    txtCybercoreName.setText(cyberCoreName);
    txtAddress.setText(cyberCoreAddress);

    if (userIcon != null) { imgUserIcon.setImageBitmap(userIcon); }
    if (cyberCoreIcon != null) { imgCybercoreIcon.setImageBitmap(cyberCoreIcon); }

    txtEvaluation.setText(evaluation);

    if (isExpired) {
      imgDeleteBooking.setVisibility(View.INVISIBLE);
      imgEditBooking.setVisibility(View.INVISIBLE);
    }

    txtnumOfSeat.setText(numberOfSeat);
    txtGoingDate.setText(goingDate);
    txtGoingTime.setText(goingTime);
    txtDuration.setText(duration);
    txtPrice.setText(price);
    txtStatus.setText(status);
    txtBookingDate.setText(bookingDate);

    ratingStar.setRating(stars);
  }

  private Bitmap getImageFromUrl(String... urls) {
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

  private void setElementColor(boolean isPaid, boolean isExpired, boolean isApproved) {
    int green = ContextCompat.getColor(this, R.color.green);
    int orange = ContextCompat.getColor(this, R.color.orange);
    int red = ContextCompat.getColor(this, R.color.red);
    int blue = ContextCompat.getColor(this, R.color.blue);

    txtStatus.setTextColor(isPaid ?
            green
            : isExpired ?
            red
            : isApproved ?
            orange
            : blue);
  }

  private void onCloseListener() {
    this.imgBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  private void onEditListener() {
//    this,imgEditBooking.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//
//      }
//    });
  }

  private void onDeleteListener() {
//    this.imgDeleteBooking.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        dialog.setContentView(R.layout.delete_confirmation_popup);
//
//        // Identify elements in popup
//        Button btnDelete = dialog.findViewById(R.id.btnDelete);
//        Button btnGoBack = dialog.findViewById(R.id.btnGoback);
//
//        // Set listener for buttons
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
////            BookingList.bookingList.remove(booking);
//            finish();
//            dialog.dismiss();
//          }
//        });
//
//        btnGoBack.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//            dialog.dismiss();
//          }
//        });
//
//        // This is used to fill popup with the window
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        // Start dialog
//        dialog.show();
//      }
//    });
  }

  private void onEvaluateListener(boolean isExpired) {
    // Set logic for booking to be evalutate
    if (isExpired & serviceRequestDetailDTO.getDone()) {
      if (serviceRequestDetailDTO.getStar() == 0) {
        this.lEvaluation.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(ServiceRequestDetail.this, Evaluation.class);
            intent.putExtra("serviceRequestDetailDTO", serviceRequestDetailDTO);
            startActivity(intent);
          }
        });
      } else {
        // Do nothing
      }
    } else {
      layoutEvaluation.setVisibility(View.GONE);
    }
  }
}
