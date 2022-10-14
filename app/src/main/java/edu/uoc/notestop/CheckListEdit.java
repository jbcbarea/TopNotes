package edu.uoc.notestop;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import edu.uoc.notestop.Models.CheckListModel;
import edu.uoc.notestop.SQLDataBase.ConexionSQLite;
import edu.uoc.notestop.SQLDataBase.UtilitiesSQL;

import static android.graphics.Paint.STRIKE_THRU_TEXT_FLAG;

public class CheckListEdit extends AppCompatActivity {

    private UtilitiesSQL utilitiesSQL;
    private ConexionSQLite sq;
    private String catego;
    private String titl;
    private int id;
    private LinearLayout layoutList;
    private  EditText editTextCat;
    private EditText editTextTitle;
    private Button buttonAdd;
    private Button buttonSubmitList;
    private ArrayList<CheckListModel> modeloCheckListArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list_edit);

        getIncomingIntent();

        editTextCat = findViewById(R.id.categoriesCK);
        editTextTitle = findViewById(R.id.titleCK);
        buttonAdd = findViewById(R.id.button_add);
        buttonSubmitList = findViewById(R.id.button_submit_list);

        utilitiesSQL = new UtilitiesSQL();
        sq = new ConexionSQLite(this.getBaseContext(), "MisCUS", null, 4);
        utilitiesSQL.checkListEditArray.removeAll(utilitiesSQL.checkListEditArray);
        utilitiesSQL.getArrayCheckListEdit(sq, id);
        System.out.println(utilitiesSQL.checkListEditArray.size());
        System.out.println(catego+titl+id);
        layoutList = findViewById(R.id.layout_list);
         addView();
         buttonAdd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 addViewAfter();
                 markoff_on(layoutList);
             }
         });

         buttonSubmitList.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 checkIfValidAndRead();
                 //Aqui llamada a la base de datos para que haga un update por si he cambiado cosas!!!
                 System.out.println(modeloCheckListArrayList.toString());
             }
         });
    }

    public void getIncomingIntent() {
        //Sacar la fecha Telefono, nombre , id........
        catego = getIntent().getStringExtra("Categories");
        titl = getIntent().getStringExtra("Title");
        id = getIntent().getIntExtra("Id", 0);
    }

    private void addView() {

        for (int i = 0; i < utilitiesSQL.checkListEditArray.size(); i++) {

            final View checkListView = getLayoutInflater().inflate(R.layout.add_row_checklist, null, false);

            ImageView imageClose = (ImageView)checkListView.findViewById(R.id.image_remove);

            imageClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutList.removeView(checkListView);
                }
            });
            EditText editTextName = checkListView.findViewById(R.id.edit_cricketer_name);
            CheckBox checkBox = checkListView.findViewById(R.id.checkBox);

            editTextName.setText(utilitiesSQL.checkListEditArray.get(i).getItem());
            if (utilitiesSQL.checkListEditArray.get(i).getBooleano().equals("oK")) {

                checkBox.setChecked(true);

                if (checkBox.isChecked()) {

                    //NO HARIA FALTA ESTE METODO PERO MOLA POR SI QUIERO HACER COSAS CON EL MIENTRAS ESTOY ESCRIBIENDO
                    editTextName.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            editTextName.setPaintFlags(editTextName.getPaintFlags() | STRIKE_THRU_TEXT_FLAG);
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            editTextName.setPaintFlags(editTextName.getPaintFlags() | STRIKE_THRU_TEXT_FLAG);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            editTextName.setPaintFlags(editTextName.getPaintFlags() | STRIKE_THRU_TEXT_FLAG);
                        }
                    });

                    editTextName.setPaintFlags(editTextName.getPaintFlags() | STRIKE_THRU_TEXT_FLAG);
                }

            } else if (utilitiesSQL.checkListEditArray.get(i).getBooleano().equals("X")) {
                checkBox.setChecked(false);

                if (!checkBox.isChecked()) {


                    editTextName.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            editTextName.setPaintFlags(editTextName.getPaintFlags() & (Paint.DITHER_FLAG));

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            editTextName.setPaintFlags(editTextName.getPaintFlags() & (Paint.DITHER_FLAG));
                            editTextName.setBackgroundColor(Color.CYAN);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            editTextName.setPaintFlags(editTextName.getPaintFlags() & (Paint.DITHER_FLAG));
                        }
                    });
                    editTextName.setPaintFlags(editTextName.getPaintFlags() & (Paint.DITHER_FLAG));
                }
            }
            layoutList.addView(checkListView);
        }
    }

    private void addViewAfter() {

        final View checkListView = getLayoutInflater().inflate(R.layout.add_row_checklist,null,false);

        ImageView imageClose = (ImageView)checkListView.findViewById(R.id.image_remove);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutList.removeView(checkListView);
            }
        });

        layoutList.addView(checkListView);

    }

    private void markoff_on(LinearLayout layoutList) {
        for(int i=0;i<layoutList.getChildCount();i++) {

            System.out.println(layoutList.getChildCount());
            System.out.println(layoutList.getChildCount());
            System.out.println(layoutList.getChildAt(i));
            View checklistView = layoutList.getChildAt(i);

            EditText editTextName = (EditText) checklistView.findViewById(R.id.edit_cricketer_name);
            CheckBox checkBox = (CheckBox) checklistView.findViewById(R.id.checkBox);
            CheckListModel checlist = new CheckListModel();

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkBox.isChecked()) {

                        //NO HARIA FALTA ESTE METODO PERO MOLA POR SI QUIERO HACER COSAS CON EL MIENTRAS ESTOY ESCRIBIENDO
                        editTextName.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                editTextName.setPaintFlags(editTextName.getPaintFlags() | STRIKE_THRU_TEXT_FLAG);
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                editTextName.setPaintFlags(editTextName.getPaintFlags() | STRIKE_THRU_TEXT_FLAG);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                editTextName.setPaintFlags(editTextName.getPaintFlags() | STRIKE_THRU_TEXT_FLAG);
                            }
                        });

                        editTextName.setPaintFlags(editTextName.getPaintFlags() | STRIKE_THRU_TEXT_FLAG);
                    }

                    if (!checkBox.isChecked()) {

                        editTextName.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                editTextName.setPaintFlags(editTextName.getPaintFlags() & (Paint.DITHER_FLAG));
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                editTextName.setPaintFlags(editTextName.getPaintFlags() & (Paint.DITHER_FLAG));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                editTextName.setPaintFlags(editTextName.getPaintFlags() & (Paint.DITHER_FLAG));
                            }
                        });
                        editTextName.setPaintFlags(editTextName.getPaintFlags() & (Paint.DITHER_FLAG));
                    }
                }
            });
        }
    }

    private void checkIfValidAndRead() {

        // modeloCheckListArrayList.removeAll(modeloCheckListArrayList);
        modeloCheckListArrayList.clear();

        for(int i=0;i<layoutList.getChildCount();i++){

            System.out.println(layoutList.getChildCount());
            System.out.println(layoutList.getChildCount());
            System.out.println(layoutList.getChildAt(i));
            View checklistView = layoutList.getChildAt(i);

            EditText editTextName = (EditText)checklistView.findViewById(R.id.edit_cricketer_name);
            CheckBox checkBox = (CheckBox)checklistView.findViewById(R.id.checkBox);
            CheckListModel checlist = new CheckListModel();

            if(!editTextName.getText().toString().equals("")){
                System.out.println(editTextName.getText().toString());
                checlist.setItem(editTextName.getText().toString());
            }

            if (checkBox.isChecked()){
                checlist.setBooleano("oK");
                //editTextName.setPaintFlags(editTextName.getPaintFlags() | STRIKE_THRU_TEXT_FLAG);
            }else{
                checlist.setBooleano("X");
            }
            modeloCheckListArrayList.add(checlist);
            System.out.println(modeloCheckListArrayList.get(i).getItem()+modeloCheckListArrayList.get(i).getBooleano());
        }
    }
}
