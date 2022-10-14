package edu.uoc.notestop.SQLDataBase;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import edu.uoc.notestop.Models.AudioNotes;
import edu.uoc.notestop.Models.CheckListModel;
import edu.uoc.notestop.Models.NotesModels;

import static edu.uoc.notestop.Fragments.NotesFragment.insertAcplishNotes;


public class UtilitiesSQL {

    public static ArrayList<NotesModels> notesmodeloMain = new ArrayList<>();

    public static ArrayList<String>kukuruku=new ArrayList<>();
    public static ArrayList<CheckListModel>checkListEditArray = new ArrayList<>();
    int i =0;
    int o=8;
    //ArrayList<NotesModels>ku=new ArrayList<>();
    private int notesPk;
    private int checkListPK;
    private int audioFilesPk;
    private int mic_On_Off ;
    private ArrayList<AudioNotes> audios = new ArrayList<>();
   ArrayList<CheckListModel> checkLists = new ArrayList<>();


    public void consultFromSQLiteNotes(ConexionSQLite sq) {

        SQLiteDatabase db = sq.getReadableDatabase();
        SQLiteDatabase db2 =sq.getWritableDatabase();

        if (insertAcplishNotes){

            notesmodeloMain.removeAll(notesmodeloMain);
            insertAcplishNotes = false;
        }
        //notesmodels.removeAll(notesmodels);

        //String sql2 = " INSERT INTO NOTESMAIN  VALUES (1,'sdfsd', date('now'),1,0);";
       // db2.execSQL(sql2);
        String sql="select N.IDNOTESMAIN,N.TITLE,N.MIC,N.DATENOTE,C.TYPE,NC.CONTENT,N.NOTECHECKLIST FROM categories C JOIN notesmain N ON C.CATID=N.IDNOTESMAIN JOIN notescontent NC ON NC.IDNOTESCONTENT=N.IDNOTESMAIN;";
        //PARA SACAR LOS AUDIOS Y LOS METO EN UN ARRAY QUE LLEVARA NOTES MAIN
        //String sql ="select n.idnotesmain,n.title,n.datenote,n.mic,c.type,nc.content from categories c join notesmain n on c.catid=n.idnotesmain join notescontent nc on nc.idnotescontent=n.idnotesmain;";
        //SELECT A.AUDIONAME,A.ROUTE FROM AUDIOFILES A JOIN NOTESMAIN N ON IDAUDIOFILES=N.IDNOTESMAIN WHERE N.IDNOTESMAIN=1;
        //  cuentosRVArrayList.removeAll(cuentosRVArrayList);
       // android.database.Cursor cursor = db.rawQuery(sql, null);
        Cursor cursor = db.rawQuery(sql, null);
        System.out.println("***************************************************44");
        System.out.println(cursor.getCount());
        while (cursor.moveToNext()) {
/*
            audios.removeAll(audios);
            //En cada nota se mete los audios correspondientes !!!
            String sqlAudio = "SELECT A.ROUTE,A.AUDIONAME FROM AUDIOFILES A JOIN NOTESMAIN N ON A.IDAUDIOFILES=N.IDNOTESMAIN WHERE N.IDNOTESMAIN ="+cursor.getInt(0)+";";
            Cursor cursorAudios = db.rawQuery(sqlAudio, null);

            while(cursorAudios.moveToNext()) {
                AudioNotes audioNotes = new AudioNotes();
                audioNotes.setName(cursorAudios.getString(1));
                audioNotes.setRoute(cursorAudios.getString(0));
                audios.add(audioNotes);
            }
*/
            System.out.println(cursor.getCount());
            //  int id = cursor.getInt(0);
            //    CuentosRV cRV = new CuentosRV();
            //CuentosRV cRV = new CuentosRV(imgCuentos[cursor.getInt(1)],cursor.getString(0));
            /*
            NotesModels n = new NotesModels();
            n.setTitle(cursor.getString(0));
            n.setMic(cursor.getInt(1));
            n.setDate(cursor.getString(2));
            n.setType(cursor.getString(3));
            n.setContent(cursor.getString(4));


            String t = cursor.getString(0);
            int mic = cursor.getInt(1);
            String date = cursor.getString(2);

            System.out.println(t);
            System.out.println(mic);
            System.out.println(date);
            System.out.println("@|||||1444444444444444444444444444444444444444");

            notesmodels.add(n);
*/
            NotesModels n = new NotesModels();


            int t = cursor.getInt(0);
            String title=cursor.getString(1);
            String date = cursor.getString(2);
            int mic =cursor.getInt(3);
            String noteche=cursor.getString(4);
            String content =cursor.getString(5);

            System.out.println(String.valueOf(t));
            System.out.println(title);
            System.out.println(date);
            System.out.println(String.valueOf(mic));
            System.out.println(String.valueOf(noteche));
            System.out.println(content);

            n.setId(cursor.getInt(0));
            n.setTitle(cursor.getString(1));
            n.setMic(cursor.getInt(2));
            n.setDate(cursor.getString(3));
            n.setCategories(cursor.getString(4));
            n.setContent(cursor.getString(5));
            n.setNoteChecklist(cursor.getInt(6));
           // n.setAudios(audios);

            notesmodeloMain.add(n);
        }
        //Log.i("CONTACTos", "Array de la base de datos" + misCumpl.toString());
        cursor.close();
        db.close();
        db2.close();
    }

