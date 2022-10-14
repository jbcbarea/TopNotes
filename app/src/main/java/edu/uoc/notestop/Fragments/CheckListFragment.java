package edu.uoc.notestop.Fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import edu.uoc.notestop.MainActivity;
import edu.uoc.notestop.Models.CheckListModel;
import edu.uoc.notestop.R;
import edu.uoc.notestop.SQLDataBase.ConexionSQLite;
import edu.uoc.notestop.SQLDataBase.UtilitiesSQL;
import edu.uoc.notestop.VoiceRecorder;

import static android.graphics.Paint.STRIKE_THRU_TEXT_FLAG;


public class CheckListFragment extends Fragment {

    private LinearLayout layoutList;
    private LinearLayout mainLinear;
    private ConexionSQLite sq;
    private UtilitiesSQL utilitiesSQL;
    public static boolean insertAcplishCheckList =false;
    private Button buttonAdd;
    private Button buttonSubmitList;
    private String audionotesName;
    private final int note = 1;
    private EditText editTextCat;
    private EditText editTextTitle;
    private ImageView recordCN;
    private String cat;
    private String tit;
    private ArrayList<CheckListModel> modeloCheckListArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.checklist_fragment_xml,container,false);

        editTextCat = root.findViewById(R.id.categoriesCK);
        editTextTitle = root.findViewById(R.id.titleCK);
        layoutList = root.findViewById(R.id.layout_list);
        buttonAdd = root.findViewById(R.id.button_add);
        buttonSubmitList = root.findViewById(R.id.button_submit_list);

        Bundle bundle=getArguments();

        //  try{

        //}catch (NullPointerException e){

        //}

        if(bundle!=null){
            audionotesName = (String) bundle.get("audioname");
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+audionotesName);
        }

        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$CHECLIST"+audionotesName);
        recordCN = root.findViewById(R.id.recordingNotes);
        System.out.println("fragmento CHEcklist!!!!!!!!!!!!!!!!!!!!!!");
        recordCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(getContext(), VoiceRecorder.class);
               // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               // startActivity(intent);
                Intent intent = new Intent(getContext(), VoiceRecorder.class);
                intent.putExtra("note", note);
                System.out.println("AMIGO!!!"+note);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        //mainLinear = root.findViewById(R.id.MainLinear);
        //Drawable image  = ContextCompat.getDrawable(getContext(), R.drawable.cool);
        //mainLinear.setBackground(image);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();
                markoff_on(layoutList);
            }
        });
        buttonSubmitList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println(layoutList.getChildCount());
                checkIfValidAndRead();
                cat =  String.valueOf((editTextCat.getText()));
                tit =  String.valueOf((editTextTitle.getText()));
                insertAcplishCheckList=true;
                sq = new ConexionSQLite(getContext(), "MisCUS", null, 4);
                utilitiesSQL = new UtilitiesSQL();
                utilitiesSQL.insertChecklistNotes(sq, tit, 1, cat, modeloCheckListArrayList);
                editTextCat.setText(null);
                editTextTitle.setText(null);

                Toast.makeText(getContext() , "Se ha insertado con exito", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return root;
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

    private void addView() {

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