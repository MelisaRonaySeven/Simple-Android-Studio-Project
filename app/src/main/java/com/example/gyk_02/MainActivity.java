package com.example.gyk_02;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.imageMovie)
    Button imageMovie;
    @BindView(R.id.voiceRecord)
    Button voiceRecord;
    @BindView(R.id.goMap)
    Button goMap;
    @BindView(R.id.goWeb)
    Button goWeb;
    @BindView(R.id.sendSms)
    Button sendSms;
    @BindView(R.id.makeCall)
    Button makeCall;

    Intent action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.imageMovie, R.id.voiceRecord, R.id.goMap, R.id.goWeb, R.id.sendSms, R.id.makeCall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageMovie:
                action = new Intent(MainActivity.this, ImageMovie.class);
                startActivity(action);
                break;
            case R.id.voiceRecord:
                action = new Intent(MainActivity.this, VoiceRecord.class);
                startActivity(action);
                break;
            case R.id.goMap:
                action = new Intent(MainActivity.this, GoMap.class);
                startActivity(action);
                break;
            case R.id.goWeb:
                action = new Intent(MainActivity.this, GoWeb.class);
                startActivity(action);
                break;
            case R.id.sendSms:
                action = new Intent(MainActivity.this, SendSMS.class);
                startActivity(action);
                break;
            case R.id.makeCall:
                action = new Intent(MainActivity.this, MakeCall.class);
                startActivity(action);
                break;
        }
    }

}