    public void deleteNotes(ConexionSQLite sq,int id) {

        SQLiteDatabase db2 =sq.getWritableDatabase();

         db2.execSQL("DELETE FROM AUDIOFILES where IDAUDIOFILES ="+id+";");
         db2.execSQL("DELETE FROM CATEGORIES where CATID ="+id+";");
         db2.execSQL("DELETE FROM CHECKLISTCONTENT where IDCHECKLISTCONTENT ="+id+";");
         db2.execSQL("DELETE FROM NOTESCONTENT WHERE IDNOTESCONTENT ="+id+";");
         db2.execSQL("DELETE FROM NOTESMAIN WHERE IDNOTESMAIN ="+id+";");
         db2.execSQL("DELETE FROM EDITTEXT WHERE IDEDITTEXT ="+id+";");

         db2.close();
    }
    public void consultFromSQLiteChecklist(ConexionSQLite sq) {

        SQLiteDatabase db = sq.getReadableDatabase();
        SQLiteDatabase db2 =sq.getWritableDatabase();

       //  db2.execSQL("DELETE FROM NOTESMAIN");
       // db2.execSQL("DELETE FROM AUDIOFILES");
       //  db2.execSQL("DELETE FROM CATEGORIES");
       //  db2.execSQL("DELETE FROM CHECKLISTCONTENT");
       //  db2.execSQL("DELETE FROM NOTESCONTENT");

        //if ( insertAcplishCheckList){
          //  notesmodels.removeAll(notesmodels);
        //    insertAcplishCheckList= false;
       // }

      // notesModelsArrayList.removeAll(notesModelsArrayList);
        ArrayList<NotesModels> kukuruku=new ArrayList<>();
        kukuruku.removeAll(kukuruku);

        String sql="SELECT N.IDNOTESMAIN,N.TITLE,N.MIC,N.DATENOTE,C.TYPE,N.NOTECHECKLIST FROM categories C JOIN NOTESMAIN N ON N.IDNOTESMAIN=C.CATIDNOTEMAIN JOIN CHECKLISTCONTENT CH ON CH.IDCHECKLISTCONTENT=N.IDNOTESMAIN GROUP BY N.IDNOTESMAIN;";
       // String sqlChecklist = "SELECT CH.CONTENT,CH.CONTENTBOOLEAN FROM CHECKLISTCONTENT CH JOIN NOTESMAIN N ON CH.IDCHECKLISTCONTENT=N.IDNOTESMAIN WHERE N.IDNOTESMAIN =" + cursor.getInt(0) + ";";
        Cursor cursor = db.rawQuery(sql, null,null);
        System.out.println("esdrfdsrfdfgdfgkhñol`dfgkhloñfd");
        System.out.println(cursor.getCount());

        while (cursor.moveToNext()) {

            checkLists.removeAll(checkLists);
            //En cada nota se mete las listas de checklist correspondientes !!!
            String sqlChecklist = "SELECT CH.CONTENT,CH.CONTENTBOOLEAN FROM CHECKLISTCONTENT CH JOIN NOTESMAIN N ON CH.IDCHECKLISTCONTENT=N.IDNOTESMAIN WHERE N.IDNOTESMAIN =" + cursor.getInt(0) + ";";
            android.database.Cursor cursorCk = db.rawQuery(sqlChecklist, null);
            System.out.println("#############################################################");
            System.out.println("Esta es la checklist numero:  "+cursor.getInt(0));
            while (cursorCk.moveToNext()) {

                CheckListModel checkList = new CheckListModel();
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                checkList.setItem(cursorCk.getString(0));
               // item = (cursorCk.getString(0));
                System.out.println(checkList.getItem());
                checkList.setBooleano(cursorCk.getString(1));
               // booleano = (cursorCk.getString(1));
                System.out.println(checkList.getBooleano());
                checkLists.add(checkList);
            }

            NotesModels n = new NotesModels();

            System.out.println("Notes model!!!Imprimir");
            n.setId(cursor.getInt(0));
            System.out.println(n.getId());
            n.setTitle(cursor.getString(1));
            System.out.println(n.getTitle());
            n.setMic(cursor.getInt(2));
            System.out.println(n.getMic());
            n.setDate(cursor.getString(3));
            System.out.println(n.getDate());
            n.setCategories(cursor.getString(4));
            n.setNoteChecklist(cursor.getInt(5));
            //n.setChecklist(checkLists);

            for(int i=0;i<checkLists.size();i++) {
                CheckListModel checkList2 = new CheckListModel();
                checkList2.setItem(checkLists.get(i).getItem());
                checkList2.setBooleano(checkLists.get(i).getBooleano());
                n.addChecklist(checkList2);
            }

            // checkList2.setItem(item);
            // checkList2.setBooleano(booleano);
            System.out.println(n.getCategories());
            //n.setChecklist(checkLists);
            // n.addChecklist(checkList2);
            System.out.println("eoooooooooooooooooooo metete aqui amigo");
            System.out.println(n.getChecklist().toString());
            notesmodeloMain.add(n);
            //notesModelsArrayList.add(n);
        }
//
        /*
       for (int i = 0; i< notesModelsArrayList.size(); i++) {
           System.out.println("Marca agua");
            System.out.println(notesModelsArrayList.size());
            System.out.println(notesModelsArrayList.get(i).getId());
            System.out.println(notesModelsArrayList.get(i).getChecklist().toString());
        }

         */
        cursor.close();
        db.close();
        db2.close();

    }

