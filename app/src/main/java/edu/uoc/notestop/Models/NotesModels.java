package edu.uoc.notestop.Models;

import java.util.ArrayList;

public class NotesModels {
    String categories;
    String title;

    public ArrayList<AudioNotes> getAudioNotes() {
        return audioNotes;
    }

    public void setAudioNotes(ArrayList<AudioNotes> audioNotes) {
        this.audioNotes = audioNotes;
    }

    ArrayList<AudioNotes> audioNotes=new ArrayList<>();
    ArrayList<CheckListModel> checklist = new ArrayList<>();
    String content;
    String type;
    String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;

    public int getNotechecklist() {
        return notechecklist;
    }

    public void setNotechecklist(int notechecklist) {
        this.notechecklist = notechecklist;
    }

    int notechecklist;


    public NotesModels(int id,String categories, String title, ArrayList<CheckListModel> checkList, String date, int mic, int noteChecklist) {
        this.id=id;
        this.categories = categories;
        this.title = title;
        this.checklist = checkList;
        this.date = date;
        this.mic = mic;
        this.noteChecklist = noteChecklist;
    }

    public NotesModels() {
    }

    public ArrayList<CheckListModel> getChecklist() {
        return checklist;
    }

    public void addChecklist(CheckListModel checkList) {
        checklist.add(checkList);
    }

    public void setChecklist(ArrayList<CheckListModel> checklist) {
        this.checklist = checklist;
    }

    public NotesModels(String categories, String title, ArrayList<AudioNotes> audioNotes, String content, String date, int mic, int noteChecklist, String type) {
        this.categories = categories;
        this.title = title;
        this.audioNotes = audioNotes;
        this.content = content;
        this.date = date;
        this.mic = mic;
        this.noteChecklist = noteChecklist;
        this.type=type;
    }

    int mic;
    int noteChecklist;
    public void addAudios(AudioNotes audi) {
        audioNotes.add(audi);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<AudioNotes> getAudios() {
        return audioNotes;
    }

    public void setAudios(ArrayList<AudioNotes> audios) {
        this.audioNotes = audios;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMic() {
        return mic;
    }

    public void setMic(int mic) {
        this.mic = mic;
    }

    public int getNoteChecklist() {
        return noteChecklist;
    }

    public void setNoteChecklist(int noteChecklist) {
        this.noteChecklist = noteChecklist;
    }

    @Override
    public String toString() {
        return "NotesModels{" +
                "categories='" + categories + '\'' +
                ", title='" + title + '\'' +
                ", audios=" + audioNotes.toString() +
                ", checklist=" + checklist +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", notechecklist=" + notechecklist +
                ", mic=" + mic +
                ", noteChecklist=" + noteChecklist +
                '}';
    }

    public NotesModels(String categories, String title, String content, String date, int mic, int noteChecklist, String type) {
        this.categories = categories;
        this.title = title;
        this.content = content;
        this.date = date;
        this.mic = mic;
        this.noteChecklist = noteChecklist;
        this.type=type;
    }
}
