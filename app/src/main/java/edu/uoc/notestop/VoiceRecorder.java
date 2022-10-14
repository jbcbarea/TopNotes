package edu.uoc.notestop;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.uoc.notestop.Fragments.CheckListFragment;
import edu.uoc.notestop.Fragments.NotesFragment;

import static edu.uoc.notestop.R.drawable.pause_icon_151732;

public class VoiceRecorder extends AppCompatActivity {

    //Aqui las variables de los botones tendría que meter.....
    private static final int REQUEST_AUDIO_PERMISSION_CODE = 1000;
    MediaRecorder mediaRecorder=null;
    MediaPlayer mediaPlayer;
    ImageView image;
    TextView txtPath;
    TextView timer;
    ImageView record;
    ImageView stop;
    String route;
    int noteOrCk;
    int noteOrCkEdit;
    public static int noteOrcK2;
    ImageView playPause;
    int playableSecond;
    private Button save;
    int second;
    int dummySecond;
    String audioNameString = null;
    public static String audioNameString2;
    String audioPath=null;
    public static File file;
    Handler handler;
    //Para saber si esta grabando o no o en pause para reproducir
    boolean isRecording =false;
    boolean isPlaying = false;
    LottieAnimationView lottie;
    //Importante vamos a ejecutar una tarea pesada asi que vamos a hacer esta operacion en segundo plano usamos para ello el ExcutorService
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    String catego;
    String titl;
    String cont;
    int id;
    int mic;
    //Mañana sigo con todo esto!!!

    //If i don´t submitted i wil deelete the audioFile!!
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(file != null){
            file.delete();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recorder);


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(edu.uoc.notestop.VoiceRecorder.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
            //Comprobacion para saber si el archivo manifest contiene los permisos, si no los posee, la aplicacion no funcionara
        }
        checkPermissions();

        noteOrCk = getIntent().getIntExtra("note", 5);
        noteOrCkEdit = getIntent().getIntExtra("noteEdit", 6);
        System.out.println("ESTOY DENTRO DE VOICE RECORDER PERO VENGO DESDE EDITNOTE"+noteOrCkEdit);
        System.out.println("la notes desde los frasgment"+noteOrCk);
        //catego = getIntent().getStringExtra("Categories");
        titl = getIntent().getStringExtra("Title");
        cont = getIntent().getStringExtra("Content");
        id = getIntent().getIntExtra("Id", 0);
        mic =getIntent().getIntExtra("mic",0);
        System.out.println("a ver en voiverecorde activity"+noteOrCk);
        mediaPlayer = new MediaPlayer();
        image = findViewById(R.id.imageView);
        lottie =findViewById(R.id.lottiewaves);
        txtPath = findViewById( R.id.pat);
        timer = findViewById(R.id.time);
        record = findViewById(R.id.record);
        playPause = findViewById(R.id.play);
        save = findViewById(R.id.btnSAve);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Al guardar mandar con intent el nomre y vamos a la actividad anterior
              // Intent intent = new Intent(getApplicationContext(), NotesFragment.class);
              //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              //  intent.putExtra("Name", audioNameString2);
                 //  startActivity(intent);
                //I would have to see now wether im coming from fragment or from the edit activities.....So from the activities i have to pass the id, and
                //also integer, wich i will know if its a note or checklist so i can get back

                if (noteOrCk ==0) {

                    NotesFragment notesFragment = new NotesFragment();
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@"+audioNameString2);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("audioname", audioNameString2);
                    notesFragment.setArguments(bundle);
                    //Asi vuelve al fragmento como lo tenía para seguir haciendo cosas que no se borre lo grabado!!!
                    Toast.makeText(edu.uoc.notestop.VoiceRecorder.this, "Se ha añadido la siguiente nota de voz, :"+audioNameString2+".No olvides pulsar al botón de guardar para no perder el archivo", Toast.LENGTH_LONG).show();
                    fragmentTransaction.replace(R.id.activityRecord, notesFragment).commit();
                   // fragmentTransaction.replace(R.id.activityRecord,new NotesFragment()).setArguments(bundle.commit();
                }else if(noteOrCk ==1){
                    Bundle bundle = new Bundle();
                    bundle.putString("audioname", audioNameString2);
                    CheckListFragment checkListFragment = new CheckListFragment();
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@"+audioNameString2);
                    checkListFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    Toast.makeText(edu.uoc.notestop.VoiceRecorder.this, "Se ha añadido la siguiente nota de voz, :"+audioNameString2+".No olvides pulsar al botón de guardar para no perder el archivo", Toast.LENGTH_LONG).show();
                    fragmentTransaction.replace(R.id.activityRecord, checkListFragment).commit();
                }

