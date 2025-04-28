package com.migu3lone.pryclinica.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.migu3lone.pryclinica.beans.medico;
import com.migu3lone.pryclinica.connection.ResponseMessage;
import com.migu3lone.pryclinica.R;

public class ViewMedicoAdapter extends RecyclerView.Adapter<ViewMedicoAdapter.ViewHolder> {
    private medico[] beans;
    private ViewMedicoAdapter.OnItemClickListener onItemClickListener;

    public ViewMedicoAdapter(ResponseMessage<medico[]> responseMessage, ViewMedicoAdapter.OnItemClickListener listener) {
        this.beans = responseMessage.getData();
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewMedicoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medico, parent, false);
        return new ViewMedicoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMedicoAdapter.ViewHolder holder, int position) {
        medico obj = beans[position];
        holder.vr1.setText(obj.getIdMedico());
        holder.vr2.setText(obj.medicox());
        holder.vr3.setText(obj.getDNI());
        holder.vr4.setText(obj.getCMP());
        holder.vr5.setText(obj.getDireccion());
        holder.vr6.setText(obj.getCelular());

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
                onItemClickListener.onDeleteClick(obj.getIdMedico());
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
        void onItemClick(medico obj);
        void onEditClick(medico obj);
        void onDeleteClick(String var1);
    }
}
