package edu.uoc.notestop.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import edu.uoc.notestop.CheckListEdit;
import edu.uoc.notestop.Fragments.CheckListFragment;
import edu.uoc.notestop.MainActivity;
import edu.uoc.notestop.Models.NotesModels;
import edu.uoc.notestop.NotesEdit;
import edu.uoc.notestop.R;
import edu.uoc.notestop.SQLDataBase.ConexionSQLite;
import edu.uoc.notestop.SQLDataBase.UtilitiesSQL;

import static edu.uoc.notestop.R.drawable.ic_baseline_checklist_24;


public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.ViewHolder> {

    Activity activity;
    List<NotesModels> adapterNotesModelsSv = new ArrayList<>();

    Context context;
    TextView txtTitle;
    ImageView icon1;
    ImageView icon2;
    ImageView icon3;
    TextView txtCategories;
    TextView txtContent;
    TextView txtDate;
    MaterialCardView materialCardView;
    CheckListFragment ck;
    boolean aux;
    UtilitiesSQL utilitiesSQL;
    ConexionSQLite sq;
    public static ArrayList<Integer> selectedDelete = new ArrayList<>();


    //Creamos el constructor
    public AdapterNotes(Activity activity, List<NotesModels> arrayList, Context context) {
        this.activity = activity;
        this.adapterNotesModelsSv = arrayList;
        this.context = context;
        notifyDataSetChanged();
    }

    public AdapterNotes(){

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inicializamos Vistas
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_notes, parent, false);
        //ViewHolder holder = new ViewHolder(view);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Inicializamos los objetos de contactos

        NotesModels notesModels= adapterNotesModelsSv.get(position);


      //  holder.txtCategories.setText(notesModels.getCategories());
        holder.txtTitle.setText(notesModels.getTitle());

        if (notesModels.getChecklist() !=null) {
            holder.icon1.setImageResource(ic_baseline_checklist_24);
            holder.txtContent.setText((String) notesModels.getChecklist().toString().replace("[", "").replace("]", "").replace(",", ""));
        }
        if (notesModels.getContent() !=null) {
            holder.txtNoteContent.setText((String) notesModels.getContent());
            holder.icon1.setImageResource(R.drawable.ic_baseline_edit_note_24);
        }

        holder.txtDate.setText(notesModels.getDate());
        //Iconos en funcion de si tenemos audios o no.....
        if (notesModels.getMic()==1){
            holder.icon2.setImageResource(R.drawable.ic_baseline_mic_24);
        }else if(notesModels.getMic() ==0){
            holder.icon2.setImageResource(R.drawable.ic_baseline_mic_off_24);
        }
        // holder.icon2.setImageResource(notesModels.getIcon2());
        //holder.txtContent.setText(notesModels.getContent());

        holder.materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (notesModels.getNoteChecklist() == 0) {

                    //LLamo a la Activity de notes!!!
                    Intent intent = new Intent(context, NotesEdit.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    intent.putExtra("Id", notesModels.getId());
                    intent.putExtra("Categories", notesModels.getCategories());
                    intent.putExtra("Title", notesModels.getTitle());
                    intent.putExtra("mic", notesModels.getMic());
                    intent.putExtra("Content", notesModels.getContent());

                    notesModels.getAudioNotes();

                    //intent.putExtra("Audios",notesModels.getAudios());
                    context.getApplicationContext().startActivity(intent);

                } else if (notesModels.getNoteChecklist() == 1) {
                    Intent intent = new Intent(context, CheckListEdit.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("mic", notesModels.getMic());
                    intent.putExtra("Id", notesModels.getId());
                    intent.putExtra("Categories", notesModels.getCategories());
                    intent.putExtra("Title", notesModels.getTitle());


                   // notesModels.getAudioNotes();

                    //intent.putExtra("Audios",notesModels.getAudios());
                    context.getApplicationContext().startActivity(intent);
                }
            }

        });

        holder.materialCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (holder.materialCardView.isChecked()) {

                    // holder.materialCardView.setBackgroundColor(Color.parseColor("#FFD24C"));
                    //Le meto si no al array de objetos un booleano para que me pille si esta o no seleccionado

                    holder.materialCardView.setChecked(false);
                    holder.materialCardView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    deleteUnselected(selectedDelete, notesModels);
                    holder.icon3.setImageResource(R.drawable.ic_baseline_design_services_24);
                    aux=true;
                } else if(!holder.materialCardView.isChecked()){

                    holder.materialCardView.setChecked(true);
                    System.out.println(holder.txtTitle.getText());
                   //System.out.println(holder.txtCategories.getText());
                    aux=true;
                    holder.materialCardView.setBackgroundColor(Color.parseColor("#FFD24C"));
                    holder.icon3.setImageResource(R.drawable.ic_baseline_delete_forever_24);
                    //Array para ver los que estan seleccionadas y borrar las notas!!
                    selectedDelete.add(notesModels.getId());

                    for (int i =0;i<selectedDelete.size();i++){
                        System.out.println(selectedDelete.get(i).toString());
                    }

                    /*
                    AlertDialog.Builder exit= new AlertDialog.Builder(v.getContext());
                    exit.setMessage("¿Do you want to delete this Note?");
                    exit.setCancelable(true);
                    exit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(v.getContext() , "Se ha borrado con exito", Toast.LENGTH_SHORT).show();
                            sq = new ConexionSQLite(v.getContext(), "MisCUS", null, 4);
                            utilitiesSQL = new UtilitiesSQL();
                            int id =notesModels.getId();
                            utilitiesSQL.deleteNotes(sq,id);
                            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@LOS IDNOTESMAIN!!!1"+id);

                            //Tengo que diferenciar cuando es checklist y cuando borro nota
                          //  if (notesModels.getNoteChecklist()==1){
                           //     insertAcplishCheckList =true;
                           // }else if (notesModels.getNoteChecklist()==0){
                           //     insertAcplishNotes = true;
                           // }

                           Intent intent = new Intent(v.getContext(), MainActivity.class);
                           intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           v.getContext().startActivity(intent);
                        }
                    });
                    exit.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                            holder.materialCardView.setChecked(false);
                            holder.materialCardView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        }
                    });
                    AlertDialog salir =exit.create();
                    salir.setTitle("Delete Note");
                    salir.show();

                     */
                }
                return aux;
            }
        });
        //Glide.with(context).load(cuentosList.getImage()).into(holder.imgView);
        //int ident = model.getContact_Photo();
        //holder.imgView.setImageDrawable(cuentosList.getImage());
        //Aqui por medio de holder referenciamos el layout donde tenemos cada fila del RecyclerView para que con un método setOnclickListener al pulksar nos llecve a la nueva Activity
    }

    @Override
    public int getItemCount() {
        return adapterNotesModelsSv.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        //Referenciamos las variables, it´s holding the view somehow
        TextView txtTitle;
        ImageView icon1;
        ImageView icon2;
        ImageView icon3;
        TextView txtCategories;
        TextView txtContent;
        TextView txtNoteContent;
        TextView txtDate;
        MaterialCardView materialCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Asignamos las variables
            txtTitle = itemView.findViewById(R.id.tit);
            icon1 = itemView.findViewById(R.id.imgViewRecord);
            icon2 = itemView.findViewById(R.id.imgViewRecord2);
            icon3 = itemView.findViewById(R.id.imgViewRecord3);
          //  txtCategories = itemView.findViewById(R.id.cat);
            txtContent = itemView.findViewById(R.id.textViewText);
            txtDate = itemView.findViewById(R.id.textViewTextDate);
            materialCardView = itemView.findViewById(R.id.Notes);
            txtNoteContent = itemView.findViewById(R.id.textViewNote);
        }
    }

    public void deleteUnselected(ArrayList<Integer> selectedDelete, NotesModels notesmodels){

        for (int i=0;i<selectedDelete.size();i++) {

            if(selectedDelete.get(i) == notesmodels.getId()) {

                selectedDelete.remove(i);
                System.out.println(selectedDelete.toString());
            }
        }
    }

}
