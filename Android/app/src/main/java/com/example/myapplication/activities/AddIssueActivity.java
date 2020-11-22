package com.example.myapplication.activities;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.UploadPicturesAdapter;
import com.example.myapplication.appConstants.AppConstants;
import com.example.myapplication.helpers.HttpClientManager;
import com.example.myapplication.models.ImageModel;
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
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.example.myapplication.appConstants.AppConstants.INTENT_RESULT_NUMBER;
import static com.example.myapplication.appConstants.AppConstants.ON_ADD_ISSUE;

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
        problemModel.setPhotosList(new ArrayList<ImageModel>());
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.7518239,21.2221786), 13));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                googleMap.clear();
                MarkerOptions marker = new MarkerOptions().position(latLng);
                googleMap.addMarker(marker);
                problemModel.setPosition(latLng);
                problemModel.setLatitude(latLng.latitude);
                problemModel.setLongitude(latLng.longitude);
                sendIssueFab.setVisibility(View.VISIBLE);
            }
        });
    }

    public void sendIssueToServer(View view) {
        boolean hasNoPhotosInList = mImagesURIs == null || mImagesURIs.size() == 0;
        if (descriptionEt.getText().toString().isEmpty() || hasNoPhotosInList){
            Toast.makeText(this, "Please fill description and add photos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mImagesURIs == null || mImagesURIs.size() == 0){
            return;
        }

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Reporting issue");
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
                        problemModel.getPhotosList().add(new ImageModel(mUri));
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

    private void hideProgressDialog(final ProgressDialog progressDialog){
        uploadCounter++;
        if (uploadCounter >= mImagesURIs.size()){
            problemModel.setDescription(descriptionEt.getText().toString());
            HttpClientManager.getInstance().postProblem(problemModel, new HttpClientManager.OnDataReceived<ProblemModel>() {
                @Override
                public void dataReceived(ProblemModel data) {
                    if (data == null){
                        progressDialog.dismiss();
                        return;
                    }
                    problemModel.setId(data.getId());
                    problemModel.setPostedDate(data.getPostedDate().substring(0, 10));
                    onAddIssue(problemModel);
                    progressDialog.dismiss();
                }

                @Override
                public void onFailed() {
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void onAddIssue(ProblemModel problem){
        Intent intent = new Intent();
        Gson gson = new Gson();
        intent.putExtra(ON_ADD_ISSUE, gson.toJson(problem));
        setResult(INTENT_RESULT_NUMBER, intent);
        finish();
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