    public void insertChecklistNotes(ConexionSQLite sq,String title,int mic,String type,ArrayList<CheckListModel> arrayList) {

        SQLiteDatabase db = sq.getReadableDatabase();
        SQLiteDatabase db2 = sq.getWritableDatabase();
/*
        db2.execSQL("delete from audiofiles");
        db2.execSQL("delete from categories");
        db2.execSQL("delete from checklistcontent");
        db2.execSQL("delete from notescontent");
        db2.execSQL("delete from notesmain");
*/

        //Auto notesmain PK.....

        Cursor cursorPk = db.rawQuery("select max(IDNOTESMAIN) from NOTESMAIN", null);
        while (cursorPk.moveToNext()) {
            notesPk = cursorPk.getInt(0) + 1;
            System.out.println(notesPk);
        }

        //para las notas 1 para los checklist 0
        String sql = " INSERT INTO NOTESMAIN  VALUES (" + notesPk + "," + "'" + title + "'" + ", date('now'),"+mic_On_Off+",1);" +
                "        INSERT INTO categories (catidnotemain,CATID,TYPE) VALUES (" + notesPk + "," + notesPk + "," + "'" + type + "'" + ");" ;

        String[] qsl = sql.split(";");
        for (int i = 0; i < qsl.length; i++) {
            System.out.println(qsl[i]);
            db2.execSQL(qsl[i]);
        }

        //Auto Checklist Content Pk...
        Cursor cursorCheckListPk = db.rawQuery("select max(IDCHECKLISTCONTENTPK) FROM CHECKLISTCONTENT", null);
        while (cursorCheckListPk.moveToNext()){
            checkListPK = cursorCheckListPk.getInt(0)+1;
            System.out.println("*****************"+checkListPK);
        }

        System.out.println("tamaño del array amigos!!!!"+arrayList.size());
        for (int i=0;i<arrayList.size();i++){
            System.out.println("init init init"+checkListPK);
            System.out.println("la pk de notes"+notesPk);
            String content =arrayList.get(i).getItem();
            String contentBoolean = arrayList.get(i).getBooleano();
            System.out.println("ContenidoLista: "+content);
            System.out.println("Boolean: "+contentBoolean);
            System.out.println(" INSERT INTO CHECKLISTCONTENT (IDCHECKLISTCONTENTPK,IDCHECKLISTCONTENT,CONTENT,CONTENTBOOLEAN) VALUES ("+checkListPK+"," + notesPk + "," + "'" + content+ "'"+",'" + contentBoolean + "'"+");");
            db2.execSQL( " INSERT INTO CHECKLISTCONTENT (IDCHECKLISTCONTENTPK,IDCHECKLISTCONTENT,CONTENT,CONTENTBOOLEAN) VALUES ("+checkListPK+"," + notesPk + "," + "'" + content+ "'"+",'" + contentBoolean + "'"+");");
            System.out.println("zzzzzzzzzzzzzzzzz"+checkListPK);
            checkListPK++;
        }

        db2.close();
        cursorPk.close();
        cursorCheckListPk.close();
        db.close();
    }

