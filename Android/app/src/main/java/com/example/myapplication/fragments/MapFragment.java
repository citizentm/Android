package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activities.AddIssueActivity;
import com.example.myapplication.adapters.IssuePicturesAdapter;
import com.example.myapplication.adapters.UploadPicturesAdapter;
import com.example.myapplication.appConstants.AppConstants;
import com.example.myapplication.helpers.Mockers;
import com.example.myapplication.helpers.ProblemsClusterRenderer;
import com.example.myapplication.helpers.StorageHelper;
import com.example.myapplication.models.ProblemModel;
import com.example.myapplication.models.ProblemsCluster;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static com.example.myapplication.appConstants.AppConstants.INTENT_RESULT_NUMBER;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private final int ONGOING_PROBLEMS = 0;
    private final int FIXED_PROBLEMS = 1;
    private ClusterManager<ProblemsCluster> clusterManager;
    private ProblemsClusterRenderer clusterRenderer;
    private TextView ongoingProblemsTv;
    private TextView fixedProblemsTv;
    private TextView issueDescriptionTv;
    private TextView issueDateTv;
    private LinearLayout bottomContainerLl;
    private FloatingActionButton addIssueFab;
    private RecyclerView issuePhotoListRv;
    private ImageView closeDetailsIv;
    private GoogleMap googleMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        initializeViews(root);
        setOnClickListeners();
        SupportMapFragment supportMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.f_google_map);
        supportMapFragment.getMapAsync(this);
        onProblemsTabChanged(ONGOING_PROBLEMS);
        bottomContainerLl.setVisibility(GONE);
        return root;
    }

    private void setOnClickListeners() {
        ongoingProblemsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProblemsTabChanged(ONGOING_PROBLEMS);
            }
        });

        fixedProblemsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProblemsTabChanged(FIXED_PROBLEMS);
            }
        });

        addIssueFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), AddIssueActivity.class), INTENT_RESULT_NUMBER);
            }
        });

        closeDetailsIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideDown(bottomContainerLl);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == INTENT_RESULT_NUMBER){
            if (data == null){
                return;
            }
            Toast.makeText(getContext(), "Our community is grateful for your report", Toast.LENGTH_SHORT).show();
            String result = data.getStringExtra(AppConstants.ON_ADD_ISSUE);
            Gson gson = new Gson();
            ProblemModel problemModel = gson.fromJson(result, ProblemModel.class);
            ProblemsCluster clusterItem = new ProblemsCluster(problemModel.getId(), problemModel.getPosition(),  problemModel.getPostedDate(), problemModel.getDescription(), false, problemModel.getPhotosList());
            clusterManager.addItem(clusterItem);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(clusterItem.getPosition().latitude,clusterItem.getPosition().longitude), 15));
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.7518239,21.2221786), 13));
        clusterManager = new ClusterManager<>(getContext(), googleMap);
        googleMap.setOnCameraIdleListener(clusterManager);
        googleMap.setOnMarkerClickListener(clusterManager);
        clusterManager.setAnimation(true);
        clusterRenderer = new ProblemsClusterRenderer(getContext(), googleMap, clusterManager);
        clusterManager.setRenderer(clusterRenderer);
        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<ProblemsCluster>() {
            @Override
            public boolean onClusterClick(Cluster<ProblemsCluster> cluster) {
                googleMap.getUiSettings().setZoomControlsEnabled(false);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cluster.getPosition(), 13));
                return false;
            }
        });

        clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<ProblemsCluster>() {
            @Override
            public boolean onClusterItemClick(ProblemsCluster item) {
                setMarkerData(item);
                slideUp(bottomContainerLl);
                return false;
            }
        });
        addItems();
    }

    private void setMarkerData(ProblemsCluster item) {
        setRecyclerView(new ArrayList<>(item.getPhotosUrl()));
        issueDescriptionTv.setText(item.getDescription());
        issueDateTv.setText(item.getPostedDate().substring(0,10));
    }

    private void setRecyclerView(ArrayList<String> photosUrl){
        if (photosUrl == null){
            return;
        }
        IssuePicturesAdapter mPictureAdapter = new IssuePicturesAdapter(photosUrl);
        issuePhotoListRv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        issuePhotoListRv.setAdapter(mPictureAdapter);
    }

    private void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    private void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    private void addItems() {
        List<ProblemModel> list =  StorageHelper.getInstance().getProblemsList();
        if (list == null || list.size() == 0){
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            ProblemModel problemModel = list.get(i);
            LatLng position = new LatLng(problemModel.getLatitude(), problemModel.getLongitude());
            ProblemsCluster clusterItem = new ProblemsCluster(problemModel.getId(), position,  problemModel.getPostedDate(), problemModel.getDescription(), problemModel.isResolved(), problemModel.getPhotosList());
            clusterManager.addItem(clusterItem);
        }
    }

    private void initializeViews(View root) {
        fixedProblemsTv = root.findViewById(R.id.tv_map_fixed);
        ongoingProblemsTv = root.findViewById(R.id.tv_map_ongoing);
        addIssueFab = root.findViewById(R.id.fab_add_issue);
        issuePhotoListRv = root.findViewById(R.id.rv_map_issue_photos);
        issueDescriptionTv = root.findViewById(R.id.tv_map_issue_description);
        issueDateTv = root.findViewById(R.id.tv_map_issue_date);
        bottomContainerLl = root.findViewById(R.id.ll_map_issue_selected);
        closeDetailsIv = root.findViewById(R.id.iv_map_issue_close);
    }

    private void onProblemsTabChanged(int selectedTab){
        switch (selectedTab){
            case 0:
                fixedProblemsTv.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                ongoingProblemsTv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 1:
                fixedProblemsTv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ongoingProblemsTv.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
            default:
                break;
        }
    }
}