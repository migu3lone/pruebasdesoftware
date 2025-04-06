package com.migu3lone.pryclinica.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.migu3lone.pryclinica.beans.espmed;
import com.migu3lone.pryclinica.connection.ResponseMessage;
import com.migu3lone.pryclinica.R;

public class ViewEspmedAdapter extends RecyclerView.Adapter<ViewEspmedAdapter.ViewHolder> {
    private espmed[] beans;
    private ViewEspmedAdapter.OnItemClickListener onItemClickListener;

    public ViewEspmedAdapter(ResponseMessage<espmed[]> responseMessage, ViewEspmedAdapter.OnItemClickListener listener) {
        this.beans = responseMessage.getData();
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewEspmedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_espmed, parent, false);
        return new ViewEspmedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewEspmedAdapter.ViewHolder holder, int position) {
        espmed obj = beans[position];
        holder.vr1.setText(obj.getIdEspmed());
        holder.vr2.setText(obj.getFK_Especialidad());
        holder.vr3.setText(obj.getFK_Medico());

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
                onItemClickListener.onDeleteClick(obj.getIdEspmed());
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editarImageView = itemView.findViewById(R.id.editar);
            eliminarImageView = itemView.findViewById(R.id.eliminar);
            vr1 = itemView.findViewById(R.id.vr1);
            vr2 = itemView.findViewById(R.id.vr2);
            vr3 = itemView.findViewById(R.id.vr3);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(espmed obj);
        void onEditClick(espmed obj);
        void onDeleteClick(String var1);
    }
}
