package com.example.myapplication.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.adapters.UploadPicturesAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class AddIssueActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int RESULT_LOAD_IMAGE = 1543;
    private RecyclerView photosListRv;
    private UploadPicturesAdapter mPictureAdapter;
    private ArrayList<Uri> mImagesURIs;
    private ExtendedFloatingActionButton addPhotosFab;
    private ExtendedFloatingActionButton sendIssueFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_issue);
        initializeViews();
        SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.f_google_map_add_issue);
        supportMapFragment.getMapAsync(this);
        mImagesURIs = new ArrayList<>();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.7518239,21.2221786), 13));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                googleMap.clear();
                MarkerOptions marker = new MarkerOptions().position(latLng).title("Problem here");
                googleMap.addMarker(marker);
                sendIssueFab.setVisibility(View.VISIBLE);
            }
        });
    }

    public void sendIssueToServer(View view) {
    }

    public void uploadPhotos(View view) {
        Intent mIntent = new Intent();
        mIntent.setType("image/*");
        mIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        mIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(mIntent,"Select photos"), RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getClipData() != null)
        {
            mImagesURIs.clear();
            ClipData clipData = data.getClipData();
            for(int i = 0; i < clipData.getItemCount(); i++){
                Uri mUri =  clipData.getItemAt(i).getUri();
                mImagesURIs.add(mUri);
            }
            setRecyclerView();
        }
    }

    private void setRecyclerView()
    {
        if (mImagesURIs != null){
            mPictureAdapter = new UploadPicturesAdapter(mImagesURIs);
        }

//        addPhotosFab.setVisibility(View.GONE);
        photosListRv.setVisibility(View.VISIBLE);
        photosListRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        photosListRv.setHasFixedSize(true);
        photosListRv.setAdapter(mPictureAdapter);
    }

    private void initializeViews() {
        photosListRv = findViewById(R.id.rv_issues_photo_gallery);
        addPhotosFab = findViewById(R.id.fab_issue_add_photos);
        sendIssueFab = findViewById(R.id.fab_issue_add_send);

        sendIssueFab.setVisibility(View.GONE);
    }
}