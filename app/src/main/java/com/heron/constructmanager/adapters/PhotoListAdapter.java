
package com.heron.constructmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.heron.constructmanager.R;
import com.heron.constructmanager.activities.views.PhotoViewActivity;
import com.heron.constructmanager.models.Photo;

import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {

    private final List photoList;
    private final String constructionUid;
    private final Context context;


    public PhotoListAdapter(List photoList, Context context, String constructionUid) {
        this.photoList = photoList;
        this.context = context;
        this.constructionUid = constructionUid;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView cardPhotoTitleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardPhotoTitleTextView = itemView.findViewById(R.id.card_photo_title_text);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View photoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Photo photo = (Photo) photoList.get(position);
        holder.cardPhotoTitleTextView.setText(photo.getTitle());

        holder.cardPhotoTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(context, PhotoViewActivity.class);
                intent = putExtrasPhoto(intent, photo);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    private Intent putExtrasPhoto(Intent intent, Photo photo) {
        intent.putExtra("constructionUid", constructionUid);
        intent.putExtra("title", photo.getTitle());
        intent.putExtra("desc", photo.getDesc());
        return intent;
    }

}
