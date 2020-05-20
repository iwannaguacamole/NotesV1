package com.samkakria.notesv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.samkakria.notesv1.Adapter.NotesRecyclerAdapter;
import com.samkakria.notesv1.Models.Note;
import com.samkakria.notesv1.Persistence.NoteRepository;
import com.samkakria.notesv1.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity implements NotesRecyclerAdapter.OnNoteListener, FloatingActionButton.OnClickListener {

    private RecyclerView mRecyclerview;

    private ArrayList<Note> mNotes = new ArrayList<>();
    private NotesRecyclerAdapter mNoteRecyclerAdapter;
    private NoteRepository mNoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerview = findViewById(R.id.recycler_view);
        mNoteRepository = new NoteRepository(this);

        findViewById(R.id.fab).setOnClickListener(this);

        initRecyclerView();
        //insertFakeNotes();
        retrieveNotes();

        setSupportActionBar((Toolbar) findViewById(R.id.notes_toolbar));
        setTitle("Notes");
    }

    private void retrieveNotes() {
        mNoteRepository.retrieveNotesTask().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if (notes.size() > 0) {
                    mNotes.clear();
                }

                mNotes.addAll(notes);
                mNoteRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void insertFakeNotes() {
        for (int i = 0; i < 1000; i++) {
            Note note = new Note();
            note.setTitle("Title #" + i);
            note.setContent("Content is " + i);
            note.setTimeStamp("January 2019");
            mNotes.add(note);
        }
        mNoteRecyclerAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerview.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(mRecyclerview);
        mNoteRecyclerAdapter = new NotesRecyclerAdapter(mNotes, this);
        mRecyclerview.setAdapter(mNoteRecyclerAdapter);
    }

    @Override
    public void onNoteClick(int position) {
        Toast.makeText(this, "We clicked on note# "
                + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("selected_note", mNotes.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }

    private void deleteNote(Note note) {
        mNotes.remove(note);
        mNoteRecyclerAdapter.notifyDataSetChanged();
        mNoteRepository.deleteNote(note);
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));
        }
    };
}
