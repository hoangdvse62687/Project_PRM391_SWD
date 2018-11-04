package com.example.dell.booking_cyber;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.booking_cyber.DTO.Booking;
import com.example.dell.booking_cyber.List.BookingList;

public class Evaluation extends AppCompatActivity {
  private ImageView imgCybercoreIcon;
  private TextView txtCybercoreName, txtAddress,txtClose;
  private EditText edtEvaluation;
  private Button btnEvaluation;

  private Intent intent;
  private Bundle extras;

  private Booking booking;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.evaluation);

    // Get intent and bundle
    intent = getIntent();
    extras = intent.getExtras();

    // Identify booking to be evaluate
    if (extras != null) {
      booking = BookingList.bookingList.get(extras.getInt("position"));
    }

    identifyElements();
    setElementsShowAndDisplay();

    onCloseListener();
  }

  private void identifyElements() {
    imgCybercoreIcon = findViewById(R.id.imgCybercoreIcon);
    txtCybercoreName = findViewById(R.id.txtCybercoreName);
    txtAddress = findViewById(R.id.txtAddress);
    txtClose = findViewById(R.id.txtClose);
    edtEvaluation = findViewById(R.id.edtEvaluation);
    btnEvaluation = findViewById(R.id.btnEvaluation);
  }

  private void setElementsShowAndDisplay() {
    imgCybercoreIcon.setImageResource(booking.getCybercoreIcon());
    txtCybercoreName.setText(booking.getCybercoreName());
    txtAddress.setText(booking.getAddress());
//    txtClose
//    edtEvaluation
//    btnEvaluation
  }

  private void setElementColor() {

  }

  private void onCloseListener() {
    this.txtClose.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }
}
