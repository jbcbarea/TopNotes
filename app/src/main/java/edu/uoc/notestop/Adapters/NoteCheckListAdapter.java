package edu.uoc.notestop.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.uoc.notestop.Fragments.CheckListFragment;
import edu.uoc.notestop.Fragments.NotesFragment;
import edu.uoc.notestop.VoiceRecorder;


//Adaptador para cambiar entre fragmentos
public class NoteCheckListAdapter extends FragmentPagerAdapter {


    private int caseS;
    private Context context;
    int totalTabs;
    VoiceRecorder voiceRecorder= new VoiceRecorder();



    public NoteCheckListAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }


    @Override
    public Fragment getItem(int position) {

        System.out.println("EOOOOOOOOOOOOOOOOOO"+ voiceRecorder.noteOrcK2);
        switch (position) {
            case 0:
              //  if ( voiceRecorder.noteOrcK2 ==0) {
                    NotesFragment notesFragment = new NotesFragment();
                    return notesFragment;

               // }else if( voiceRecorder.noteOrcK2 ==1){
                   // CheckListFragment checkListFragment = new CheckListFragment();
                   // return checkListFragment;
                 //       }
            case 1:

               // if ( voiceRecorder.noteOrcK2 ==1) {
               ///     NotesFragment notesFragment = new NotesFragment();
                 //   return notesFragment;

              //  }else if( voiceRecorder.noteOrcK2 ==0){
                    CheckListFragment checkListFragment = new CheckListFragment();
                    return checkListFragment;
               // }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}

