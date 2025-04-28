package com.migu3lone.pryclinica.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.migu3lone.pryclinica.beans.especialidad;
import com.migu3lone.pryclinica.connection.ResponseMessage;
import com.migu3lone.pryclinica.R;
//import java.util.List;

public class ViewEspecialidadAdapter extends RecyclerView.Adapter<ViewEspecialidadAdapter.ViewHolder> {
    private especialidad[] beans;
    private ViewEspecialidadAdapter.OnItemClickListener onItemClickListener; // Listener para el clic

    public ViewEspecialidadAdapter(ResponseMessage<especialidad[]> responseMessage, ViewEspecialidadAdapter.OnItemClickListener listener) {
        this.beans = responseMessage.getData();
        this.onItemClickListener = listener; // Pasar el listener desde el fragmento
    }

    @NonNull
    @Override
    public ViewEspecialidadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_especialidad, parent, false);
        return new ViewEspecialidadAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewEspecialidadAdapter.ViewHolder holder, int position) {
        especialidad obj = beans[position];
        holder.vr1.setText(obj.getIdEspecialidad());
        holder.vr2.setText(obj.getEspecialidad());
        //holder.vr1.setText(String.valueOf(obj.getIdEspecialidad()));

        // Manejar el clic en la celda
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(obj); // Pasa el objeto 'especialidad' al listener
            }
        });

        // Editar
        holder.editarImageView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onEditClick(obj);
            }
        });

        // Eliminar
        holder.eliminarImageView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onDeleteClick(obj.getIdEspecialidad());
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editarImageView = itemView.findViewById(R.id.editar);
            eliminarImageView = itemView.findViewById(R.id.eliminar);
            vr1 = itemView.findViewById(R.id.vr1);
            vr2 = itemView.findViewById(R.id.vr2);
        }
    }

    // Interfaz para manejar clics
    public interface OnItemClickListener {
        void onItemClick(especialidad obj); // Este método se llamará cuando se haga clic en una celda
        void onEditClick(especialidad obj);
        void onDeleteClick(String var1);
    }
}
