package edu.uoc.notestop.SQLDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//
public class ConexionSQLite extends SQLiteOpenHelper {

    final String CREATE_TABLE_NOTAS_MAIN = "CREATE TABLE NOTESMAIN ( IDNOTESMAIN INTEGER NOT NULL, TITLE VARCHAR2, DATENOTE DATE,MIC INTEGER,NOTECHECKLIST INTEGER, PRIMARY KEY (IDNOTESMAIN));";

    final String CREATE_TABLE_CATEGORIES = "CREATE TABLE CATEGORIES ( CATIDNOTEMAIN INTEGER NOT NULL PRIMARY KEY,CATID INTEGER UNIQUE, TYPE VARCHAR2, CONSTRAINT NOTES_FK FOREIGN KEY (CATID) REFERENCES NOTESMAIN (IDNOTESMAIN));";
    final String CREATE_TABLE_NOTESCONTENT = " CREATE TABLE NOTESCONTENT (\n" +
            "                       \n" +
            "                IDNOTEMAINNC INTEGER NOT NULL ,\n" +
            "                IDNOTESCONTENT INTEGER UNIQUE,\n" +
            "                CONTENT NVARCHAR,\n" +
            "                PRIMARY KEY (IDNOTEMAINNC),\n" +
            "                CONSTRAINT NOTESCONTENT_FK FOREIGN KEY (IDNOTESCONTENT)\n" +
            "                            REFERENCES NOTESMAIN (IDNOTESMAIN));";

    final String CREATE_TABLE_CHECKLISTCONTENT = "  CREATE TABLE CHECKLISTCONTENT (\n" +
            "                        IDCHECKLISTCONTENTPK INTEGER  NOT NULL,\n" +
            "                        IDCHECKLISTCONTENT INTEGER ,  \n" +
            "                        CONTENT VARCHAR2,\n" +
            "                        CONTENTBOOLEAN VARCHAR2,  \n" +
            "                        PRIMARY KEY (IDCHECKLISTCONTENTPK),\n" +
            "                        CONSTRAINT IDCHECKLIST_FK  FOREIGN KEY (IDCHECKLISTCONTENT)\n" +
            "                        REFERENCES NOTESMAIN (IDNOTESMAIN)); ";

    final String CREATE_TABLE_AUDIONOTES = " CREATE TABLE AUDIOFILES (\n" +
            "                       IDAUDIOFILESPK INTEGER NOT NULL,\n" +
            "                       IDAUDIOFILES INTEGER ,  \n" +
            "                       ROUTE VARCHAR2,\n" +
            "                       AUDIONAME VARCHAR2,  \n" +
            "                       PRIMARY KEY (IDAUDIOFILESPK),\n" +
            "                       CONSTRAINT IDAUDIO_FK FOREIGN KEY (IDAUDIOFILES)\n" +
            "                       REFERENCES NOTESMAIN (IDNOTESMAIN));   ";



    final String CREATE_TABLE_FORMATT = " CREATE TABLE EDITTEXT (\n" +
            "                       IDEDITTEXTPK INTEGER NOT NULL,\n" +
            "                       IDEDITTEXT INTEGER ,  \n" +
            "                       EDITSTYLE VARCHAR2,\n" +
            "                       SELECTIONSTART INTEGER,  \n" +
            "                       SELECTIONEND INTEGER,  \n" +
            "                       PRIMARY KEY (IDEDITTEXTPK),\n" +
            "                       CONSTRAINT IDAUDIO_FK FOREIGN KEY (IDEDITTEXT)\n" +
            "                       REFERENCES NOTESMAIN (IDNOTESMAIN));   ";

    //                                 contexto aplicacion , nombre dfe la base de datos, cursos , version
    public ConexionSQLite(@androidx.annotation.Nullable Context context, @androidx.annotation.Nullable String name, @androidx.annotation.Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Creara las tablas que tengamos de nuestras entidades ....
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_NOTAS_MAIN);
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_CHECKLISTCONTENT);
        db.execSQL(CREATE_TABLE_NOTESCONTENT);
        db.execSQL(CREATE_TABLE_AUDIONOTES);
        db.execSQL(CREATE_TABLE_FORMATT);
    }
    //Verifica si ya existe antes una version antigua y otro parametro para una version nueva
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Si existe que la borre y me vuelva a crear la tabla, base de datos.....
        // db.execSQL("DROP TABLE IF EXISTS CUENTOS");
        db.execSQL("DROP TABLE IF EXISTS AUDIOFILES  ");
        db.execSQL("DROP TABLE IF EXISTS CATEGORIES  ");
        db.execSQL("DROP TABLE IF EXISTS NOTESCONTENT  ");
        db.execSQL("DROP TABLE IF EXISTS CHECKLISTCONTENT  ");
        db.execSQL("DROP TABLE IF EXISTS NOTESMAIN ");
        db.execSQL("DROP TABLE IF EXISTS EDITTEXT");
        onCreate(db);

    }
}

