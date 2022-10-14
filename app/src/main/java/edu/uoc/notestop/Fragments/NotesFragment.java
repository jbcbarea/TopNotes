package edu.uoc.notestop.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.sql.SQLOutput;
import java.util.ArrayList;

import edu.uoc.notestop.MainActivity;
import edu.uoc.notestop.R;
import edu.uoc.notestop.SQLDataBase.ConexionSQLite;
import edu.uoc.notestop.SQLDataBase.UtilitiesSQL;
import edu.uoc.notestop.VoiceRecorder;

import static android.content.Intent.getIntent;


public class NotesFragment extends Fragment {

    private ConexionSQLite sq;
    private UtilitiesSQL utilitiesSQL;
    private final int note = 0;
    private EditText title;
    private EditText content;
    private String titleString;
    public static boolean insertAcplishNotes =false;
    private String type;
    private ImageView recordNotes;
    public static ArrayList<String> audiosArray =new ArrayList<>();
    private String audionotesName;
    private String contentString;
    private String contentfinalString;
    private Button submit;
    private Spinner spinner;
    private ImageView leftbtn;
    private ImageView centerbtn;
    private ImageView rightbtn;
    private ImageView boldbtn;
    private ImageView italicbtn;
    private ImageView underlinebtn;
    private ImageView noeditbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.notes_fragment_xml,container,false);


        EditText categories = root.findViewById(R.id.categoriesEditText);
        title = root.findViewById(R.id.titleEditText);
        content = root.findViewById(R.id.contentEditText);
        spinner = root.findViewById(R.id.spnote);
        Bundle bundle=getArguments();
      //  try{
        //}catch (NullPointerException e){
        //}
        audiosArray.clear();

        if(bundle!=null){
            audionotesName = (String) bundle.get("audioname");
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+audionotesName);



            audiosArray.add(audionotesName);
            //ArrayAdapter<String> audiosAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, audiosArray);
            //audiosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //spinner.setAdapter(audiosAdapter);
            //Toast.makeText(getContext(), "Se ha añadido el audio"+audionotesName, Toast.LENGTH_SHORT).show();
        }

        if (audiosArray!=null) {
            System.out.println("qweqweqweqweqwewqe"+audiosArray.size());

        }

        System.out.println();
        //Spinner!!!!!!
       // if(audiosArray!=null) {
        //    ArrayAdapter<String> audiosAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, audiosArray);
         //   audiosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          //  spinner.setAdapter(audiosAdapter);
       // }
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+audionotesName);

        sq = new ConexionSQLite(getContext(), "MisCUS", null, 4);
        utilitiesSQL = new UtilitiesSQL();
        root.setTranslationY(300);
        root.animate().translationY(0).setDuration(1000).setStartDelay(100).start();

        leftbtn =root.findViewById(R.id.leftbtn);
        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAlignmentLeft(v);
            }
        });
        centerbtn =root.findViewById(R.id.centerbtn);
        centerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAlignmentCenter(v);
            }
        });
        rightbtn = root.findViewById(R.id.rightbtn);
        rightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAlignmentRight(v);
            }
        });
        boldbtn = root.findViewById(R.id.boldbtn);
        boldbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonBold(v);
            }
        });
        italicbtn = root.findViewById(R.id.italicbtn);
        italicbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonItalics(v);
            }
        });
        underlinebtn = root.findViewById(R.id.underlinebtn);
        underlinebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonUnderline(v);
            }
        });
        noeditbtn = root.findViewById(R.id.noformatbtn);
        noeditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonNoFormat(v);
            }
        });

        //BOTON GRABAR NOTA DE AUDIO!!!!!!!!!!!!!!!!
        recordNotes = root.findViewById(R.id.recordingNotes);
        recordNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(getContext(), VoiceRecorder.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(intent);
                Intent intent = new Intent(getContext(), VoiceRecorder.class);
                intent.putExtra("note", note);
                System.out.println("AMIGO!!!"+note);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        //GUARDAR LAS NOTAS SUBMIT!!!!!!!!!
        submit = root.findViewById(R.id.button_submit_list);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //utilitiesSQL.insertContentListMain(sq, titleString, 1, type, contentString);
                //Tengo que capturarlo dentro del boton el strin
                //
                titleString = String.valueOf((title.getText()));
                type = String.valueOf((categories.getText()));
                //Cambiar lo de replace por si tuviera alguna apóstrofe!!!
                contentString= String.valueOf((content.getText()));
                contentfinalString = contentString.replace("'", "''");
                insertAcplishNotes=true;
                sq = new ConexionSQLite(getContext(), "MisCUS", null, 4);
                utilitiesSQL = new UtilitiesSQL();
                if(audiosArray!=null) {
                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEooo lo audios que manda"+audiosArray.toString());
                    System.out.println(audiosArray.size());
                }

                utilitiesSQL.insertContentNotes(sq, titleString, 1, type, contentfinalString,audiosArray);
                audiosArray.removeAll(audiosArray);
                audionotesName=null;
                title.setText(null);
                categories.setText(null);
                content.setText(null);
                System.out.println(titleString);
                System.out.println(type);
                System.out.println(contentString);
                Toast.makeText(getContext() , "Se ha insertado con exito", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return root;
    }

    public void buttonBold(View view){
        Spannable spannableString = new SpannableStringBuilder(content.getText());
        spannableString.setSpan(new StyleSpan(Typeface.BOLD),
                content.getSelectionStart(),
                content.getSelectionEnd(),
                0);


        utilitiesSQL = new UtilitiesSQL();
        utilitiesSQL.insertFormatText(sq,"BOLD", content.getSelectionStart(), content.getSelectionEnd());
        System.out.println("qqqqqqqqqqqqqqqqqqqquertyyyy!!!!!!"+Integer.toString(content.getSelectionStart())+"WERWERWERWER"+Integer.toString(content.getSelectionEnd()));
        content.setText(spannableString);
    }
    public void buttonItalics(View view){
        Spannable spannableString = new SpannableStringBuilder(content.getText());
        spannableString.setSpan(new StyleSpan(Typeface.ITALIC),
                content.getSelectionStart(),
                content.getSelectionEnd(),
                0);


        utilitiesSQL = new UtilitiesSQL();

        utilitiesSQL.insertFormatText(sq,"ITALIC", content.getSelectionStart(), content.getSelectionEnd());
        //InsertAttributesDatabase("ITALIC", editText.getSelectionStart(), editText.getSelectionEnd());

        content.setText(spannableString);
    }

    public void buttonUnderline(View view){
        Spannable spannableString = new SpannableStringBuilder(content.getText());
        spannableString.setSpan(new UnderlineSpan(),
                content.getSelectionStart(),
                content.getSelectionEnd(),
                0);


        utilitiesSQL = new UtilitiesSQL();

        utilitiesSQL.insertFormatText(sq,"UNDERLINE", content.getSelectionStart(), content.getSelectionEnd());
        //InsertAttributesDatabase("UNDERLINE", editText.getSelectionStart(), editText.getSelectionEnd());
        content.setText(spannableString);
    }

    public void buttonNoFormat(View view){
        String stringText = content.getText().toString();
        content.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

        content.setText(stringText);
    }

    public void buttonAlignmentLeft(View view){
        content.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        Spannable spannableString = new SpannableStringBuilder(content.getText());
        utilitiesSQL = new UtilitiesSQL();
        utilitiesSQL.insertFormatText(sq,"LEFT", 0, 0);
        //InsertAttributesDatabase("LEFT", 0, 0);
        content.setText(spannableString);
    }

    public void buttonAlignmentCenter(View view){
        content.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Spannable spannableString = new SpannableStringBuilder(content.getText());
        utilitiesSQL = new UtilitiesSQL();
        utilitiesSQL.insertFormatText(sq,"CENTER", 0, 0);
        //InsertAttributesDatabase("CENTER", 0, 0);
        content.setText(spannableString);
    }

    public void buttonAlignmentRight(View view){
        content.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        Spannable spannableString = new SpannableStringBuilder(content.getText());
        utilitiesSQL = new UtilitiesSQL();
        utilitiesSQL.insertFormatText(sq,"RIGHT", 0, 0);
        //InsertAttributesDatabase("RIGHT", 0, 0);
        content.setText(spannableString);

    }

}
