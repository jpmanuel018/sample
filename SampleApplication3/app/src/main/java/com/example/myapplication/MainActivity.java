package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;
    String url = "https://run.mocky.io/v3/e314c56c-15f6-4a8c-b866-9b832d803f83";
    ProgressDialog progressDialog;
    boolean isEmailValid = false, isNumberValid = false, isAgeValid = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        binding.edtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1 + 1;
                        String date = i + "-" + i1 + "-" + i2;
                        String age;
                        age = getAge(i,i1,i2);
                        binding.edtAge.setText(age);
                        binding.edtBirthday.setText(date);
                        if(Integer.parseInt(age) < 18){
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Age Restriction");
                            builder.setMessage("Your age is below 18");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int arg1) {
                                    dialog.dismiss();
                                }
                            });
                            builder.setNegativeButton("", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int arg1) {

                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                            nbutton.setTextColor(Color.parseColor("#007ac1"));
                            Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                            pbutton.setTextColor(Color.parseColor("#007ac1"));
                        }
                    }
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
                Button nbutton = datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setTextColor(Color.parseColor("#007ac1"));
                Button pbutton = datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                pbutton.setTextColor(Color.parseColor("#007ac1"));
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    if (!Patterns.EMAIL_ADDRESS.matcher(binding.edtEmail.getText().toString()).matches()) {
                        binding.edtEmail.setError("Invalid Email");
                        isEmailValid = false;
                    }else{
                        isEmailValid = true;
                    }
                    if(!isPhoneNumberValid(binding.edtContactNo.getText().toString(),"PH")) {
                        binding.edtContactNo.setError("Invalid Mobile Number");
                        isNumberValid = false;
                    }else{
                        isNumberValid = true;
                    }
                    if(Integer.parseInt(binding.edtAge.getText().toString()) < 18){
                        isAgeValid = false;
                    }else{
                        isAgeValid = true;
                    }
                    if(isEmailValid && isNumberValid && isAgeValid){
                        progressDialog.show();
                        makeApiCall();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Please input all fields!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void makeApiCall(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("Message");
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage(message);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int arg1) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                            pbutton.setTextColor(Color.parseColor("#007ac1"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public boolean isPhoneNumberValid(String phoneNumber, String countryCode)
    {
        //NOTE: This should probably be a member variable.
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try
        {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber, countryCode);
            return phoneUtil.isValidNumberForRegion(numberProto,countryCode);
        }
        catch (NumberParseException e)
        {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

        return false;
    }

    private boolean isValid(){
        String fullName = binding.edtFullName.getText().toString();
        String email = binding.edtEmail.getText().toString();
        String mobileNumber = binding.edtContactNo.getText().toString();
        String birthday = binding.edtBirthday.getText().toString();
        String age = binding.edtAge.getText().toString();
        if(TextUtils.isEmpty(fullName) ||
            TextUtils.isEmpty(email) ||
            TextUtils.isEmpty(mobileNumber) ||
            TextUtils.isEmpty(birthday) ||
            TextUtils.isEmpty(age)){
            return false;
        }else{
            return true;
        }
    }

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

}