package com.example.dell.booking_cyber;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dell.booking_cyber.Constant.LocaleData;
import com.example.dell.booking_cyber.DTO.CyberGamingDTO;
import com.example.dell.booking_cyber.DTO.ServiceRequestDTO;
import com.example.dell.booking_cyber.DTO.ServiceRequestDetailDTO;
import com.example.dell.booking_cyber.Model.ServiceRequestManager;

import java.io.InputStream;
import java.util.Date;

public class Evaluation extends AppCompatActivity {
  private ImageView imgCybercoreIcon, imgBack;
  private TextView txtCybercoreName, txtAddress;
  private EditText edtEvaluation;
  private TextView txtEvaluationError;
  private Button btnEvaluation;
  private RatingBar ratingStar;

  private Intent intent;
  private ServiceRequestDetailDTO serviceRequestDetailDTO;
  private CyberGamingDTO cyberGamingDTO;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.evaluation);

    intent = getIntent();

    serviceRequestDetailDTO = (ServiceRequestDetailDTO) intent.getSerializableExtra("serviceRequestDetailDTO");
    cyberGamingDTO= (CyberGamingDTO) intent.getSerializableExtra("cyberGamingDTO");

    identifyElements();
    setElementsShowAndDisplay();
    onRatingStartListener();
    onEvaluateClickListener();
    onCloseListener();
  }

  private void identifyElements() {
    imgCybercoreIcon = findViewById(R.id.imgCybercoreIcon);
    txtCybercoreName = findViewById(R.id.txtCybercoreName);
    txtAddress = findViewById(R.id.txtAddress);
    imgBack = findViewById(R.id.imgBack);
    edtEvaluation = findViewById(R.id.edtEvaluation);
    txtEvaluationError = findViewById(R.id.txtEvaluationError);
    btnEvaluation = findViewById(R.id.btnEvaluation);
    ratingStar = findViewById(R.id.ratingStar);
  }

  private void setElementsShowAndDisplay() {
    Bitmap cyberCoreIcon = LocaleData.getImageFromUrl(cyberGamingDTO.getLogo());
    if (cyberCoreIcon != null) { imgCybercoreIcon.setImageBitmap(cyberCoreIcon); }
    txtCybercoreName.setText(cyberGamingDTO.getName());
    txtAddress.setText(cyberGamingDTO.getAddress());
    txtEvaluationError.setText("");
  }

  private void onRatingStartListener() {
    ratingStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
      @Override
      public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        ratingStar.setRating(rating);
      }
    });
  }

  private void onEvaluateClickListener() {
    btnEvaluation.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (validate()) {
          // Set params
//          ServiceRequestDTO serviceRequestDTO = modelMapper.map(serviceRequestDetailDTO, ServiceRequestDTO.class);
          boolean active = serviceRequestDetailDTO.getActive();
          boolean approved = serviceRequestDetailDTO.getApproved();
          String code = serviceRequestDetailDTO.getCode();
          int configurationId = serviceRequestDetailDTO.getConfigurationId();
          int cyberGamingId = serviceRequestDetailDTO.getCyberGamingId();
          Date dateRequest = serviceRequestDetailDTO.getDateRequest();
          boolean deleted = serviceRequestDetailDTO.getDeleted();
          boolean done = serviceRequestDetailDTO.getDone();
          double duration = serviceRequestDetailDTO.getDuration();
          String evaluation = edtEvaluation.getText().toString();
          Date goingDate = serviceRequestDetailDTO.getGoingDate();
          int id = serviceRequestDetailDTO.getId();
          double latitude = serviceRequestDetailDTO.getLatitude();
          double longitude = serviceRequestDetailDTO.getLongitude();
          int numberOfServiceSlot = serviceRequestDetailDTO.getNumberOfServiceSlot();
          boolean paid = serviceRequestDetailDTO.getPaid();
          Date paidDate = serviceRequestDetailDTO.getPaidDate();
          int roomId = serviceRequestDetailDTO.getRoomId();
          int star = (int) ratingStar.getRating();
          double totalPrice = serviceRequestDetailDTO.getTotalPrice();
          int userId = serviceRequestDetailDTO.getUserId();

          ServiceRequestDTO serviceRequestDTO = new ServiceRequestDTO(
                  id,
                  userId,
                  cyberGamingId,
                  duration,
                  numberOfServiceSlot,
                  done,
                  paid,
                  paidDate,
                  dateRequest,
                  goingDate,
                  evaluation,
                  star,
                  longitude,
                  latitude,
                  code,
                  totalPrice,
                  roomId,
                  configurationId,
                  approved,
                  active,
                  deleted
          );
//          serviceRequestDetailDTO.setStar((int) ratingStar.getRating());
//          serviceRequestDetailDTO.setEvaluation(edtEvaluation.getText().toString());

          // Send put request
          ServiceRequestManager serviceRequestManager = new ServiceRequestManager();
          serviceRequestManager.ratingServiceRequest(serviceRequestDTO);

          // Oo back to service request detail & update UI
          Intent resultIntent = new Intent();
          resultIntent.putExtra("numOfStar", (int) ratingStar.getRating());
          setResult(Activity.RESULT_OK, resultIntent);
          finish();
        }
      }
    });
  }

  private boolean validate() {
    boolean valid = true;
    if (ratingStar.getRating() == 0) {
      txtEvaluationError.setText(LocaleData.STAR_NOT_RATING);
      valid = false;
    } else if (edtEvaluation.getText().toString().isEmpty()) {
      txtEvaluationError.setText(LocaleData.EVALUATE_REQUIRED);
      valid = false;
    } else if (edtEvaluation.getText().toString().length() > 500) {
      txtEvaluationError.setText(LocaleData.LONG_EVALUATION);
      valid = false;
    } else {
      txtEvaluationError.setText("");
    }
    return valid;
  }

  private void onCloseListener() {
    this.imgBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }
}
