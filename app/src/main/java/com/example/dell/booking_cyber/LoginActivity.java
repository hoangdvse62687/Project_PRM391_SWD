package com.example.dell.booking_cyber;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.booking_cyber.Adapter.NavigationAdapter;

public class LoginActivity extends NavigationAdapter {

    EditText txtEmail;
    EditText txtPassword;
    Button btnLogin;
    TextView link_SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.profileNavigationOn = false;
        super.layout = R.layout.activity_login;
        super.currentClass = LoginActivity.class;
        super.BottomNavigationOn = false;
        super.onCreate(savedInstanceState);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        link_SignUp = (TextView)findViewById(R.id.txtSignUp);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        link_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
        if(super.accountManager.isLogin()){
            finish();
        }

    }

    public void InitData(){

    }

    public void login(){
        if (!validate()) {
            onLoginFailed();
            return;
        }

        btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đăng nhập...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        final String email = txtEmail.getText().toString();
        final String password = txtPassword.getText().toString();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        try{
                            if(accountManager.checkLogin(email,password)){
                                onLoginSuccess();
                            }else{
                                onLoginFailed();
                            }
                        }catch (Exception ex){
                            Intent intent = new Intent(LoginActivity.this,ReloadActivity.class);
                            startActivity(intent);
                            finish();
                            ex.printStackTrace();
                        }finally {
                            progressDialog.dismiss();
                        }
                    }
                },1000);
    }

    public boolean validate() {
        boolean valid = true;

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("email chưa đúng");
            valid = false;
        } else {
            txtEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            txtPassword.setError("password phải từ 4-10 từ");
            valid = false;
        } else {
            txtPassword.setError(null);
        }

        return valid;
    }

    public void onLoginSuccess() {
        btnLogin.setEnabled(true);
        //Reload view
        try{
            setResult(RESULT_OK);
        }finally {
            finish();
        }
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Đăng nhập thất bại", Toast.LENGTH_LONG).show();

        btnLogin.setEnabled(true);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
            if(!accountManager.isLogin()){
                startActivity(getIntent());
            }
        }
    }
}
