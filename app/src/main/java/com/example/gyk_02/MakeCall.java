package com.example.gyk_02;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MakeCall extends AppCompatActivity {

    @BindView(R.id.phoneNumber)
    EditText phoneNumber;
    @BindView(R.id.callButton)
    Button callButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_call);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.callButton)
    public void onViewClicked() {
        String phoneNum = phoneNumber.getText().toString();
        
        if(phoneNum.equals("") || phoneNum.length() < 10 || phoneNum.length() > 11) {
            Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show();
        }else {
            makeCall(phoneNum);
        }
    }

    private void makeCall(String phoneNum) {
        Uri uri = Uri.parse("tel:" + phoneNum);

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(uri);

        if(callIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(callIntent);
        }
    }
}
