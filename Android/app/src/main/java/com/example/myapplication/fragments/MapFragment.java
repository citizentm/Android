package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.helpers.ProblemsClusterRenderer;
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
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;

import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private ClusterManager<ProblemsCluster> clusterManager;
    private ProblemsClusterRenderer clusterRenderer;
    private List<MarkerOptions> markersList;
    private ProblemsCluster clusterItem;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.f_google_map);

        supportMapFragment.getMapAsync(this);
        setupCluster();
        return root;
    }

    private void setupCluster() {

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.7518239,21.2221786), 13));
        clusterManager = new ClusterManager<>(getContext(), googleMap);
        googleMap.setOnCameraIdleListener(clusterManager);
        googleMap.setOnMarkerClickListener(clusterManager);
        googleMap.setOnMapLongClickListener(this);
        clusterManager.setAnimation(true);
        clusterRenderer = new ProblemsClusterRenderer(getContext(), googleMap, clusterManager);
        clusterManager.setRenderer(clusterRenderer);
        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<ProblemsCluster>() {
            @Override
            public boolean onClusterClick(Cluster<ProblemsCluster> cluster) {
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(cluster.getPosition() )
                        .zoom(12)
                        .build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, null);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cluster.getPosition(), 10));
                return false;
            }
        });

        clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<ProblemsCluster>() {
            @Override
            public boolean onClusterItemClick(ProblemsCluster item) {
                return false;
            }
        });
        addItems();
    }

    private void addItems() {
        for (int i = 1; i < 15; i++) {
            LatLng newPosition = new LatLng(45.7518239 + Math.random(), 21.2221786 - Math.random());
            ProblemsCluster clusterItem = new ProblemsCluster(newPosition, "marker" +i, "snippet" + i);
            clusterManager.addItem(clusterItem);
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (clusterItem != null){
            clusterManager.removeItem(clusterItem);
        }
        clusterItem = new ProblemsCluster(latLng, "My new item", "Very cool item");
        clusterManager.addItem(clusterItem);
    }
}