    public void insertContentNotes(ConexionSQLite sq, String title, int mic, String type, String content, ArrayList<String>arrayList) {

        SQLiteDatabase db = sq.getReadableDatabase();
        SQLiteDatabase db2 = sq.getWritableDatabase();

/*
        db2.execSQL("delete from audiofiles");
        db2.execSQL("delete from categories");
        db2.execSQL("delete from checklistcontent");
        db2.execSQL("delete from notescontent");
        db2.execSQL("delete from notesmain");
*/
        //Auto notesmain PK.....
        Cursor cursorPk = db.rawQuery("select max(idnotesmain) from notesmain", null);

        while (cursorPk.moveToNext()) {
            notesPk = cursorPk.getInt(0) + 1;
            System.out.println(notesPk);
        }

        if (arrayList.isEmpty()) {
            mic_On_Off = 0;
        }else if (!arrayList.isEmpty()){
            mic_On_Off= 1;
        }
        System.out.println(arrayList.toString());
        String sql = " INSERT INTO NOTESMAIN  VALUES (" + notesPk + "," + "'" + title + "'" + ", date('now'),"+mic_On_Off+",0);" +
                "        INSERT INTO categories (catidnotemain,CATID,TYPE) VALUES (" + notesPk + "," + notesPk + "," + "'" + type + "'" + ");" +
                "        INSERT INTO notescontent (CONTENT,IDNOTESCONTENT,IDNOTEMAINNC) VALUES (" + "'" + content + "'" + "," + notesPk + "," + notesPk + ");";

       String[] qsl = sql.split(";");
       for (int i = 0; i < qsl.length; i++) {
           db2.execSQL(qsl[i]);
           System.out.println(qsl[i]);
        }

            /*
            String sql = " INSERT INTO NOTESMAIN  VALUES (1,"+"'" + title +"'"+ ", date('now'),1,0);" +
                    "        INSERT INTO categories (catidnotemain,CATID,TYPE) VALUES (1,1,"+"'" + type +"'"+ ");\n" +
                    "        INSERT INTO notescontent (CONTENT,IDNOTESCONTENT) VALUES ("+"'"+ content +"'"+ ",1);\n" +
                    "        INSERT INTO AUDIOFILES (idaudiofilespk,AUDIONAME,IDAUDIOFILES,ROUTE) VALUES (1,NULL,1,NULL);";
            System.out.println("ewwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwt");
            //pARECE SER QUE CON QUE SEAN PRIMARY KEY NO HAY QUE PONERLE MAS TU TIA EL AUTOINCREMENT LO HACE SOLO ASI QUE GUAY.....

            String[] qsl = sql.split(";");
            for (int i = 0; i < qsl.length; i++) {
                db2.execSQL(qsl[i]);
            }
        }
        /*

          if (cursor.getCount() > 0) {


            Cursor cursorPk = db.rawQuery("select max(idnotesmain) from notesmain", null);
            while (cursorPk.moveToNext()) {
                notesPk = cursorPk.getInt(0) + 1;
                System.out.println(notesPk);
            }
            String sql = " INSERT INTO NOTESMAIN  VALUES (" + notesPk + "," + "'" + title + "'" + ", date('now'),1,0);" +
                    "        INSERT INTO categories (catidnotemain,CATID,TYPE) VALUES (" + notesPk + "," + notesPk + "," + "'" + type + "'" + ");\n" +
                    "        INSERT INTO notescontent (CONTENT,IDNOTESCONTENT) VALUES (" + "'" + content + "'" + "," + notesPk + ";" +
                    "        INSERT INTO AUDIOFILES (idaudiofilespk,AUDIONAME,IDAUDIOFILES,ROUTE) VALUES (2,'mp3dos'," + notesPk + ",'la ruta de donde se guarda');" +
                    "INSERT INTO AUDIOFILES (idaudiofilespk,AUDIONAME,IDAUDIOFILES,ROUTE) VALUES (3,'mp3tres'," + notesPk + ",'la ruta de donde se guarda3');";
            System.out.println("ewwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwt");
            //pARECE SER QUE CON QUE SEAN PRIMARY KEY NO HAY QUE PONERLE MAS TU TIA EL AUTOINCREMENT LO HACE SOLO ASI QUE GUAY.....

            String[] qsl = sql.split(";");
            for (int i = 0; i < qsl.length; i++) {
                db2.execSQL(qsl[i]);

            }
        }
             *

           // }
            /*
            if (cursor.getCount()>0){
                Cursor cursorid = db.rawQuery("SELECT MAX(IDNOTESMAIN) FROM NOTESMAIN", null);
                while(cursorid.moveToNext()) {
                    checkId = cursorid.getInt(0);
                    String sql2 =" INSERT INTO NOTESMAIN (IDNOTESMAIN,TITLE,MIC,DATENOTE,NOTECHECKLIST) VALUES ("+checkId+1+","+title+","+mic+", date('now'),0);\n" +
                            "        INSERT INTO categories  VALUES ("+checkId+1+",'"+type+"');\n" +
                            "        INSERT INTO notescontent  VALUES ("+checkId+1+","+content+");\n" +
                            "        INSERT INTO AUDIOFILES  VALUES (NULL,\"+checkId+1+\",\"+checkId+1+\",NULL);";

                    String [] qsl2 = sql2.split(";");
                    for (int i=0;i<qsl2.length;i++) {
                        db2.execSQL(qsl2[i]);
                    }
                }
                */
        //Auto Checklist Audio Pk...
        Cursor cursorAudioListPk = db.rawQuery("select max(IDAUDIOFILESPK) FROM AUDIOFILES", null);
        while (cursorAudioListPk.moveToNext()){
            audioFilesPk = cursorAudioListPk.getInt(0)+1;
            System.out.println("*****************"+audioFilesPk);
        }
        if (arrayList!=null) {
            mic_On_Off =1;
            System.out.println("tamaño del array amigos!!!!" + arrayList.size());
            for (int i = 0; i < arrayList.size(); i++) {
                System.out.println("init init init" + checkListPK);
                System.out.println("la pk de notes" + notesPk);
                String audis = arrayList.get(i);
                String routes = "";
                //String contentBoolean = arrayList.get(i).getBooleano();
                //System.out.println("ContenidoLista: "+content);
                //System.out.println("Boolean: "+contentBoolean);
                System.out.println(" INSERT INTO AUDIOFILES (IDCHECKLISTCONTENTPK,IDCHECKLISTCONTENT,CONTENT,CONTENTBOOLEAN) VALUES (" + checkListPK + "," + notesPk + "," + "'" + routes + "'" + ",'" + audis + "'" + ");");
                db2.execSQL(" INSERT INTO AUDIOFILES (IDAUDIOFILESPK,IDAUDIOFILES,ROUTE,AUDIONAME) VALUES (" + audioFilesPk + "," + notesPk + "," + "'" + routes + "'" + ",'" + audis + "'" + ");");
                System.out.println("zzzzzzzzzzzzzzzzz" + audioFilesPk);
                audioFilesPk++;
            }
        }
        db2.close();
        cursorAudioListPk.close();
        cursorPk.close();
        db.close();
    }

