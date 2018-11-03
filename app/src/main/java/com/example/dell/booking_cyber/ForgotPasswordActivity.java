package com.example.dell.booking_cyber;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.booking_cyber.Adapter.NavigationAdapter;
import com.example.dell.booking_cyber.DTO.AccountDTO;
import com.example.dell.booking_cyber.Model.AccountManager;
import com.example.dell.booking_cyber.Model.GMailSender;

import java.util.List;

public class ForgotPasswordActivity extends NavigationAdapter {

    EditText txtEmail;
    Button btnGetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.profileNavigationOn = false;
        super.layout = R.layout.activity_forgot_password;
        super.currentClass = ForgotPasswordActivity.class;
        super.BottomNavigationOn = false;
        super.onCreate(savedInstanceState);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        btnGetPassword = findViewById(R.id.btnGetPassword);
        btnGetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPassword();
            }
        });
    }

    public void getPassword(){
        final ProgressDialog progressDialog = new ProgressDialog(ForgotPasswordActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đăng nhập...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        try{
                            if (txtEmail.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()) {
                                txtEmail.setError("email chưa đúng định dạng");
                            } else {
                                txtEmail.setError(null);
                            }
                            AccountManager accountManager = new AccountManager(ForgotPasswordActivity.this);
                            List<AccountDTO> accountDTOS = accountManager.getAllAccount();
                            for (AccountDTO item:
                                 accountDTOS) {
                                if(item.getUsername().equalsIgnoreCase(txtEmail.getText().toString())){
                                    GMailSender gMailSender = new GMailSender(txtEmail.getText().toString(),"Lấy lại mật khẩu",
                                            "<h1>Booking cyber application</h1><br>" +
                                                    "<h3>Chào "+item.getUsername()+",</h3><br>" +
                                                    "<a>Mật khẩu của bạn là:<b>"+item.getPassword()+"</b></a><br>" +
                                                    "<a>Hãy đổi mật khẩu ngay để bảo mật tài khoản của bản</a><br>" +
                                                    "<a>Cảm ơn</a>");
                                    gMailSender.sendMail();
                                    Toast.makeText(ForgotPasswordActivity.this,"Kiểm tra email để nhận lại mật khẩu",Toast.LENGTH_LONG).show();
                                    txtEmail.setError(null);
                                    return;
                                }
                            }
                            Toast.makeText(ForgotPasswordActivity.this,"Email không tồn tại",Toast.LENGTH_LONG).show();
                            txtEmail.setError("Email không tồn tại");
                        }catch (Exception ex){
                            Intent intent = new Intent(ForgotPasswordActivity.this,ReloadActivity.class);
                            startActivity(intent);
                            ex.printStackTrace();
                        }finally {
                            progressDialog.dismiss();
                        }
                    }
                },1000);
    }
}
