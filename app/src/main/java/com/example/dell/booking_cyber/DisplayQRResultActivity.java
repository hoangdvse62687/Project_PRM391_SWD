package com.example.dell.booking_cyber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayQRResultActivity extends AppCompatActivity {

    TextView txtContent;
    ImageView ic_ok;
    TextView txtCode;
    TextView txtTotalPrice;
    Button btnAccept;
    TextView txtNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_qrresult);
        txtContent = findViewById(R.id.content);
        ic_ok = findViewById(R.id.ic_ok);
        txtCode = findViewById(R.id.code);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        btnAccept = findViewById(R.id.btnAccept);
        txtNote = findViewById(R.id.note);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        boolean isSuccess = false;
        String note = "";
        String code = "";
        String totalPrice = "";
        String content = "";

        Intent intent = getIntent();
        isSuccess = intent.getBooleanExtra("ISSUCCESS",false);
        note = intent.getStringExtra("NOTE");
        code = intent.getStringExtra("CODE");
        totalPrice = intent.getStringExtra("TOTALPRICE");
        content = intent.getStringExtra("CONTENT");
        if(isSuccess){
            txtContent.setText(content);
            txtCode.setText(txtCode.getText()+code);
            txtTotalPrice.setText(totalPrice);
            ic_ok.setImageResource(R.drawable.ic_success);
            }else {
            txtNote.setText(note);
        }
    }
}
