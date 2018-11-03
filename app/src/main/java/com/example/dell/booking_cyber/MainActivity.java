package com.example.dell.booking_cyber;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dell.booking_cyber.Adapter.NavigationAdapter;
import com.example.dell.booking_cyber.Adapter.OrderListViewAdapter;
import com.example.dell.booking_cyber.Constant.LocaleData;
import com.example.dell.booking_cyber.DTO.ServiceRequestDTO;
import com.example.dell.booking_cyber.DTO.ServiceRequestDetailDTO;
import com.example.dell.booking_cyber.Fragment.GoogleMapApiFragment;
import com.example.dell.booking_cyber.DTO.AccountDTO;
import com.example.dell.booking_cyber.Model.ServiceRequestManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends NavigationAdapter {
    boolean isHiddenItem = true;
    //Var
    TextView btnHide;
    CardView orderView;
    GoogleMapApiFragment fragment;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.layout = R.layout.activity_main;
        super.currentClass = MainActivity.class;
        super.onCreate(savedInstanceState);

        btnHide = findViewById(R.id.btnHide);
        orderView = findViewById(R.id.orderView);
        listView = findViewById(R.id.BaseListView);
        fragment = new GoogleMapApiFragment();
        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isHiddenItem){
                    Drawable top = getResources().getDrawable(R.drawable.black_arrow_down);
                    btnHide.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                    orderView.setVisibility(View.VISIBLE);
                    isHiddenItem = false;
                }else{
                    Drawable top = getResources().getDrawable(R.drawable.black_arrow_down);
                    btnHide.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                    orderView.setVisibility(View.GONE);
                    isHiddenItem = true;
                }
            }
        });
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang tải...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        // add google Map
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.googleMap, fragment);
        ft.commit();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        try{
                            OnHandleData();
                        }catch (Exception ex){
                            Intent intent = new Intent(MainActivity.this,ReloadActivity.class);
                            startActivity(intent);
                            finish();
                            ex.printStackTrace();
                        }finally {
                            progressDialog.dismiss();
                        }
                    }
                },1000);
    }
    private void OnHandleData(){
        try{
            AccountDTO accountDTO = super.accountManager.getAccount();
            if(accountDTO == null || accountDTO.getUsername() == null){
                //Handle unauthorized
                onHandleDataunauthorized();
            }else if(accountDTO.getRole().equals(LocaleData.ROLE_USER)){
                //Handle data user see
                onHandleDataUser();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            Intent intent = new Intent(this,ReloadActivity.class);
            startActivityForResult(intent,0);
        }
    }
    private void onHandleDataunauthorized(){
        //Load data for unauthorized user
        btnHide.setVisibility(View.GONE);
        orderView.setVisibility(View.GONE);
    }

    private void onHandleDataUser(){
        if(super.accountManager.isLogin()){
            //load data request in process here
            OrderListViewAdapter adapter = new OrderListViewAdapter(this,fragment);
            listView.setAdapter(adapter);
            List<ServiceRequestDetailDTO> listOrders = new ArrayList<>();
            ServiceRequestManager serviceRequestManager = new ServiceRequestManager();
            listOrders = serviceRequestManager.getConfigurationByCyberId(super.accountManager.customerDTO.getId());

            //Handle Data time and Approve here
            Date currentTime = new Date();
            List<ServiceRequestDetailDTO> listOrDerShowInMap = new ArrayList<>();
            if(listOrders != null){
                for (ServiceRequestDetailDTO item:
                        listOrders) {
                    if(item.getApproved() && !item.getDone()){
                        Date timeStart = LocaleData.addHours(item.getGoingDate(),-LocaleData.TIME_ZONE);//get time start
                        Date timeEnd = LocaleData.addHours(item.getGoingDate(),-LocaleData.TIME_ZONE);
                        timeEnd = LocaleData.addHours(timeEnd,Integer.parseInt(item.getDuration().toString().substring(0,1)));

                        if(currentTime.before(timeEnd)){
                            listOrDerShowInMap.add(item);
                        }
                    }
                }
            }
            for (ServiceRequestDetailDTO item:
                 listOrDerShowInMap) {
                adapter.addItem(item);
            }

        }else{
            btnHide.setVisibility(View.GONE);
            orderView.setVisibility(View.GONE);
        }

        if(isHiddenItem){
            Drawable top = getResources().getDrawable(R.drawable.black_arrow_down);
            btnHide.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
            orderView.setVisibility(View.GONE);
            isHiddenItem = true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
            startActivity(getIntent());
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
