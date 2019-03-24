package com.example.gyk_02;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageMovie extends AppCompatActivity {
    int CAMERA_REQ = 1;
    int VIDEO_REQ = 2;

    @BindView(R.id.takenPic)
    ImageView takenPic;
    @BindView(R.id.videoPlayer)
    VideoView videoPlayer;
    @BindView(R.id.takePhoto)
    Button takePhoto;
    @BindView(R.id.recordVideo)
    Button recordVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_movie);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.takePhoto, R.id.recordVideo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.takePhoto:
                requestMultiplePermissions(CAMERA_REQ);
                break;
            case R.id.recordVideo:
                requestMultiplePermissions(VIDEO_REQ);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(requestCode == CAMERA_REQ) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                takenPic.setImageBitmap(imageBitmap);
            }else if(requestCode == VIDEO_REQ) {
                videoPlayer.setVideoURI(data.getData());
                videoPlayer.setMediaController(new MediaController(this));
                videoPlayer.requestFocus();
                videoPlayer.start();
            }
        }
    }

    public void requestMultiplePermissions(final int check) {
        Dexter.withActivity(this).withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if(report.areAllPermissionsGranted()) {
                    if(check == CAMERA_REQ) {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQ);
                    }else if(check == VIDEO_REQ) {
                        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        startActivityForResult(videoIntent, VIDEO_REQ);
                    }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ImageMovie.this);
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
