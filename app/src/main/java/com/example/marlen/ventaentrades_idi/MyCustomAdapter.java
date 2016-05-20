package com.example.marlen.ventaentrades_idi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.AdapterViewHolder> {

    ArrayList<Obra> obres;

    MyCustomAdapter() {
        obres = new ArrayList<>();
    }

    @Override
    public MyCustomAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rows, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCustomAdapter.AdapterViewHolder holder, int position) {
        holder.titol.setText(obres.get(position).getTitol());
        holder.preu.setText(obres.get(position).getPreu().toString() + "€");
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
        public TextView titol, preu;
        //public ImageView foto;
        public View v;
        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            this.titol = (TextView)itemView.findViewById(R.id.titolObra);
            this.preu = (TextView)itemView.findViewById(R.id.preuObra);
            //  this.foto = (ImageView)itemView.findViewById(R.id.fotoPerfil);
        }
    }

    public void setData (ArrayList<Obra> obres){
        this.obres = obres;
        notifyDataSetChanged();
    }
}
