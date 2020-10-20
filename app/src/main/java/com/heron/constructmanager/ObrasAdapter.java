package com.heron.constructmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.heron.constructmanager.models.Obra;

import java.util.ArrayList;

public class ObrasAdapter extends RecyclerView.Adapter<ObrasAdapter.ViewHolder> {

    private final ArrayList<Obra> obrasList;
    private final Context context;

    public ObrasAdapter(ArrayList<Obra> obrasList, Context context) {
        this.obrasList = obrasList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title_obra_text, address_obra_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_obra_text = itemView.findViewById(R.id.title_obra_text);
            address_obra_text = itemView.findViewById(R.id.address_obra_text);
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
        holder.title_obra_text.setText(obrasList.get(position).getTitulo());
        holder.address_obra_text.setText(obrasList.get(position).getEndereco());

    }

    @Override
    public int getItemCount() {
        return obrasList.size();
    }
}
