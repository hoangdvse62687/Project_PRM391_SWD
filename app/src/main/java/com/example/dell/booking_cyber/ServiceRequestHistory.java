package com.example.dell.booking_cyber;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.dell.booking_cyber.Adapter.ServiceRequestAdapter;
import com.example.dell.booking_cyber.DTO.ServiceRequestDetailDTO;
import com.example.dell.booking_cyber.Model.AccountManager;
import com.example.dell.booking_cyber.Model.ServiceRequestManager;

import java.util.List;

public class ServiceRequestHistory extends AppCompatActivity {
  private ListView listViewCybercore;
  private ImageView imgBack;
  private List<ServiceRequestDetailDTO> serviceRequestList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_service_request_history);

    listViewCybercore = findViewById(R.id.listViewCybercore);
    imgBack = findViewById(R.id.imgBack);

    // Calling manager
    final AccountManager accountManager = new AccountManager(this);
    final ServiceRequestManager serviceRequestManager = new ServiceRequestManager();

    // Get current account
    accountManager.getAccount();

    serviceRequestList = serviceRequestManager.getServiceRequestByCustomerId(
            accountManager.customerDTO.getId()
    );
    ServiceRequestAdapter adapter = new ServiceRequestAdapter(ServiceRequestHistory.this, R.layout.service_request_item, serviceRequestList);
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
        ServiceRequestDetailDTO serviceRequestDetailDTO = serviceRequestList.get(position);
        intent.putExtra("serviceRequestDetailDTO", serviceRequestDetailDTO);
        startActivity(intent);
      }
    });
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