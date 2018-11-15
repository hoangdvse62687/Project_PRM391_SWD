package com.example.dell.booking_cyber;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dell.booking_cyber.Adapter.NavigationAdapter;
import com.example.dell.booking_cyber.Adapter.ViewPagerAdapter;
import com.example.dell.booking_cyber.Constant.LocaleData;
import com.example.dell.booking_cyber.DTO.CyberGamingDTO;
import com.example.dell.booking_cyber.Fragment.CyberCommentFragment;
import com.example.dell.booking_cyber.Fragment.DetailCyberFragment;
import com.example.dell.booking_cyber.Model.CybercoreManager;

import java.io.IOException;

public class CyberDetailActivity extends NavigationAdapter {
    private final int CREATE_SERVICE_REQUEST_CODE = 1;

    CyberGamingDTO detail;
    CybercoreManager cybercoreManager = new CybercoreManager();
    Integer IdCyber;

    //view
    RatingBar ratingbarCyber;
    TextView txtAddress;
    Button btnBooking;
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView imgPoster;
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
        imgPoster = findViewById(R.id.imgPoster);
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBooking.setClickable(false);
                try{
                    if(accountManager.isLogin()){
                        //add your booking action here
                        Intent intent = new Intent(CyberDetailActivity.this, ServiceRequestNew.class);
                        intent.putExtra("cyberGamingDTO", detail);
                        startActivityForResult(intent, CREATE_SERVICE_REQUEST_CODE);
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

    private void HandleData() throws IOException{
        if(getData()){
            renderView(detail);
        }
    }

    private void renderView(CyberGamingDTO data) throws IOException{
        getSupportActionBar().setTitle(data.getName());

        Double rating = Double.parseDouble("5");//default of rating in beginning
        if(data.getNumberOfEvaluator() != null && data.getNumberOfEvaluator() != 0){
            rating = (data.getNumberOfStar()/data.getNumberOfEvaluator());
        }
        ratingbarCyber.setRating(Float.parseFloat(rating.toString()));
        txtAddress.setText(data.getAddress());
        imgPoster.setImageBitmap(LocaleData.loadBitmap(data.getLogo()));
    }
    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DetailCyberFragment.newInstance(IdCyber), "Thông tin");
        adapter.addFragment(CyberCommentFragment.newInstance(IdCyber), "Đánh giá");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CREATE_SERVICE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                  finish();
                }
                break;
            default:
                break;
        }
    }
}
