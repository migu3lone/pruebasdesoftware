package com.migu3lone.pryclinica.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.migu3lone.pryclinica.beans.cita;
import com.migu3lone.pryclinica.connection.ResponseMessage;
import com.migu3lone.pryclinica.R;

public class ViewCitaAdapter extends RecyclerView.Adapter<ViewCitaAdapter.ViewHolder> {
    private cita[] beans;
    private ViewCitaAdapter.OnItemClickListener onItemClickListener;

    public ViewCitaAdapter(ResponseMessage<cita[]> responseMessage, ViewCitaAdapter.OnItemClickListener listener) {
        this.beans = responseMessage.getData();
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewCitaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cita, parent, false);
        return new ViewCitaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewCitaAdapter.ViewHolder holder, int position) {
        cita obj = beans[position];
        holder.vr1.setText(obj.getIdCita());
        holder.vr2.setText(obj.getPaciente());
        holder.vr3.setText(obj.getFK_Especialidad());
        holder.vr4.setText(obj.getFK_Medico());
        holder.vr5.setText(obj.getFecha());
        holder.vr6.setText(obj.horax());

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(obj);
            }
        });

        holder.editarImageView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onEditClick(obj);
            }
        });

        holder.eliminarImageView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onDeleteClick(obj.getIdCita());
                //onItemClickListener.onDeleteClick(obj);
            }
        });
    }

    @Override
    public int getItemCount() {
        return beans.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView  editarImageView;
        ImageView  eliminarImageView;
        TextView vr1;
        TextView vr2;
        TextView vr3;
        TextView vr4;
        TextView vr5;
        TextView vr6;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editarImageView = itemView.findViewById(R.id.editar);
            eliminarImageView = itemView.findViewById(R.id.eliminar);
            vr1 = itemView.findViewById(R.id.vr1);
            vr2 = itemView.findViewById(R.id.vr2);
            vr3 = itemView.findViewById(R.id.vr3);
            vr4 = itemView.findViewById(R.id.vr4);
            vr5 = itemView.findViewById(R.id.vr5);
            vr6 = itemView.findViewById(R.id.vr6);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(cita obj);
        void onEditClick(cita obj);
        void onDeleteClick(String var1);
        //void onDeleteClick(String idCita);
    }
}
