package com.example.dell.booking_cyber;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.dell.booking_cyber.DTO.ServiceRequestDTO;
import com.example.dell.booking_cyber.DTO.ServiceRequestDetailDTO;
import com.example.dell.booking_cyber.Helper.DialogHelper;
import com.example.dell.booking_cyber.Model.AccountManager;
import com.example.dell.booking_cyber.Model.CybercoreManager;
import com.example.dell.booking_cyber.Model.ServiceRequestManager;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Callable;

public class ServiceRequestDetail extends AppCompatActivity {
  private final int EVALUATION_REQUEST_CODE = 1;
  private final int EDIT_REQUEST_CODE = 2;

  private TextView txtCybercoreName, txtAddress, txtEvaluation;
  private TextView txtnumOfSeat, txtGoingDate, txtGoingTime, txtDuration, txtPrice, txtStatus, txtBookingDate, txtRoom, txtConfiguration;
  private ImageView imgUserIcon, imgCybercoreIcon, imgEditBooking, imgDeleteBooking, imgQrCode;
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
    txtRoom = findViewById(R.id.txtRoom);
    txtConfiguration = findViewById(R.id.txtConfiguration);
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
    imgQrCode = findViewById(R.id.imgQrCode);
  }

  private void setElementsShowAndDisplay(boolean isPaid, boolean isExpired, boolean isApproved) {
    String cyberCoreName = serviceRequestDetailDTO.getCyberGamingName();
    String cyberCoreAddress = cyberGamingDTO.getAddress();
    Bitmap userIcon = LocaleData.getImageFromUrl(customerDTO.getAvatar());
    Bitmap cyberCoreIcon = LocaleData.getImageFromUrl(cyberGamingDTO.getLogo());

    String evaluation = serviceRequestDetailDTO.getStar() > 0 ?
            LocaleData.YOUR_EVALUATION
            : LocaleData.NOT_EVALUATED;

    String numberOfSeat = serviceRequestDetailDTO.getNumberOfServiceSlot().toString();
    String room = serviceRequestDetailDTO.getRoomname();
    String configuration = serviceRequestDetailDTO.getConfigurationName();

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

    if (status.equals(LocaleData.NOT_COMPLETE) || status.equals(LocaleData.WAITING_TO_APPROVED)) {
      imgQrCode.setVisibility(View.GONE);
    }

    txtnumOfSeat.setText(numberOfSeat);
    txtRoom.setText(room);
    txtConfiguration.setText(configuration);
    txtGoingDate.setText(goingDate);
    txtGoingTime.setText(goingTime);
    txtDuration.setText(duration);
    txtPrice.setText(price);
    txtStatus.setText(status);
    txtBookingDate.setText(bookingDate);

    ratingStar.setRating(stars);
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
    imgEditBooking.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(ServiceRequestDetail.this, ServiceRequestNew.class);
        intent.putExtra("serviceRequestDetailDTO", serviceRequestDetailDTO);
        startActivityForResult(intent, EDIT_REQUEST_CODE);
      }
    });
  }

  private void onDeleteListener() {
    final AlertDialog.Builder deleteConfirmationDialog = DialogHelper.createAlertDialogBuilder(
            ServiceRequestDetail.this,
            LocaleData.DELETE_COMFIRMATION,
            LocaleData.YES,
            LocaleData.NO,
            new Callable() {
              @Override
              public Object call() throws Exception {
                deleteServiceRequest();
                return null;
              }
            });

    imgDeleteBooking.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        deleteConfirmationDialog.create().show();
      }
    });
  }

  private void deleteServiceRequest() {
    ServiceRequestManager serviceRequestManager = new ServiceRequestManager();
    if (serviceRequestManager.deleteServiceRequestByid(serviceRequestDetailDTO.getId())) {
      final AlertDialog.Builder successDialog = DialogHelper.createAlertDialogBuilder(
              ServiceRequestDetail.this,
              LocaleData.DELETE_SUCCESS,
              LocaleData.FINISH,
              new Callable() {
                @Override
                public Object call() throws Exception {
                  finish();
                  return null;
                }
              });
      setResult(Activity.RESULT_OK);
      successDialog.create().show();
    } else {
      final AlertDialog.Builder failedDialog = DialogHelper.createAlertDialogBuilder(
              ServiceRequestDetail.this,
              LocaleData.DELETE_FAILED,
              LocaleData.OK);
      failedDialog.create().show();
    };
  }

  private void onEvaluateListener(boolean isExpired) {
    // Set logic for booking to be evalutate
    if (isExpired & serviceRequestDetailDTO.getDone()) {
      if (ratingStar.getRating() == 0) {
        this.lEvaluation.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(ServiceRequestDetail.this, Evaluation.class);
            intent.putExtra("serviceRequestDetailDTO", serviceRequestDetailDTO);
            intent.putExtra("cyberGamingDTO", cyberGamingDTO);
            startActivityForResult(intent, EVALUATION_REQUEST_CODE);
          }
        });
      } else {
        // Do nothing
      }
    } else {
      layoutEvaluation.setVisibility(View.GONE);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case EVALUATION_REQUEST_CODE:
        if (resultCode == RESULT_OK) {
          ratingStar.setRating(data.getIntExtra("numOfStar", 0));
        }
        break;
      case EDIT_REQUEST_CODE:
        if (resultCode == RESULT_OK) {
          updateUiWhenEditSuccessful(data);
        }
        break;
      default:
        break;
    }
  }

  private void updateUiWhenEditSuccessful(Intent data) {
    ServiceRequestManager serviceRequestManager = new ServiceRequestManager();
    serviceRequestDetailDTO = serviceRequestManager.getServiceRequestById(serviceRequestDetailDTO.getId());

    String numberOfSeat = serviceRequestDetailDTO.getNumberOfServiceSlot().toString();
    String room = serviceRequestDetailDTO.getRoomname();
    String configuration = serviceRequestDetailDTO.getConfigurationName();

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

    txtnumOfSeat.setText(numberOfSeat);
    txtRoom.setText(room);
    txtConfiguration.setText(configuration);
    txtGoingDate.setText(goingDate);
    txtGoingTime.setText(goingTime);
    txtDuration.setText(duration);
    txtPrice.setText(price);
    txtStatus.setText(LocaleData.WAITING_TO_APPROVED);
  }
}
