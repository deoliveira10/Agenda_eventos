package com.example.calendario_agenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    ArrayList<Eventos> list;

    public MyAdapter(Context context, ArrayList<Eventos> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        Eventos eventos = list.get(position);
        holder.evento.setText(eventos.getEvento());
        holder.local.setText(eventos.getLocal());
        holder.data.setText(eventos.getData());
        holder.horario.setText(eventos.getHorario());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView evento, local, data, horario;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            evento = itemView.findViewById(R.id.tvEvent);
            local = itemView.findViewById(R.id.tvLocal);
            data = itemView.findViewById(R.id.tvData);
            horario = itemView.findViewById(R.id.tvHorario);
        }
    }

}
