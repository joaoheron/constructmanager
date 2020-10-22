
package com.heron.constructmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.heron.constructmanager.R;
import com.heron.constructmanager.models.Construction;
import com.heron.constructmanager.activities.views.ConstructionPrepViewActivity;

import java.util.ArrayList;

public class ConstructionAdapter extends RecyclerView.Adapter<ConstructionAdapter.ViewHolder> {

    private final ArrayList<Construction> constructionsList;
    private final Context context;

    public ConstructionAdapter(ArrayList<Construction> constructionsList, Context context) {
        this.constructionsList = constructionsList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView cardConstructionTitleTextView, cardConstructionAddressTextView;
        Button cardConstructionEyeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardConstructionTitleTextView = itemView.findViewById(R.id.card_construction_title_text);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View constructionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.construction_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(constructionView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Construction construction = constructionsList.get(position);

        holder.cardConstructionTitleTextView.setText(construction.getTitle());
        holder.cardConstructionTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Definir a activity com base no stage da construction
                Intent intent = new Intent(context, ConstructionPrepViewActivity.class);
                intent.putExtra("title", construction.getTitle());
                intent.putExtra("address", construction.getAddress());
                intent.putExtra("stage", construction.getStage());
                intent.putExtra("type", construction.getType_construction());
                intent.putExtra("responsibles", construction.getResponsibles());
                intent.putExtra("uid", construction.getUid());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return constructionsList.size();
    }
}
