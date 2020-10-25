
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
import com.heron.constructmanager.activities.views.ConstructionCancelledViewActivity;
import com.heron.constructmanager.activities.views.ConstructionExecViewActivity;
import com.heron.constructmanager.activities.views.ConstructionFinishedViewActivity;
import com.heron.constructmanager.models.Construction;
import com.heron.constructmanager.activities.views.ConstructionPrepViewActivity;

import java.util.ArrayList;
import java.util.List;

public class ConstructionInformationAdapter extends RecyclerView.Adapter<ConstructionInformationAdapter.ViewHolder> {

    private final String PREPARACAO = "Preparação";
    private final String EXECUCAO = "Execução";
    private final String CONCLUIDA = "Concluída";
    private final String CANCELADA = "Cancelada";

    private final List constructionsList;
    private final Context context;


    public ConstructionInformationAdapter(List constructionsList, Context context) {
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
        final Construction construction = (Construction) constructionsList.get(position);
        holder.cardConstructionTitleTextView.setText(construction.getInformation().getTitle());
        holder.cardConstructionTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (construction.getInformation().getStage().equals(PREPARACAO)) {
                    intent = new Intent(context, ConstructionPrepViewActivity.class);
                } else if (construction.getInformation().getStage().equals(EXECUCAO)) {
                    intent = new Intent(context, ConstructionExecViewActivity.class);
                } else if (construction.getInformation().getStage().equals(CANCELADA)) {
                    intent = new Intent(context, ConstructionCancelledViewActivity.class);
                } else {
                    intent = new Intent(context, ConstructionFinishedViewActivity.class);
                }
                intent = putExtrasConstruction(intent, construction);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return constructionsList.size();
    }

    private Intent putExtrasConstruction(Intent intent, Construction construction) {
        intent.putExtra("title", construction.getInformation().getTitle());
        intent.putExtra("address", construction.getInformation().getAddress());
        intent.putExtra("stage", construction.getInformation().getStage());
        intent.putExtra("type", construction.getInformation().getType());
        intent.putExtra("responsibles", construction.getInformation().getResponsibles());
        intent.putExtra("constructionUid", construction.getUid());
        return intent;
    }

//    private void putExtrasConstructionExec(Intent intent, Construction construction) {
//        intent.putExtra("title", construction.getInformation().getTitle());
//        intent.putExtra("address", construction.getInformation().getAddress());
//        intent.putExtra("stage", construction.getInformation().getStage());
//        intent.putExtra("type", construction.getInformation().getType());
//        intent.putExtra("responsibles", construction.getInformation().getResponsibles());
//        intent.putExtra("constructionUid", construction.getUid());
//    }
//
//    private void putExtrasConstructionCancelled(Intent intent, Construction construction) {
//        intent.putExtra("title", construction.getInformation().getTitle());
//        intent.putExtra("address", construction.getInformation().getAddress());
//        intent.putExtra("stage", construction.getInformation().getStage());
//        intent.putExtra("type", construction.getInformation().getType());
//        intent.putExtra("responsibles", construction.getInformation().getResponsibles());
//        intent.putExtra("constructionUid", construction.getUid());
//    }
//
//    private void putExtrasConstructionFinished(Intent intent, Construction construction) {
//        intent.putExtra("title", construction.getInformation().getTitle());
//        intent.putExtra("address", construction.getInformation().getAddress());
//        intent.putExtra("stage", construction.getInformation().getStage());
//        intent.putExtra("type", construction.getInformation().getType());
//        intent.putExtra("responsibles", construction.getInformation().getResponsibles());
//        intent.putExtra("constructionUid", construction.getUid());
//    }
}
