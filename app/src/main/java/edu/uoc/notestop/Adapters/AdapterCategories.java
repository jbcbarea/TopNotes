package edu.uoc.notestop.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.uoc.notestop.Models.CategoriesModels;
import edu.uoc.notestop.R;


public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.ViewHolder> {

    Activity activity;
    List<CategoriesModels> categoriesModelsSv = new ArrayList<>();
    Context context;

    //Creamos el constructor
    public AdapterCategories(Activity activity, List<CategoriesModels> arrayList, Context context) {
        this.activity = activity;
        this.categoriesModelsSv = arrayList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inicializamos Vistas
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_categories, parent, false);
        //ViewHolder holder = new ViewHolder(view);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Inicializamos los objetos de contactos

        CategoriesModels categorieslist = categoriesModelsSv.get(position);

        holder.txtTitle.setText(categorieslist.getTitle());
        holder.imgView.setImageResource(categorieslist.getImage());
        //Glide.with(context).load(cuentosList.getImage()).into(holder.imgView);
        //int ident = model.getContact_Photo();
        //holder.imgView.setImageDrawable(cuentosList.getImage());
        //Aqui por medio de holder referenciamos el layout donde tenemos cada fila del RecyclerView para que con un método setOnclickListener al pulksar nos llecve a la nueva Activity
        holder.txtDescription.setText(categorieslist.getDescription());

    }

    @Override
    public int getItemCount() {
        return categoriesModelsSv.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        //Referenciamos las variables, it´s holding the view somehow
        TextView txtTitle;
        ImageView imgView;
        TextView txtDescription;
        CardView cardViewCategories;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Asignamos las variables
            txtTitle = itemView.findViewById(R.id.featured_title);
            imgView = itemView.findViewById(R.id.featured_image);
            cardViewCategories =itemView.findViewById(R.id.card_view_Categories);
            txtDescription = itemView.findViewById(R.id.featured_desc);
            //parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }

}


