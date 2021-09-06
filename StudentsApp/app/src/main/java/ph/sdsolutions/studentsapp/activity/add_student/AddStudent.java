package ph.sdsolutions.studentsapp.activity.add_student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.c.progress_dialog.BlackProgressDialog;

import ph.sdsolutions.studentsapp.MainActivity;
import ph.sdsolutions.studentsapp.R;
import ph.sdsolutions.studentsapp.activity.add_student.presenter.AddStudentPresenter;
import ph.sdsolutions.studentsapp.activity.add_student.view.AddStudentContract;
import ph.sdsolutions.studentsapp.activity.login.view.LoginContract;
import ph.sdsolutions.studentsapp.databinding.ActivityAddStudentBinding;
import ph.sdsolutions.studentsapp.databinding.ActivityLoginBinding;
import ph.sdsolutions.studentsapp.dialog.CustomProgressDialog;
import ph.sdsolutions.studentsapp.utils.SharedValues;

public class AddStudent extends AppCompatActivity implements View.OnClickListener, AddStudentContract.View {

    ActivityAddStudentBinding binding;
    AddStudentContract.Presenter presenter;
    CustomProgressDialog progressDialog;
    String accessToken;
    Intent intent;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        accessToken = SharedValues.getSharedValuesString(this,"accessToken");
        Log.d("accessToken",accessToken);

        presenter = new AddStudentPresenter(this);
        binding.btnSave.setOnClickListener(this);

        intent = getIntent();
        if(intent.getExtras()!=null){
            binding.toolbar.setTitle("Edit Student");
            id = intent.getExtras().getInt("id");
            binding.edtFirstName.setText(intent.getExtras().getString("first_name"));
            binding.edtLastName.setText(intent.getExtras().getString("last_name"));
            binding.edtMiddleName.setText(intent.getExtras().getString("middle_name"));
            binding.edtGender.setText(intent.getExtras().getString("gender"));
            binding.edtAge.setText(String.valueOf(intent.getExtras().getInt("age")));
            binding.edtAddress.setText(intent.getExtras().getString("address"));
            binding.edtCourse.setText(intent.getExtras().getString("course"));
        }

        progressDialog = new CustomProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                String firstName = binding.edtFirstName.getText().toString();
                String lastName = binding.edtLastName.getText().toString();
                String middleName = binding.edtMiddleName.getText().toString();
                String gender = binding.edtGender.getText().toString();
                String age = binding.edtAge.getText().toString();
                String address = binding.edtAddress.getText().toString();
                String course = binding.edtCourse.getText().toString();
                if(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(middleName) || TextUtils.isEmpty(gender) || TextUtils.isEmpty(age) || TextUtils.isEmpty(address) || TextUtils.isEmpty(course)){
                    Toast.makeText(getApplicationContext(),"Please input all fields",Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.show();
                    if(intent.getExtras()!=null){
                        presenter.onUpdateStudent(accessToken,id,firstName,lastName,middleName,gender,Integer.parseInt(age),address,course);
                    }else{
                        presenter.onAddStudent(accessToken,firstName,lastName,middleName,gender,Integer.parseInt(age),address,course);
                    }
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess() {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
        binding.edtFirstName.setText("");
        binding.edtLastName.setText("");
        binding.edtMiddleName.setText("");
        binding.edtGender.setText("");
        binding.edtAge.setText("");
        binding.edtAddress.setText("");
        binding.edtCourse.setText("");
    }

    @Override
    public void onSuccessUpdate() {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Update Successful",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(AddStudent.this, MainActivity.class));
    }

    @Override
    public void onError(int code, String message) {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Error Code: " + code + " " +message,Toast.LENGTH_SHORT).show();
    }
}