                if(noteOrCkEdit == 2) {
                    Intent intent = new Intent(getApplicationContext(), NotesEdit.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("audiRecord", audioNameString2);
                    intent.putExtra("Title", titl);
                    intent.putExtra("Content", cont);
                    intent.putExtra("Id", id);
                    intent.putExtra("mic", mic);
                    startActivity(intent);
                }

                //
               // Intent intent = new Intent(getApplicationContext(), AddNoteChecklist.class);
                //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //  intent.putExtra("Name", audioNameString2);
                //  if(noteOrCk == 0){
                      //  intent.putExtra("note",noteOrCk);
                //            noteOrcK2=0;
               //   }else if(noteOrCk ==1){
                      //intent.putExtra("cK", noteOrCk);
              //        noteOrcK2=1;
                //  }
                System.out.println("El valor de noteOrCK"+noteOrcK2);
             //     startActivity(intent);
                  finish();
            }
        });

        //playpause
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (audioPath!=null) {

                    if (!isPlaying) {

                        isPlaying = true;
                        playPause.setImageDrawable(ContextCompat.getDrawable(edu.uoc.notestop.VoiceRecorder.this, pause_icon_151732));
                       // lottie.setVisibility(View.VISIBLE);
                       // image.setVisibility(View.GONE);
                        mediaPlayer = new MediaPlayer();

                        try {
                            // System.out.println("La ruta Man!!!!!" + AudioSavaPath);
                            //Aqui me reproduce el archivo de audio que tengo en el telefono el rollo ahora es que pueda acceder a las grabaciones.
                            route="/sdcard/Download/"+audioNameString2+"";
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
                            runTimer();
                            Toast.makeText(edu.uoc.notestop.VoiceRecorder.this, "Start playing", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        handler.removeCallbacks(null);
                    } else {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                        //We create antother mediaPlayer in case you want to play again
                        mediaPlayer=new MediaPlayer();
                        isPlaying = false;
                        //second=0;
                        handler.removeCallbacksAndMessages(null);
                        //mediaPlayer= new MediaPlayer();
                        playPause.setImageDrawable(ContextCompat.getDrawable(edu.uoc.notestop.VoiceRecorder.this, R.drawable.play_icon_151743));
                        lottie.setVisibility(View.GONE);
                        image.setVisibility(View.VISIBLE);
                        Toast.makeText(edu.uoc.notestop.VoiceRecorder.this, "Playing Paused", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(edu.uoc.notestop.VoiceRecorder.this, "There is no AudioFile to play", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Recording Stoping!!!
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Primero tengo que seleccionar el nombre que le quiero poner!!!!! Y despues ya que empieze a grabar!!!.

                if (audioNameString==null){
                    openDialogSaveAudioFile(v);
                    Toast.makeText(edu.uoc.notestop.VoiceRecorder.this,"First make sure you give a name for your audio note, after that begin recording your notes", Toast.LENGTH_SHORT).show();
                }else {
                    if (!isRecording) {
                        isRecording = true;
                        record.setImageDrawable(ContextCompat.getDrawable(edu.uoc.notestop.VoiceRecorder.this, R.drawable.stop));
                        Toast.makeText(edu.uoc.notestop.VoiceRecorder.this, "Recording started", Toast.LENGTH_SHORT).show();
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                //Aqui esta tarea que es hevy la hacemos en segundo plano gracias al excutor!....

                                //Tendria que meter diálogo para introducir el nombre que queremos q tenga el archivo de audio
                                file = new File("/sdcard/Download/"+audioNameString+"");
                                audioNameString2=audioNameString;
                                audioPath = file.getPath();
                                System.out.println("weeeeeeeeeeeeeeeeeeeee MAn en el file" + file.getAbsolutePath());
                                mediaRecorder = new MediaRecorder();
                                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                                mediaRecorder.setOutputFile(file);

                                try {
                                    mediaRecorder.prepare();
                                    mediaRecorder.start();
                                    // Toast.makeText(VoiceRecorder.this, "Recording started", Toast.LENGTH_SHORT).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //Aqui otro proceso vamoa a actualizar la interfaz ya que ahora el objeto lottier estara activo y se cambiará por el imageView
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                       // lottie.setVisibility(View.VISIBLE);
                                       // image.setVisibility(View.GONE);
                                        txtPath.setText(audioPath);
                                        playableSecond = 0;
                                        second = 0;
                                        dummySecond = 0;
                                        runTimer();
                                    }
                                });
                            }
                        });
                    } else {

                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {

                                mediaRecorder.stop();
                             //   mediaRecorder.reset();
                                mediaRecorder.release();
                                mediaRecorder = null;
                                audioNameString = null;
                                playableSecond = second;
                                dummySecond = second;
                                second = 0;
                                isRecording = false;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                       // lottie.setVisibility(View.GONE);
                                       // image.setVisibility(View.VISIBLE);
                                        handler.removeCallbacks(null);
                                    }
                                });
                            }
                        });

                    }
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

    private boolean checkPermissions() {
        int first = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO);
        int second = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int third = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.MANAGE_EXTERNAL_STORAGE);

        return first == PackageManager.PERMISSION_GRANTED &&
                second == PackageManager.PERMISSION_GRANTED &&
                third == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int readExternalStoragePermision = ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE);
            return readExternalStoragePermision == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                startActivityForResult(intent, 100);
            } catch (Exception exception) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 100);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
        }
    }

    public void openDialogSaveAudioFile(View view) {

        AlertDialog.Builder builder= new AlertDialog.Builder(edu.uoc.notestop.VoiceRecorder.this);

        view = getLayoutInflater().inflate(R.layout.save_audiofile_dialog, null);
        EditText audioname= view.findViewById(R.id.audioname);
        builder.setView(view)
        .setMessage("Select name Audio File")
        .setCancelable(true)
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                audioNameString =audioname.getText().toString();

                System.out.println("El nombreeeee"+audioNameString);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        AlertDialog s =builder.create();
        s.setTitle("Selecting Audio Name");
        s.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_AUDIO_PERMISSION_CODE) {
            if (grantResults.length > 0) {
                boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (permissionToRecord) {
                    Toast.makeText(this, "Permission Given", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}