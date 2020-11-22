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

public class UploadPicturesAdapter extends RecyclerView.Adapter<UploadPicturesAdapter.ViewHolder> {
    public ArrayList<Uri> uriList;

    public UploadPicturesAdapter(ArrayList<Uri> mUriList)
    {
        uriList = mUriList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_upload_photos, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        Glide.with(viewHolder.photoUploadedIv.getContext()).load(uriList.get(i)).apply(new RequestOptions().centerCrop()).into(viewHolder.photoUploadedIv);
    }

    private void removeItemAtPosition(int position){
        uriList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, uriList.size());
    }

    @Override
    public int getItemCount() {
        return uriList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView closeIv;
        public ImageView photoUploadedIv;
        public View overlayV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            closeIv = itemView.findViewById(R.id.iv_row_upload_close);
            photoUploadedIv = itemView.findViewById(R.id.iv_row_upload_image);
            overlayV = itemView.findViewById(R.id.v_row_upload_overlay);

            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        removeItemAtPosition(position);
                    }
                }
            });
        }
    }
}
