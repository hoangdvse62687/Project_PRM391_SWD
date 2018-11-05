package com.example.dell.booking_cyber;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.booking_cyber.Constant.LocaleData;
import com.example.dell.booking_cyber.DTO.CustomerDTO;
import com.example.dell.booking_cyber.DTO.ServiceRequestDTO;
import com.example.dell.booking_cyber.DTO.ServiceRequestDetailDTO;
import com.example.dell.booking_cyber.Model.AccountManager;
import com.example.dell.booking_cyber.Model.ServiceRequestManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class ScanQRActivity extends AppCompatActivity{
    private static final int REQUEST_CAMERA = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        int apiVersion = android.os.Build.VERSION.SDK_INT;
        if (apiVersion >= android.os.Build.VERSION_CODES.M) {
            if (!checkPermission()) {
                requestPermission();
            }else{
                init();
            }
        }
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        init();
                        Toast.makeText(getApplicationContext(), "Quyền truy cập đã được cấp", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Bạn không cung cấp quyền truy cập", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                                showMessage("Bạn cần cung cấp quyền dùng camera.",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessage(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(ScanQRActivity.this)
                .setMessage(message)
                .setPositiveButton("Đồng ý", okListener)
                .setNegativeButton("Hủy bỏ", null)
                .create()
                .show();
    }

    public void init(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator .setPrompt("Đang đọc QR code");
        integrator.setCameraId(0);
        // beep khi scan qr thành công
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultcode, Intent intent){
        boolean isSuccess = false;
        String note = "";
        String code = "";
        String totalPrice = "";
        String content = "";
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultcode, intent);
        if(result != null){
            try{
                String contents = result.getContents();
                // lấy hiệu ứng rung khi scan thành công.
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                // SET RUNG 400 MILLISECONDS
                v.vibrate(400);
                //Xủ lý logic scan mã QR ở đây
                Integer id = Integer.parseInt(contents);
                ServiceRequestManager serviceRequestManager = new ServiceRequestManager();
                ServiceRequestDetailDTO detailDTO = serviceRequestManager.getServiceRequestById(id);
                if(detailDTO == null){
                    return;
                }
                if(detailDTO.getDone()){
                    //chơi xong rồi thì quét chi?
                    note = "Mã này đã dùng";
                    return;
                }
                AccountManager accountManager = new AccountManager(ScanQRActivity.this);
                accountManager.getAccount();
                CustomerDTO customerDTO = accountManager.customerDTO;
                if(customerDTO.getId() == null){
                    return;
                }
                if(customerDTO.getId() != detailDTO.getUserId()){
                    //ko phai ticket cua ban
                    note = "Mã này không phải của bạn";
                    return;
                }
                // sửa thuộc tính done = true
                if(!serviceRequestManager.updateDoneStatus(id)){
                    return;
                }
                // thông báo ở view
                code = detailDTO.getId().toString();
                content = "Quét mã thành công";
                totalPrice = detailDTO.getTotalPrice().toString() + "đ";
                isSuccess = true;
            }catch (Exception ex){
                ex.printStackTrace();
            }finally {
                Intent intent1 = new Intent(ScanQRActivity.this,DisplayQRResultActivity.class);
                intent1.putExtra("ISSUCCESS",isSuccess);
                intent1.putExtra("NOTE",note);
                intent1.putExtra("CODE",code);
                intent1.putExtra("CONTENT",content);
                intent1.putExtra("TOTALPRICE",totalPrice);
                startActivity(intent1);
                finish();
            }
        }
    }
}
