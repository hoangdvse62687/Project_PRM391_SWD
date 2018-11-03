package com.example.dell.booking_cyber;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.booking_cyber.Adapter.NavigationAdapter;
import com.example.dell.booking_cyber.Constant.LocaleData;
import com.example.dell.booking_cyber.DTO.AccountDTO;
import com.example.dell.booking_cyber.DTO.CustomerDTO;

public class SignupActivity extends NavigationAdapter {

    EditText txtName;
    EditText txtAddress;
    EditText txtEmail;
    EditText txtPhoneNumber;
    EditText txtPassword;
    EditText txtreEnterPassword;
    Button btnSignup;
    TextView linkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.layout = R.layout.activity_signup;
        super.currentClass = SignupActivity.class;
        super.profileNavigationOn = false;
        super.BottomNavigationOn = false;
        super.onCreate(savedInstanceState);

        txtName = (EditText) findViewById(R.id.txtName);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPhoneNumber = (EditText) findViewById(R.id.txtMobile);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtreEnterPassword = (EditText) findViewById(R.id.txtreEnterPassword);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        linkLogin = (TextView) findViewById(R.id.link_login);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        if(super.accountManager.isLogin()){
            finish();
        }
    }

    public void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        btnSignup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đăng ký...");
        progressDialog.show();

        String name = txtName.getText().toString();
        String address = txtAddress.getText().toString();
        String email = txtEmail.getText().toString();
        String mobile = txtPhoneNumber.getText().toString();
        String password = txtPassword.getText().toString();
        String reEnterPassword = txtreEnterPassword.getText().toString();

        final AccountDTO accountDTO = new AccountDTO(email,password,LocaleData.ROLE_USER,true,false);
        final CustomerDTO customerDTO = new CustomerDTO(name,"",email,mobile,Double.parseDouble("1"),true,false);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        try{
                            if(accountManager.signup(accountDTO, customerDTO)){
                                onSignupSuccess();
                            }else {
                                onSignupFailed();
                            }
                        }catch (Exception ex){
                            Intent intent = new Intent(SignupActivity.this,ReloadActivity.class);
                            startActivity(intent);
                            finish();
                            ex.printStackTrace();
                        }finally {
                            progressDialog.dismiss();
                        }
                    }
                }, 1000);
    }


    private void onSignupSuccess() {
        btnSignup.setEnabled(true);
        setResult(RESULT_OK);
        finish();
    }

    private void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Email đã tồn tại", Toast.LENGTH_LONG).show();
        txtEmail.setError("Email này đã tồn tại");
        btnSignup.setEnabled(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    private boolean validate() {
        boolean valid = true;

        String name = txtName.getText().toString();
        String address = txtAddress.getText().toString();
        String email = txtEmail.getText().toString();
        String mobile = txtPhoneNumber.getText().toString();
        String password = txtPassword.getText().toString();
        String reEnterPassword = txtreEnterPassword.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            txtName.setError("at least 3 characters");
            valid = false;
        } else {
            txtName.setError(null);
        }

        if (address.isEmpty()) {
            txtAddress.setError("Enter Valid Address");
            valid = false;
        } else {
            txtAddress.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("enter a valid email address");
            valid = false;
        } else {
            txtEmail.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            txtPhoneNumber.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            txtPhoneNumber.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            txtPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            txtPassword.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            txtreEnterPassword.setError("Password Do not match");
            valid = false;
        } else {
            txtreEnterPassword.setError(null);
        }

        return valid;
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
