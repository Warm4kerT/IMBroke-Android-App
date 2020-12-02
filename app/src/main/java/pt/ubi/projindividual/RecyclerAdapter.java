package pt.ubi.projindividual;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder>{

    private final LayoutInflater layoutInflater;
    private final Context cont;
    private List<Event> eList;
    private final OnDeleteClickListener onDeleteClickListener;

    public RecyclerAdapter(Context context, OnDeleteClickListener listener){
        layoutInflater = LayoutInflater.from(context);
        cont = context;
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = layoutInflater.inflate(R.layout.list_item, parent,false);
        return (new Holder(item));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if(eList != null){
            Event e = eList.get(position);
            holder.setData(e.toString(), position);
            holder.setListenners();
        }else{
            holder.tView.setText("NO EVENT!!!");
        }
    }

    @Override
    public int getItemCount() {
        if (eList != null){
            return eList.size();
        }else {
            return 0;
        }
    }

    public void setEvents(List<Event> events) {
        eList = events;
        notifyDataSetChanged();
    }

    public interface OnDeleteClickListener {
        void OnDeleteClickListener(Event event);
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView tView;
        private Button btn_delete, btn_edit;
        private int pos;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tView = itemView.findViewById(R.id.cardDescription);
            btn_delete = itemView.findViewById(R.id.cardBtnDelete);
            btn_edit = itemView.findViewById(R.id.cardBtnEdit);
        }

        public void setData(String text, int pos){
            tView.setText(text);
            this.pos = pos;
        }

        public void setListenners(){
            btn_edit.setOnClickListener(view -> {
                Intent edit = new Intent(cont, EditActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("ID",eList.get(pos).getId());
                edit.putExtras(b);
                ((Activity) cont).startActivityForResult(edit,2);
            });

            btn_delete.setOnClickListener(view -> {
                if(onDeleteClickListener != null){
                    onDeleteClickListener.OnDeleteClickListener(eList.get(pos));
                }
            });
        }
    }
}
