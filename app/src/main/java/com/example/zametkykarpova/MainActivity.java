package com.example.zametkykarpova;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE_ID = "note_id";

    ArrayAdapter<Note> adp;
    int sel;
    long noteId;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adp = new ArrayAdapter<Note>(  this, android.R.layout.simple_list_item_1);

        ListView lst = findViewById(R.id.lst_notes);
        lst.setAdapter(adp);
        noteId = getIntent().getLongExtra(EXTRA_NOTE_ID, -1);


        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sel = position;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){

        if (data != null) {
            int pos = data.getIntExtra(  "my-note-index",   -1);
            String title = data.getStringExtra("my-note-title");
            String content = data.getStringExtra("my-note-content");

            Note n = adp.getItem(pos);
            n.title = title;
            n.content = content;

            adp.notifyDataSetChanged();

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void on_new_click(View v)
    {
        Note n = new Note();
        n.title = "New note";
        n.content = "Some content";

        adp.add(n);
        int pos = adp.getPosition(n);

        Intent i = new Intent(this, MainActivity2.class);
        i.putExtra("my-note-index", pos);
        i.putExtra("my-note-title", n.title);
        i.putExtra("my-note-content", n.content);

        startActivityForResult(i, 12345);

    }

    public void on_edit_click(View v)
    {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(MainActivity.EXTRA_NOTE_ID, noteId);
        startActivity(i);
    }

    public void on_delete_click(View v)
    {



    }

}