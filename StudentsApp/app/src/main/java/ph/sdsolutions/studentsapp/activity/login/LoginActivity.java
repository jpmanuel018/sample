package ph.sdsolutions.studentsapp.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import ph.sdsolutions.studentsapp.MainActivity;
import ph.sdsolutions.studentsapp.R;
import ph.sdsolutions.studentsapp.activity.login.presenter.LoginPresenter;
import ph.sdsolutions.studentsapp.activity.login.view.LoginContract;
import ph.sdsolutions.studentsapp.databinding.ActivityLoginBinding;
import ph.sdsolutions.studentsapp.dialog.CustomProgressDialog;
import ph.sdsolutions.studentsapp.utils.SharedValues;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginContract.View{

    ActivityLoginBinding binding;
    LoginContract.Presenter presenter;
    CustomProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new LoginPresenter(this);
        binding.btnSignIn.setOnClickListener(this);

        progressDialog = new CustomProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignIn:
                String username = binding.edtUsername.getText().toString();
                String password = binding.edtPassword.getText().toString();
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    binding.edtUsername.setError("Required");
                    binding.edtPassword.setError("Required");
                    Toast.makeText(this, "Please enter username and password!", Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.show();
                    presenter.signIn(username,password);
                }
                break;
        }
    }

    @Override
    public void onSuccessLogin(String token) {
        SharedValues.setValues(this,"accessToken",token);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        progressDialog.dismiss();
        finish();
    }

    @Override
    public void onError(int code, String message) {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyFields() {
        binding.edtUsername.setError("Required");
        binding.edtPassword.setError("Required");
        Toast.makeText(this, "Please enter username and password!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

    }
}