package com.example.dell.booking_cyber;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dell.booking_cyber.Adapter.NavigationAdapter;
import com.example.dell.booking_cyber.Adapter.ViewPagerAdapter;
import com.example.dell.booking_cyber.DTO.CyberGamingDTO;
import com.example.dell.booking_cyber.Fragment.CyberCommentFragment;
import com.example.dell.booking_cyber.Fragment.DetailCyberFragment;
import com.example.dell.booking_cyber.Model.CybercoreManager;

import java.util.ArrayList;
import java.util.HashMap;

public class CyberDetailActivity extends NavigationAdapter {
    CyberGamingDTO detail;
    CybercoreManager cybercoreManager = new CybercoreManager();
    Integer IdCyber;

    //view
    RatingBar ratingbarCyber;
    TextView txtAddress;
    Button btnBooking;
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.profileNavigationOn = false;
        super.BottomNavigationOn = false;
        super.layout = R.layout.activity_cyber_detail;
        super.currentClass = CyberDetailActivity.class;
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        IdCyber = intent.getIntExtra("ID",0);
        ratingbarCyber = findViewById(R.id.ratingbarCyber);
        txtAddress = findViewById(R.id.txtAddress);
        btnBooking = findViewById(R.id.btnBookingSeat);
        viewPager  = findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBooking.setClickable(false);
                try{
                    if(accountManager.isLogin()){
                        //add your booking action here
                    }else {
                        Intent intent1 = new Intent(CyberDetailActivity.this,LoginActivity.class);
                        startActivity(intent1);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }finally {
                    btnBooking.setClickable(true);
                }
            }
        });
        final ProgressDialog progressDialog = new ProgressDialog(CyberDetailActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang tải...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        try{
            HandleData();
            setupViewPager();
            tabLayout.setupWithViewPager(viewPager);
        }catch (Exception ex){
            Intent intent1 = new Intent(CyberDetailActivity.this,ReloadActivity.class);
            startActivity(intent1);
            finish();
            ex.printStackTrace();
        }finally {
            progressDialog.dismiss();
        }

    }

    private boolean getData(){
        detail = cybercoreManager.getCyberById(IdCyber);
        if(detail != null){
            return true;
        }
        return false;
    }

    private void HandleData(){
        if(getData()){
            renderView(detail);
        }
    }

    private void renderView(CyberGamingDTO data){
        getSupportActionBar().setTitle(data.getName());
        ratingbarCyber.setRating(Float.parseFloat(data.getStarAverage().toString()));
        txtAddress.setText(data.getAddress());
    }
    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DetailCyberFragment.newInstance(IdCyber), "Thông tin");
        adapter.addFragment(CyberCommentFragment.newInstance(IdCyber), "Đánh giá");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
