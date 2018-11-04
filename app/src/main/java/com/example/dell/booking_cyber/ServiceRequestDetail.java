package com.example.dell.booking_cyber;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.booking_cyber.DTO.Booking;
import com.example.dell.booking_cyber.List.BookingList;

public class ServiceRequestDetail extends AppCompatActivity {
  private Dialog dialog;

  private TextView txtCybercoreName, txtAddress, txtEvaluation;
  private TextView txtnumOfSeat, txtDuration, txtTotal, txtDiscount, txtPrice, txtIsPaid, txtBookingDate;
  private ImageView imgCybercoreIcon, imgEditBooking, imgDeleteBooking, star01, star02, star03, star04, star05;

  private Intent intent;
  private Bundle extras;

  private Booking booking;
  private TextView txtClose;
  private LinearLayout lEvaluation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_service_request_detail);

    // Get intent & bundle
    intent = getIntent();
    extras = intent.getExtras();

    // Get color
    int green = ContextCompat.getColor(this, R.color.green);
    int orange = ContextCompat.getColor(this, R.color.orange);

    // Get dialog
    dialog = new Dialog(this);

    if (extras != null) {
      booking = BookingList.bookingList.get(extras.getInt("position"));
    }

    identifyElements();
    setElementsShowAndDisplay();
    setElementColor();

    onCloseListener();
    onDeleteListener();
    onEvaluateListener();
  }

  private void identifyElements() {
    txtCybercoreName = findViewById(R.id.txtCybercoreName);
    txtAddress = findViewById(R.id.txtAddress);
    imgCybercoreIcon = findViewById(R.id.imgCybercoreIcon);
    txtEvaluation = findViewById(R.id.txtEvaluation);
    imgEditBooking = findViewById(R.id.imgEditBooking);
    imgDeleteBooking = findViewById(R.id.imgDeleteBooking);
    txtnumOfSeat = findViewById(R.id.txtnumOfSeat);
    txtDuration = findViewById(R.id.txtDuration);
    txtTotal = findViewById(R.id.txtTotal);
    txtDiscount = findViewById(R.id.txtDiscount);
    txtPrice = findViewById(R.id.txtPrice);
    txtIsPaid = findViewById(R.id.txtIsPaid);
    txtBookingDate = findViewById(R.id.txtBookingDate);

    star01 = findViewById(R.id.star01);
    star02 = findViewById(R.id.star02);
    star03 = findViewById(R.id.star03);
    star04 = findViewById(R.id.star04);
    star05 = findViewById(R.id.star05);

    txtClose = findViewById(R.id.txtClose);
    lEvaluation = findViewById(R.id.lEvaluation);
  }

  private void setElementsShowAndDisplay() {
    txtCybercoreName.setText(booking.getCybercoreName());
    txtAddress.setText(booking.getAddress());
    imgCybercoreIcon.setImageResource(booking.getCybercoreIcon());
    txtEvaluation.setText(booking.getPointEvaluation() > 0 ? "Đánh giá của bạn" : "Bạn chưa đánh giá");
    if (booking.isPaid()) {
      imgDeleteBooking.setVisibility(View.INVISIBLE);
      imgEditBooking.setVisibility(View.INVISIBLE);
    }
    txtnumOfSeat.setText(Integer.toString(booking.getNumOfSeat()));
    txtDuration.setText(Integer.toString(booking.getHour()) + " giờ " + Integer.toString(booking.getMinute()) + " phút ");
    txtTotal.setText(Integer.toString(booking.getTotal()) + ".000 đ");
    txtDiscount.setText(Integer.toString(booking.getDiscount()) + " %");
    txtPrice.setText(Integer.toString(booking.getPrice()) + ".000 đ");
    txtIsPaid.setText(booking.isPaid() ? "Đã thanh toán" : "Chưa thanh toán");
    txtBookingDate.setText(booking.getBookingDate());

    star01.setImageResource(booking.getStar_01());
    star02.setImageResource(booking.getStar_02());
    star03.setImageResource(booking.getStar_03());
    star04.setImageResource(booking.getStar_04());
    star05.setImageResource(booking.getStar_05());
  }

  private void setElementColor() {
    int green = ContextCompat.getColor(this, R.color.green);
    int orange = ContextCompat.getColor(this, R.color.orange);
    txtIsPaid.setTextColor(booking.isPaid() ? green : orange);
  }

  private void onCloseListener() {
    this.txtClose.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

//  private void onEditListener() {
//    this,imgEditBooking.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//
//      }
//    });
//  }

  private void onDeleteListener() {
    this.imgDeleteBooking.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dialog.setContentView(R.layout.delete_confirmation_popup);

        // Identify elements in popup
        Button btnDelete = dialog.findViewById(R.id.btnDelete);
        Button btnGoBack = dialog.findViewById(R.id.btnGoback);

        // Set listener for buttons
        btnDelete.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            BookingList.bookingList.remove(booking);
            finish();
//            startActivity(intent);
            dialog.dismiss();
          }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            dialog.dismiss();
          }
        });

        // This is used to fill popup with the window
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Start dialog
        dialog.show();
      }
    });
  }

  private void onEvaluateListener() {
    // Set logic for booking to be evalutate
    this.lEvaluation.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(ServiceRequestDetail.this, Evaluation.class);
        intent.putExtra("position", extras.getInt("position"));
        startActivity(intent);
      }
    });
  }
}
