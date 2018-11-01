package com.example.dell.booking_cyber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ReloadActivity extends AppCompatActivity {

    ImageView imgReloadPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reload);

        imgReloadPage = (ImageView)findViewById(R.id.imgReloadPage);
        imgReloadPage.setImageResource(R.drawable.ic_reload);


    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    public void clickToReload(View view) {
        try{
            setResult(RESULT_OK);

        }finally {
            finish();
        }
    }
}
