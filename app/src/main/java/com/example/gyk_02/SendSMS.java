package com.example.gyk_02;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karumi.dexter.Dexter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendSMS extends AppCompatActivity {

    @BindView(R.id.enterMessage)
    EditText enterMessage;
    @BindView(R.id.enterNumber)
    EditText enterNumber;
    @BindView(R.id.sendButton)
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sendButton)
    public void onViewClicked() {
        String message = enterMessage.getText().toString();
        String phoneNumber = enterNumber.getText().toString();

        if(message.equals("") || phoneNumber.length() < 10 || phoneNumber.length() > 11) {
            Toast.makeText(SendSMS.this, "Invalid text or number", Toast.LENGTH_SHORT).show();
        }else{
            composeMMSMessage(message, phoneNumber);
        }
    }

    private void composeMMSMessage(String message, String phoneNumber) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);

        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
        smsIntent.putExtra("sms_body", message);
        startActivity(smsIntent);
    }

}
