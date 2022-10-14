package edu.uoc.notestop;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import edu.uoc.notestop.Adapters.NoteCheckListAdapter;


public class AddNoteChecklist extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    NoteCheckListAdapter noteCheckListAdapter;
    VoiceRecorder voiceRecorder= new VoiceRecorder();

    @Override
    public void onBackPressed() {
       // Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //Here i would have to delete the audio file wich hasbeen created before, cause if don´t submitted i has to be deleted......

        VoiceRecorder voiceRecorder = new VoiceRecorder();
        if(VoiceRecorder.file!= null){
            voiceRecorder.file.delete();
        }

       // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       // startActivity(intent);
        finish();
        super.onBackPressed();
    }

    //Puedo crear dos variables le paso 1 0 cero si vengo de note o checklist para ir desde voice recorder...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_checklist);
        getSupportActionBar().hide();

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.view_pager);

      //  if(voiceRecorder.noteOrcK2 == 0){
            tabLayout.addTab(tabLayout.newTab().setText("Note+"));
            tabLayout.addTab(tabLayout.newTab().setText("CheckList+"));

       // }else{
          //  tabLayout.addTab(tabLayout.newTab().setText("CheckList+"));
          //  tabLayout.addTab(tabLayout.newTab().setText("Note+"));
       // }

        //tabLayout.addTab(tabLayout.newTab().setText("Note+"));
        //tabLayout.addTab(tabLayout.newTab().setText("CheckList+"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        noteCheckListAdapter = new NoteCheckListAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(noteCheckListAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {
                    noteCheckListAdapter.notifyDataSetChanged();
                }
                if (tab.getPosition() == 0) {
                    noteCheckListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        //Vamos a darle animación para que cambie entre derecha e izquierda entre los tablelayout
        tabLayout.setTranslationY(300);
        tabLayout.animate().translationY(0).setDuration(1000).setStartDelay(100).start();

        //mAuth = FirebaseAuth.getInstance();
/*
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loginUser("werty@gmail.com", "asdfghj");
            }

        });
*/
    }
}
