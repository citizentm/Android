package com.example.myapplication.helpers;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.myapplication.models.ProblemsCluster;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class ProblemsClusterRenderer<T extends ClusterItem> extends DefaultClusterRenderer {
    public ProblemsClusterRenderer(Context context, GoogleMap map, ClusterManager clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster cluster) {
        return cluster.getSize() >= 3;
    }

    @Override
    protected void onBeforeClusterItemRendered(@NonNull ClusterItem item, @NonNull MarkerOptions markerOptions) {
        ProblemsCluster problemsCluster = (ProblemsCluster) item;
        BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
        if(problemsCluster.isResolved()){
            markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
        }
        markerOptions.icon(markerDescriptor).snippet(item.getTitle());
    }
}
