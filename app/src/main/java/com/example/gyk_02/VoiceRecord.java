package com.example.gyk_02;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VoiceRecord extends AppCompatActivity {

    @BindView(R.id.micPic)
    ImageView micPic;
    @BindView(R.id.recordAudio)
    Button recordAudio;
    @BindView(R.id.stopRecord)
    Button stopRecord;
    @BindView(R.id.listenAudio)
    Button listenAudio;

    private MediaRecorder recorder;
    private MediaPlayer file;
    private final String filePath = Environment.getExternalStorageDirectory().getPath() + "/record.3gp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_record);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.recordAudio, R.id.stopRecord, R.id.listenAudio})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recordAudio:
                requestMultiplePermissions();
                break;
            case R.id.stopRecord:
                stopRecording();
                break;
            case R.id.listenAudio:
                startPlaying();
                break;
        }
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(filePath);

        try {
            recorder.prepare();
            recorder.start();
            Toast.makeText(this, "Started to record.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("Audio Record Test", "prepare() failed");
        }
    }

    private void stopRecording() {
        if(recorder != null) {
            Toast.makeText(this, "Stopped Recording.", Toast.LENGTH_SHORT).show();
            recorder.stop();
            recorder.reset();
            recorder.release();
            recorder = null;
        }
    }

    private void startPlaying() {
        file = new MediaPlayer();

        try {
            file.setDataSource(filePath);
            file.prepare();
            file.start();
            Toast.makeText(this, "Started to play.", Toast.LENGTH_SHORT).show();
            file.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    file.stop();
                    file.release();
                    file = null;
                }
            });
        } catch (IOException e) {
            Log.e("Audio Play Test", "prepare() failed");
        }
    }

    public void requestMultiplePermissions() {
        Dexter.withActivity(this).withPermissions(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if(report.areAllPermissionsGranted()) {
                    startRecording();
                }

                if(report.isAnyPermissionPermanentlyDenied()) {
                    showSettingsDialog();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(VoiceRecord.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("You need to grant permissions to use the app.");
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });

        builder.setNegativeButton("Maybe Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void openSettings() {
        Intent toSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);

        toSettings.setData(uri);
        startActivity(toSettings);

    }
}