    public void insertFormatText(ConexionSQLite sq,String editStyle,int SStart,int SEnd) {

        SQLiteDatabase db = sq.getReadableDatabase();
        SQLiteDatabase db2 = sq.getWritableDatabase();

        //Auto notesmain PK.....
        android.database.Cursor cursorPk = db.rawQuery("select max(IDNOTESMAIN) from NOTESMAIN", null);
        while (cursorPk.moveToNext()) {
            notesPk = cursorPk.getInt(0) + 1;
            System.out.println(notesPk);
        }
        //Auto EDITTEXT Content Pk...
        android.database.Cursor cursorCheckListPk = db.rawQuery("select max(IDEDITTEXTPK) FROM EDITTEXT", null);
        while (cursorCheckListPk.moveToNext()){
            checkListPK = cursorCheckListPk.getInt(0)+1;
            System.out.println("*****************"+checkListPK);
        }

        //para las notas 1 para los checklist 0
        String sql = " INSERT INTO EDITTEXT  VALUES (" + checkListPK + ", " + notesPk+ "," +"'" + editStyle +"'"+","+SStart+"," +SEnd+");";

            db2.execSQL(sql);

            db.close();
            db2.close();
    }

    public void updateFormatText(ConexionSQLite sq,int id,String editStyle,int SStart,int SEnd) {

     SQLiteDatabase db2 = sq.getWritableDatabase();

        //para las notas 1 para los checklist 0
        String sql = " UPDATE EDITTEXT  SET EDITSTYLE ="+"'" + editStyle +"'"+", SELECTIONSTART ="+SStart+", SELECTIONEND ="+SEnd+" WHERE IDEDITTEXT ="+"'"+id+"'";
        db2.execSQL(sql);
        db2.close();
    }

