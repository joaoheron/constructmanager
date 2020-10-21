
package com.heron.constructmanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.heron.constructmanager.activities.ListaObrasActivity;
import com.heron.constructmanager.forms.NewObraForm;
import com.heron.constructmanager.models.Obra;
import com.heron.constructmanager.views.ObraPreparacaoView;

import java.util.ArrayList;

public class ObrasAdapter extends RecyclerView.Adapter<ObrasAdapter.ViewHolder> {

    private final ArrayList<Obra> obrasList;
    private final Context context;

    public ObrasAdapter(ArrayList<Obra> obrasList, Context context) {
        this.obrasList = obrasList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView card_obra_title_text, card_obra_address_text;
        Button card_obra_eye_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_obra_title_text = itemView.findViewById(R.id.card_obra_title_text);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View obraView = LayoutInflater.from(parent.getContext()).inflate(R.layout.obra_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(obraView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Obra obra = obrasList.get(position);

        holder.card_obra_title_text.setText(obra.getTitulo());
        holder.card_obra_title_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Definir a activity com base no stage da obra
                Intent intent = new Intent(context, ObraPreparacaoView.class);
                intent.putExtra("title", obra.getTitulo());
                intent.putExtra("address", obra.getEndereco());
                intent.putExtra("stage", obra.getEtapa());
                intent.putExtra("type", obra.getTipo_obra());
                intent.putExtra("responsibles", obra.getResponsibles());
                intent.putExtra("uid", obra.getUid());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return obrasList.size();
    }
}
