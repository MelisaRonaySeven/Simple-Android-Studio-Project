package com.example.gyk_02;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoMap extends AppCompatActivity {

    @BindView(R.id.location)
    Button location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_map);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.location)
    public void onViewClicked() {
        Uri geoLocation = Uri.parse("geo:38.393784, 27.073730");
        showMap(geoLocation);
    }

    private void showMap(Uri geoLocation) {
        Intent googleMap = new Intent(Intent.ACTION_VIEW);
        googleMap.setData(geoLocation);

        if(googleMap.resolveActivity(getPackageManager()) != null) {
            startActivity(googleMap);
        }
    }
}
