package com.example.marlen.ventaentrades_idi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCustomAdapterDays extends RecyclerView.Adapter<MyCustomAdapterDays.AdapterViewHolder> {

    ArrayList<Obra> obres;

    MyCustomAdapterDays() {
        obres = new ArrayList<>();
    }

    @Override
    public MyCustomAdapterDays.AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rows_days, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCustomAdapterDays.AdapterViewHolder holder, int position) {
        holder.data.setText(obres.get(position).getData());
        holder.but_disp.setText(obres.get(position).getButDisp().toString());
        holder.diaFuncio.setText(obres.get(position).getDia());
        if(position %2 == 0)
            holder.itemView.setBackgroundColor(0xFFCFD8DC);
        else holder.itemView.setBackgroundColor(0xFFB0BEC5);

    }

    @Override
    public int getItemCount() {
        if(obres!=null) return obres.size();
        else return 0;
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder{
        public TextView data, but_disp, diaFuncio;
        public View v;
        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            this.data = (TextView)itemView.findViewById(R.id.dataDays);
            this.but_disp = (TextView)itemView.findViewById(R.id.num_but);
            this.diaFuncio = (TextView)itemView.findViewById(R.id.diaSet);
        }
    }

    public void setData (ArrayList<Obra> obres){
        this.obres = obres;
        notifyDataSetChanged();
    }
}
