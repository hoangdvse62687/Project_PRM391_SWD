package com.example.dell.booking_cyber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dell.booking_cyber.Adapter.ServiceRequestAdapter;
import com.example.dell.booking_cyber.DTO.Booking;
import com.example.dell.booking_cyber.List.BookingList;

import java.util.ArrayList;

public class ServiceRequestHistory extends AppCompatActivity {
  private ListView listViewCybercore;
  private TextView txtClose;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_service_request_history);

    listViewCybercore = findViewById(R.id.listViewCybercore);
    txtClose = findViewById(R.id.txtClose);

    BookingList.addToList();

    ArrayList<Booking> bookingList = BookingList.bookingList;

    final ServiceRequestAdapter adapter = new ServiceRequestAdapter(this, R.layout.service_request_item, bookingList);
    listViewCybercore.setAdapter(adapter);

    onBookingHistoryItemClick();
    onCloseListener();
  }

  // Set onclick listener for booking history item
  private void onBookingHistoryItemClick() {
    this.listViewCybercore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        Intent intent = new Intent(ServiceRequestHistory.this, ServiceRequestDetail.class);
        intent.putExtra("position", position);
        startActivity(intent);
      }
    });
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