    public void buttonFetch(ConexionSQLite sq, EditText editText) {

        SQLiteDatabase db = sq.getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("select IDEDITTEXT,EDITSTYLE,SELECTIONSTART,SELECTIONEND FROM EDITTEXT E JOIN NOTESMAIN N ON E.IDEDITTEXT=N.IDNOTESMAIN group by E.IDEDITTEXT;", null);
        String stringSQLQuery = "Select * from EditedTextAttributes";

        try {
            cursor.moveToFirst();
            Spannable spannableString = new SpannableStringBuilder(editText.getText());

            while (!cursor.isNull(0)){
                int intSelectionStart = cursor.getInt(2);
                int intSelectionEnd = cursor.getInt(3);

                switch(cursor.getString(1)){
                    case "BOLD":
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD),
                                intSelectionStart, intSelectionEnd,
                                0);
                        editText.setText(spannableString);
                        break;
                    case "CENTER":
                        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        editText.setText(spannableString);
                        break;
                    case "RIGHT":
                        editText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                        editText.setText(spannableString);
                        break;
                    case "LEFT":
                        editText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                        editText.setText(spannableString);
                        break;
                    case "ITALIC":
                        spannableString.setSpan(new StyleSpan(Typeface.ITALIC),
                                intSelectionStart, intSelectionEnd,
                                0);
                        editText.setText(spannableString);
                        break;
                    case "UNDERLINE":
                        spannableString.setSpan(new UnderlineSpan(),
                                intSelectionStart, intSelectionEnd,
                                0);
                        editText.setText(spannableString);
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

    public void getArraysAudioContentNotes(ConexionSQLite sq,int id) {

        SQLiteDatabase db = sq.getReadableDatabase();


        //  db2.execSQL("DELETE FROM NOTESMAIN");
        // db2.execSQL("DELETE FROM AUDIOFILES");
        //  db2.execSQL("DELETE FROM CATEGORIES");
        //  db2.execSQL("DELETE FROM CHECKLISTCONTENT");
        //  db2.execSQL("DELETE FROM NOTESCONTENT");

        //if ( insertAcplishCheckList){
        //  notesmodels.removeAll(notesmodels);
        //    insertAcplishCheckList= false;
        // }

        // notesModelsArrayList.removeAll(notesModelsArrayList);

            //En cada nota se mete las listas de checklist correspondientes !!!
            String sqlChecklist = "SELECT A.ROUTE,A.AUDIONAME FROM AUDIOFILES A  JOIN NOTESMAIN N ON A.IDAUDIOFILES=N.IDNOTESMAIN WHERE N.IDNOTESMAIN =" + id +" GROUP BY A.AUDIONAME ;";
            android.database.Cursor cursorCk = db.rawQuery(sqlChecklist, null);
            System.out.println("#############################################################");
            while (cursorCk.moveToNext()) {

                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                AudioNotes audioNotes = new AudioNotes();
                String route = (cursorCk.getString(0));
                // item = (cursorCk.getString(0));
                System.out.println(route);
                String audioname = (cursorCk.getString(1));
                // booleano = (cursorCk.getString(1));
                //audioNotes.setRoute(route);
                //audioNotes.setName(audioname);
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+audioname);
                //kukuruku.add(route);
                kukuruku.add(audioname);
            }

        cursorCk.close();
        db.close();
    }

    public void getArrayCheckListEdit(ConexionSQLite sq,int id) {

        SQLiteDatabase db = sq.getReadableDatabase();

        checkListEditArray.removeAll(checkListEditArray);
        String sqlChecklist = "SELECT CH.CONTENT,CH.CONTENTBOOLEAN FROM CHECKLISTCONTENT CH JOIN NOTESMAIN N ON CH.IDCHECKLISTCONTENT=N.IDNOTESMAIN WHERE N.IDNOTESMAIN =" + id+ ";";
        android.database.Cursor cursorCk = db.rawQuery(sqlChecklist, null);

        while (cursorCk.moveToNext()) {

            CheckListModel checkList = new CheckListModel();
            checkList.setItem(cursorCk.getString(0));
            System.out.println(checkList.getItem());
            checkList.setBooleano(cursorCk.getString(1));
            System.out.println(checkList.getBooleano());
            checkListEditArray.add(checkList);
        }
        cursorCk.close();
        db.close();
    }

    public void updateContentNotes(ConexionSQLite sq,int id, String title,String content,ArrayList<String>arrayList) {

        SQLiteDatabase db2 = sq.getWritableDatabase();
        SQLiteDatabase db =sq.getReadableDatabase();
        int audioFilesPkUpdate=0;
        if (!arrayList.isEmpty()){
            mic_On_Off =1;
        }else if (arrayList.isEmpty()) {
            mic_On_Off =0;
        }
        //Da error por las apostrofes!! cambiar eso!!!

        String sql = " UPDATE NOTESMAIN SET TITLE = "+"'" + title +"'"+",  DATENOTE = date('now'), MIC ="+mic_On_Off+" WHERE IDNOTESMAIN = "+"'"+id+"'"+";"+
                " UPDATE CATEGORIES SET TYPE =" +"'" + title + "'" + "WHERE CATID = "+"'"+id+"'"+";"+
                "  UPDATE NOTESCONTENT SET CONTENT = " + "'" + content + "'" +"WHERE IDNOTESCONTENT = "+"'"+id+"'"+";";

        String[] qsl = sql.split(";");
        for (int i = 0; i < qsl.length; i++) {
            db2.execSQL(qsl[i]);
            System.out.println(qsl[i]);
        }

        Cursor cursorAudioListPk = db.rawQuery("select max(IDAUDIOFILESPK) FROM AUDIOFILES", null);
        while (cursorAudioListPk.moveToNext()){
             audioFilesPkUpdate = cursorAudioListPk.getInt(0)+1;
            System.out.println("*****************"+audioFilesPk);
        }
        if (arrayList!=null) {
            mic_On_Off =1;
            System.out.println("tamaño del array amigos!!!!" + arrayList.size());
            for (int i = 0; i < arrayList.size(); i++) {

                String audis = arrayList.get(i);
                String routes = "";
                //String contentBoolean = arrayList.get(i).getBooleano();
                //System.out.println("ContenidoLista: "+content);
                //System.out.println("Boolean: "+contentBoolean);
                System.out.println(" INSERT INTO AUDIOFILES (IDCHECKLISTCONTENTPK,IDCHECKLISTCONTENT,CONTENT,CONTENTBOOLEAN) VALUES (" +audioFilesPkUpdate + "," + id + "," + "'" + routes + "'" + ",'" + audis + "'" + ");");
                db2.execSQL(" INSERT INTO AUDIOFILES (IDAUDIOFILESPK,IDAUDIOFILES,ROUTE,AUDIONAME) VALUES (" + audioFilesPkUpdate + "," + id + "," + "'" + routes + "'" + ",'" + audis + "'" + ");");
                System.out.println("zzzzzzzzzzzzzzzzz" + audioFilesPkUpdate);
                audioFilesPkUpdate++;
            }
        }
        /*
        Un update en este caso no va a funcionar ya que claro me va pisando los registros yo creo el nombre por eso solo sale uno todo el rato!!!!
        if (arrayList!=null) {
            System.out.println("tamaño del array amigos!!!!" + arrayList.size());
            for (int i = 0; i < arrayList.size(); i++) {
                String audis = arrayList.get(i);
                String routes = "";
                db2.execSQL(" UPDATE AUDIOFILES SET AUDIONAME ="+"'" + audis + "'"+"WHERE IDAUDIOFILES ="+"'"+id+"'"+";");
                System.out.println(" UPDATE AUDIOFILES SET AUDIONAME ="+"'" + audis + "'"+"WHERE IDAUDIOFILES ="+"'"+id+"'"+";");
            }
        }

         */
        cursorAudioListPk.close();
        db2.close();
        db.close();
    }

}



