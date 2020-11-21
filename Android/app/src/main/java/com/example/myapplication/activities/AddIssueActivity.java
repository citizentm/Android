package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapters.UploadPicturesAdapter;
import com.example.myapplication.appConstants.AppConstants;
import com.example.myapplication.models.ProblemModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class AddIssueActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int RESULT_LOAD_IMAGE = 1543;
    private RecyclerView photosListRv;
    private UploadPicturesAdapter mPictureAdapter;
    private ArrayList<Uri> mImagesURIs;
    private ExtendedFloatingActionButton addPhotosFab;
    private ExtendedFloatingActionButton sendIssueFab;
    private TextInputEditText descriptionEt;
    private ProblemModel problemModel;
    private int uploadCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_issue);
        initializeViews();
        SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.f_google_map_add_issue);
        supportMapFragment.getMapAsync(this);
        mImagesURIs = new ArrayList<>();
        problemModel = new ProblemModel();
        problemModel.setPhotosUrlList(new ArrayList<String>());
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
        boolean hasNoPhotosInList = mImagesURIs == null || mImagesURIs.size() == 0;
        if (descriptionEt.getText().toString().isEmpty() && hasNoPhotosInList){
            Toast.makeText(this, "Please fill description or add photos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mImagesURIs == null || mImagesURIs.size() == 0){
            return;
        }

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        for (Uri photoUrl: mImagesURIs) {
            final StorageReference fileReference = AppConstants.FIREBASE_PROBLEMS_PHOTOS.child(System.currentTimeMillis()+"."+ getFileExtension(photoUrl));
            UploadTask uploadTask = fileReference.putFile(photoUrl);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();
                        problemModel.getPhotosUrlList().add(mUri);
                        hideProgressDialog(pd);
                    }
                    else
                    {
                        Toast.makeText(AddIssueActivity.this, "Failed upload", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
    }

    private void hideProgressDialog(ProgressDialog progressDialog){
        uploadCounter++;
        if (uploadCounter >= mImagesURIs.size()){
            progressDialog.dismiss();
            Toast.makeText(this, "Issue reported! Timisoara is thankful!", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver mContentResolver = this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(mContentResolver.getType(uri));
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
        photosListRv.setAdapter(mPictureAdapter);
    }

    private void initializeViews() {
        photosListRv = findViewById(R.id.rv_issues_photo_gallery);
        addPhotosFab = findViewById(R.id.fab_issue_add_photos);
        sendIssueFab = findViewById(R.id.fab_issue_add_send);
        descriptionEt = findViewById(R.id.et_issues_description);
        sendIssueFab.setVisibility(View.GONE);
    }
}