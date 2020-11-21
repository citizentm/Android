package com.example.myapplication.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class IssuePicturesAdapter extends RecyclerView.Adapter<IssuePicturesAdapter.IssuePicturesViewHolder> {
    public List<String> uriList;

    public IssuePicturesAdapter(ArrayList<String> mUriList)
    {
        uriList = mUriList;
    }

    @NonNull
    @Override
    public IssuePicturesAdapter.IssuePicturesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_upload_photos, viewGroup, false);
        return new IssuePicturesAdapter.IssuePicturesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IssuePicturesViewHolder viewHolder, int position) {
        Glide.with(viewHolder.photoUploadedIv.getContext()).load(uriList.get(position)).apply(new RequestOptions().centerCrop()).into(viewHolder.photoUploadedIv);

    }

    @Override
    public int getItemCount() {
        return uriList.size();
    }

    public class IssuePicturesViewHolder extends RecyclerView.ViewHolder{
        public ImageView closeIv;
        public ImageView photoUploadedIv;

        public IssuePicturesViewHolder(@NonNull View itemView) {
            super(itemView);

            closeIv = itemView.findViewById(R.id.iv_row_upload_close);
            photoUploadedIv = itemView.findViewById(R.id.iv_row_upload_image);
            closeIv.setVisibility(GONE);
        }
    }
}