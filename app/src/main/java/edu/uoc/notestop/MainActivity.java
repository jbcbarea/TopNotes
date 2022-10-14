package edu.uoc.notestop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import edu.uoc.notestop.Adapters.AdapterCategories;
import edu.uoc.notestop.Adapters.AdapterNotes;
import edu.uoc.notestop.Adapters.NoteCheckListAdapter;
import edu.uoc.notestop.Models.CategoriesModels;
import edu.uoc.notestop.Models.NotesModels;
import edu.uoc.notestop.SQLDataBase.ConexionSQLite;
import edu.uoc.notestop.SQLDataBase.UtilitiesSQL;


public class MainActivity extends AppCompatActivity {

    RecyclerView rvCategories;
    RecyclerView rvNoteList;
    AdapterCategories adapter;
    ImageView menu;
    ConexionSQLite sq;
    UtilitiesSQL utilitiesSQL;
    AdapterNotes adapterNotes;
    ImageButton addnote;
    ImageButton deletenote;
    ImageView close;
    AdapterNotes adapter2;
    ArrayList<String> listString = new ArrayList<>();
    List<CategoriesModels> list = new ArrayList<>();
    List<NotesModels> list2 = new ArrayList<>();

    @Override
    public void onBackPressed() {

        //Al eliminar el super pierde su funcionalidad para que no me vuelva a los fragmentos anteriores
       //  super.onBackPressed();
        // finish();
        //Voy a poner funcionalidad que salga cuando le de atras ded la aplicacion asi sin problemas amigo!!!
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        sq = new ConexionSQLite(getApplicationContext(), "MisCUS", null, 4);
        utilitiesSQL = new UtilitiesSQL();
        utilitiesSQL.notesmodeloMain.removeAll(utilitiesSQL.notesmodeloMain);
        utilitiesSQL.consultFromSQLiteNotes(sq);
        utilitiesSQL.consultFromSQLiteChecklist(sq);

        menu =findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "I have to implement the settings", Toast.LENGTH_SHORT).show();
            }
        });

        rvCategories = findViewById(R.id.recyclerCategories);
        rvCategories.setHasFixedSize(true);
        rvCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        rvNoteList = findViewById(R.id.RecyclerNotes);
        addnote = findViewById(R.id.addnote);
        deletenote = findViewById(R.id.deletenote);
        rvNoteList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder exit= new AlertDialog.Builder(MainActivity.this);
                exit.setMessage("¿Exit?");
                exit.setCancelable(true);
                exit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finishAffinity();
                        System.exit(0);
                    }
                });
                exit.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
                AlertDialog salir =exit.create();
                salir.setTitle("Closing the App");
                salir.show();
            }
        });
        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterNotes = new AdapterNotes();
                //Aqui hacer metodo para que lea el array y borre todas las notes donde el id es el que tiene el array!
                adapterNotes.selectedDelete.removeAll(adapterNotes.selectedDelete);
                Intent intent = new Intent(MainActivity.this,AddNoteChecklist.class);
                startActivity(intent);
            }
        });
        deletenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (adapterNotes.selectedDelete.size() == 0) {
                    Toast.makeText(MainActivity.this, "You must select some Notes in order to delete them", Toast.LENGTH_SHORT).show();
                }else{
                AlertDialog.Builder exit = new AlertDialog.Builder(v.getContext());
                exit.setMessage("¿Do you want to delete the selected Notes?");
                exit.setCancelable(true);
                exit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(v.getContext(), "Se ha borrado con exito", Toast.LENGTH_SHORT).show();
                        sq = new ConexionSQLite(v.getContext(), "MisCUS", null, 4);
                        utilitiesSQL = new UtilitiesSQL();
                        adapterNotes = new AdapterNotes();
                        System.out.println("Tamaño del array para borrar");
                        System.out.println(adapterNotes.selectedDelete.size());
                        //Aqui hacer metodo para que lea el array y borre todas las notes donde el id es el que tiene el array!
                        for (int i = 0; i < adapterNotes.selectedDelete.size(); i++) {
                            System.out.println("A ver los que tengo seleccionados tio!!!!"+adapterNotes.selectedDelete.get(i));
                            int id = adapterNotes.selectedDelete.get(i);
                            //Para sacar de cada nota a borrar los audios correspondientes y borrarlos tambien vamos a ver si lo consigo
                          // utilitiesSQL.getArraysAudioContentNotes(sq,id);
                            utilitiesSQL.deleteNotes(sq, id);
                        }
                        adapterNotes.selectedDelete.removeAll(adapterNotes.selectedDelete);
                        File file = new File("/sdcard/Download/sacri.mp3");
                        file.delete();
                        //utilitiesSQL.deleteNotes(sq,id);
                        // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@LOS IDNOTESMAIN!!!1"+id);

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
                        // holder.materialCardView.setChecked(false);
                        // holder.materialCardView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                });
                AlertDialog salir = exit.create();
                salir.setTitle("Delete Note");
                salir.show();
            }
            }
        });

        RecyclerCategoriesInit();
        this.adapter2 =new AdapterNotes(this,utilitiesSQL.notesmodeloMain,getApplicationContext());
        this.adapter = new AdapterCategories(this,list,getApplicationContext());
        rvNoteList.setAdapter(adapter2);
        rvCategories.setAdapter(adapter);

    }
    private void RecyclerCategoriesInit() {

        list.add(new CategoriesModels(R.drawable.allnotes,"All Notes","Display all your notes"));
        list.add(new CategoriesModels(R.drawable.calendar_date_event_schedule_icon_127220,"Latest Date","Display all your notes, order by latest date"));
        list.add(new CategoriesModels(R.drawable.title,"Title","Display all your notes, order by Title"));
        list.add(new CategoriesModels(R.drawable.audionotes,"Audio Notes","Display all your notes, with audioNote"));
        list.add(new CategoriesModels(R.drawable.datedown,"Recent Date","Display all your notes, order by recent date"));
        list.add(new CategoriesModels(R.drawable.categories,"Categories","Display all your notes, order by custom categories"));
    }
}

