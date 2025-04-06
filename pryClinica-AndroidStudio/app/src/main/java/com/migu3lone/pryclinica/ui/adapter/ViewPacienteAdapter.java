package com.migu3lone.pryclinica.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.migu3lone.pryclinica.beans.paciente;
import com.migu3lone.pryclinica.connection.ResponseMessage;
import com.migu3lone.pryclinica.R;

public class ViewPacienteAdapter extends RecyclerView.Adapter<ViewPacienteAdapter.ViewHolder> {
    private paciente[] beans;
    private ViewPacienteAdapter.OnItemClickListener onItemClickListener;

    public ViewPacienteAdapter(ResponseMessage<paciente[]> responseMessage, ViewPacienteAdapter.OnItemClickListener listener) {
        this.beans = responseMessage.getData();
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewPacienteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paciente, parent, false);
        return new ViewPacienteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPacienteAdapter.ViewHolder holder, int position) {
        paciente obj = beans[position];
        holder.vr1.setText("ID: " + obj.getIdPaciente());
        holder.vr2.setText("Paciente: "+obj.pacientex());
        holder.vr3.setText("DNI: " + obj.getDNI());
        holder.vr4.setText("Sexo: " + obj.sexox());
        holder.vr5.setText("Fecha: " + obj.getNacimiento());
        holder.vr6.setText("Email: " + obj.getCorreo());
        holder.vr7.setText("Distrito: " + obj.getDireccion());
        holder.vr8.setText("Tel: " + obj.getCelular());

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
                onItemClickListener.onDeleteClick(obj.getIdPaciente());
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
        TextView vr7;
        TextView vr8;

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
            vr7 = itemView.findViewById(R.id.vr7);
            vr8 = itemView.findViewById(R.id.vr8);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(paciente obj);
        void onEditClick(paciente obj);
        void onDeleteClick(String var1);
    }
}
