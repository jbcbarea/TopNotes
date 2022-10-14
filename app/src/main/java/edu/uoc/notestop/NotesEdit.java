package edu.uoc.notestop;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import edu.uoc.notestop.Models.NotesModels;
import edu.uoc.notestop.SQLDataBase.ConexionSQLite;
import edu.uoc.notestop.SQLDataBase.UtilitiesSQL;

public class NotesEdit extends AppCompatActivity  {

    private EditText title;
    private EditText content;
    private String titleString;
    public static boolean insertAcplishNotes =false;
    //para saber cuando vamos a voice recorder desde los fragmentos o desde ya una activida de una nota creada que queramos modificar....
    private final int note = 2;
    private static ArrayList<String> audios= new ArrayList<>();
    private String audiosRecord;
    private Handler handler;
    private ImageView recordNotes;
    private String contentString;
    private Button submit;
    private Spinner spinner;
    private String catego;
    private String titl;
    private String route;
    private String cont;
    private MediaPlayer mediaPlayer;
    private TextView timer;
    private MediaController mc;
    ImageView playPause;
    int playableSecond;
    ImageView imageView;
    int second;
    private ImageView leftbtn;
    private ImageView centerbtn;
    private ImageView rightbtn;
    private ImageView boldbtn;
    private ImageView italicbtn;
    private ImageView underlinebtn;
    private ImageView noeditbtn;
    int dummySecond;
    boolean isRecording =false;
    boolean isPlaying = false;
    public static ArrayList<NotesModels>notesModelAUDIO2=new ArrayList<>();
    private int id;
    private int mic;
    private ConexionSQLite sq;
    private UtilitiesSQL utilitiesSQL;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        audios.removeAll(audios);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_edit);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  Edit Note");
        actionBar.setSubtitle("  edit what you like");
        actionBar.setLogo(R.drawable.editnote);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        getIncomingIntent();

        System.out.println("Los audios a ver si salen!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+audiosRecord);

        title = findViewById(R.id.titleEditText);
        EditText categories = findViewById(R.id.categoriesEditText);
        content = findViewById(R.id.contentEditText);
        spinner = findViewById(R.id.spnote);
        submit = findViewById(R.id.button_submit_list);

        title.setText(titl, TextView.BufferType.EDITABLE);
        //categories.setText(catego,TextView.BufferType.EDITABLE);
        content.setText(cont, TextView.BufferType.EDITABLE);

        sq = new ConexionSQLite(this.getBaseContext(), "MisCUS", null, 4);
        //utilitiesSQL = new UtilitiesSQL();
        //utilitiesSQL.kukuruku.removeAll(utilitiesSQL.kukuruku);
        //utilitiesSQL.getArraysAudioContentNotes(sq,id);
        buttonFetch(sq,id);
        System.out.println("ME DUREMO AMIGO @@@@@@@@@@@@@@@@@@");
        System.out.println("tamaño"+utilitiesSQL.kukuruku.size());
       // for(int i=0;i<utilitiesSQL.notesModelAUDIO.size();i++) {
        //    String audio=utilitiesSQL.notesModelAUDIO.get(i).getAudioNotes().get(i).getName();
         //   System.out.println("dasdñalskdaksd"+audio);
         //   audios.add(audio);
       // }

        System.out.println("entrando en el bucle!!!!!!!!!!!!!!!!!!!!!!!!!!");

            if (audios.isEmpty()) {
                utilitiesSQL = new UtilitiesSQL();
                utilitiesSQL.kukuruku.removeAll(utilitiesSQL.kukuruku);
                utilitiesSQL.getArraysAudioContentNotes(sq,id);
                for (int i = 0; i < utilitiesSQL.kukuruku.size(); i++) {
                    audios.add(utilitiesSQL.kukuruku.get(i));
                }
            }

        System.out.println("American football ???????????????????????????????????????????" + (utilitiesSQL.kukuruku.toString()));
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ aver que pasa por aqui amigo!!!!!");
        System.out.println(audios.toString());
        //Lo estoy metiendo en la base de datos cada vez que doy a submit y repetidos por que ya estan en la base de datos
        if (audiosRecord !=null){

            audios.add(audiosRecord);
            //A ver si asi funciona en plan lo pongo a null cada vez que meta uno
            audiosRecord=null;
        }
        System.out.println("El array con los audios que vienen de grabar"+audios.toString());

        //Button submit to update the notes with the new content
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sq = new ConexionSQLite(getApplicationContext(), "MisCUS", null, 4);
                utilitiesSQL = new UtilitiesSQL();
                //Tengo que tener esto en cuenta tio recuerda!!!1
                //contentString= String.valueOf((content.getText()));
                //contentfinalString = contentString.replace("'", "''");
                utilitiesSQL.updateContentNotes(sq,id, String.valueOf(title.getText()),String.valueOf(content.getText()),audios );
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@/n");
                System.out.println(audios.toString());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                audios.removeAll(audios);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

       // System.out.println(utilitiesSQL.notesModelAUDIO.toString());
        ArrayAdapter<String> audiosAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item, audios);
        //audiosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(audiosAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Creo que aqui le meto al reproductor el audiio que sea
                String item = String.valueOf(spinner.getSelectedItem());
                route="/sdcard/Download/"+item+"";
                Toast.makeText(getApplicationContext(),"item"+item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        leftbtn = findViewById(R.id.leftbtn);
        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAlignmentLeft(v);
            }
        });
        centerbtn =findViewById(R.id.centerbtn);
        centerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAlignmentCenter(v);
            }
        });
        rightbtn = findViewById(R.id.rightbtn);
        rightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAlignmentRight(v);
            }
        });
        boldbtn = findViewById(R.id.boldbtn);
        boldbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonBold(v);
            }
        });
        italicbtn = findViewById(R.id.italicbtn);
        italicbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonItalics(v);
            }
        });
        underlinebtn = findViewById(R.id.underlinebtn);
        underlinebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonUnderline(v);
            }
        });
        noeditbtn = findViewById(R.id.noformatbtn);
        noeditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonNoFormat(v);
            }
        });

        //BOTON GRABAR NOTA DE AUDIO!!!!!!!!!!!!!!!!
        recordNotes = findViewById(R.id.recordingNotes);
        recordNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Aqui mejor mandar desde lo que pille en los edit Text asi si cambio cosas pues no pasaria nada!!!!ESTARIA MEJORT ASI
                //Intent intent = new Intent(getContext(), VoiceRecorder.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(intent);
                Intent intent = new Intent(getApplicationContext(), VoiceRecorder.class);
                intent.putExtra("noteEdit", note);
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%% A VER SI MANDA LA NOTEEDIT"+note);
                titl=title.getText().toString();
                cont=content.getText().toString();
                intent.putExtra("Title", titl);
                intent.putExtra("Content", cont);
                intent.putExtra("Id", id);
                intent.putExtra("mic", mic);

                //intent.putExtra("note", note);
                //System.out.println("AMIGO!!!"+note);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        mediaPlayer = new MediaPlayer();
        playPause = findViewById(R.id.play);
        timer = findViewById(R.id.time);
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (route!=null) {

                    if (!isPlaying) {

                        isPlaying = true;
                        playPause.setImageDrawable(ContextCompat.getDrawable(NotesEdit.this, R.drawable.pause_icon_151732));
                        mediaPlayer = new MediaPlayer();

                        try {
                            // System.out.println("La ruta Man!!!!!" + AudioSavaPath);
                            //Aqui me reproduce el archivo de audio que tengo en el telefono el rollo ahora es que pueda acceder a las grabaciones.
                            System.out.println("A ver la ruta si lo saca bien no entiendo entonces!!!!!"+route);
                            mediaPlayer.setDataSource(route);
                            //mediaPlayer.setDataSource("/sdcard/Download/jbsolo.mp3");
                            //File file = new File(Environment.getExternalStorageState(),"ejemplo");
                            //path =file.getPath();
                            //FileInputStream inputStream = new FileInputStream(file);
                            // mediaPlayer.setDataSource(inputStream.getFD());
                            //mediaPlayer.setDataSource(AudioSavaPath);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                            //runTimer();
                            Toast.makeText(NotesEdit.this, "Start playing", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //handler.removeCallbacks(null);
                    } else {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                        //We create antother mediaPlayer in case you want to play again
                        mediaPlayer=new MediaPlayer();
                        isPlaying = false;
                        //second=0;
                       // handler.removeCallbacksAndMessages(null);
                        //mediaPlayer= new MediaPlayer();
                        playPause.setImageDrawable(ContextCompat.getDrawable(NotesEdit.this, R.drawable.play_icon_151743));
                        Toast.makeText(NotesEdit.this, "Playing Paused", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(NotesEdit.this, "There is no AudioFile to play", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void runTimer() {
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (second % 3600) / 60;
                int secs = second % 60;
                String time = String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);
                timer.setText(time);

                if (isRecording || isPlaying && playableSecond !=-1) {
                    second++;
                    playableSecond--;

                    if(playableSecond==-1 && isPlaying) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer=null;
                        mediaPlayer=new MediaPlayer();
                        playableSecond=second;
                        second=0;
                        handler.removeCallbacksAndMessages(null);
                        return;
                    }
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_notes_checlist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.settings:
                Toast.makeText(this, "settings",Toast.LENGTH_LONG).show();
                //Dialogo instrucciones
                break;
            case R.id.toolbarmic:
                Toast.makeText(this, "toolbarMic",Toast.LENGTH_LONG).show();
                break;
            case R.id.toolbarsettings:
                Toast.makeText(this, "toolbarsettings",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getIncomingIntent() {
            //Sacar la fecha Telefono, nombre , id........
            catego = getIntent().getStringExtra("Categories");
            titl = getIntent().getStringExtra("Title");
            cont = getIntent().getStringExtra("Content");
            id = getIntent().getIntExtra("Id", 0);
            mic =getIntent().getIntExtra("mic",0);
            audiosRecord =getIntent().getStringExtra("audiRecord");

    }

    public void buttonFetch(ConexionSQLite sq,int id) {

        SQLiteDatabase db = sq.getReadableDatabase();
       // Cursor cursor = db.rawQuery("select IDEDITTEXT,EDITSTYLE,SELECTIONSTART,SELECTIONEND FROM EDITTEXT E JOIN NOTESMAIN N ON E.IDEDITTEXT=N.IDNOTESMAIN GROUP BY E.IDEDITTEXT having N.IDNOTESMAIN="+id+";", null);
       // Cursor cursor = db.rawQuery("select IDEDITTEXT,EDITSTYLE,SELECTIONSTART,SELECTIONEND FROM EDITTEXT E JOIN NOTESMAIN N ON E.IDEDITTEXT=N.IDNOTESMAIN GROUP BY E.IDEDITTEXT having E.IDEDITTEXT="+id+";", null);
        Cursor cursor = db.rawQuery("select IDEDITTEXT,EDITSTYLE,SELECTIONSTART,SELECTIONEND FROM EDITTEXT E JOIN NOTESMAIN N ON E.IDEDITTEXT=N.IDNOTESMAIN WHERE E.IDEDITTEXT="+id+";", null);
        try {
            cursor.moveToFirst();
            Spannable spannableString = new SpannableStringBuilder(content.getText());

            while (!cursor.isNull(0)){
                int intSelectionStart = cursor.getInt(2);
                int intSelectionEnd = cursor.getInt(3);
                System.out.println("En la ACTIVITY DE EDITNOTESBDSQL"+Integer.toString(intSelectionStart)+Integer.toString(intSelectionEnd));
                switch(cursor.getString(1)){
                    case "BOLD":
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD),
                                intSelectionStart, intSelectionEnd,
                                0);
                 //       System.out.println("En la ACTIVITY DE EDITNOTES"+Integer.toString(content.getSelectionStart())+Integer.toString(content.getSelectionEnd()));
                        content.setText(spannableString);
                        break;
                    case "CENTER":
                        content.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        content.setText(spannableString);
                        break;
                    case "RIGHT":
                        content.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                        content.setText(spannableString);
                        break;
                    case "LEFT":
                        content.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                        content.setText(spannableString);
                        break;
                    case "ITALIC":
                        spannableString.setSpan(new StyleSpan(Typeface.ITALIC),
                                intSelectionStart, intSelectionEnd,
                                0);
                        content.setText(spannableString);
                        break;
                    case "UNDERLINE":
                        spannableString.setSpan(new UnderlineSpan(),
                                intSelectionStart, intSelectionEnd,
                                0);
                        content.setText(spannableString);
                        break;
                }
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //When i press the button the format changes and
    public void buttonBold(View view){
        Spannable spannableString = new SpannableStringBuilder(content.getText());
        spannableString.setSpan(new StyleSpan(Typeface.BOLD),
                content.getSelectionStart(),
                content.getSelectionEnd(),
                0);


        utilitiesSQL = new UtilitiesSQL();
        utilitiesSQL.updateFormatText(sq,id,"BOLD", content.getSelectionStart(), content.getSelectionEnd());
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

        utilitiesSQL.updateFormatText(sq,id,"ITALIC", content.getSelectionStart(), content.getSelectionEnd());
        //InsertAttributesDatabase("ITALIC", editText.getSelectionStart(), editText.getSelectionEnd());

        content.setText(spannableString);
    }

    //Methods to implement the different formats in our edittext...
    public void buttonUnderline(View view){
        Spannable spannableString = new SpannableStringBuilder(content.getText());
        spannableString.setSpan(new UnderlineSpan(),
                content.getSelectionStart(),
                content.getSelectionEnd(),
                0);

        utilitiesSQL = new UtilitiesSQL();

        utilitiesSQL.updateFormatText(sq,id,"UNDERLINE", content.getSelectionStart(), content.getSelectionEnd());
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
        utilitiesSQL.updateFormatText(sq,id,"LEFT", 0, 0);
        //InsertAttributesDatabase("LEFT", 0, 0);
        content.setText(spannableString);
    }

    public void buttonAlignmentCenter(View view){
        content.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Spannable spannableString = new SpannableStringBuilder(content.getText());
        utilitiesSQL = new UtilitiesSQL();
        utilitiesSQL.updateFormatText(sq,id,"CENTER", 0, 0);
        //InsertAttributesDatabase("CENTER", 0, 0);
        content.setText(spannableString);
    }

    public void buttonAlignmentRight(View view){
        content.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        Spannable spannableString = new SpannableStringBuilder(content.getText());
        utilitiesSQL = new UtilitiesSQL();
        utilitiesSQL.updateFormatText(sq,id,"RIGHT", 0, 0);
        //InsertAttributesDatabase("RIGHT", 0, 0);
        content.setText(spannableString);
    }
}