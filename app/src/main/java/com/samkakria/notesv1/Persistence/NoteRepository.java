package com.samkakria.notesv1.Persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.samkakria.notesv1.Models.Note;
import com.samkakria.notesv1.async.DeleteAsyncTask;
import com.samkakria.notesv1.async.InsertAsyncTask;
import com.samkakria.notesv1.async.UpdateAsyncTask;

import java.util.List;

public class NoteRepository {

    private NoteDatabase mNoteDataBase;

    public NoteRepository(Context context) {
        this.mNoteDataBase = NoteDatabase.getInstance(context);
    }

    public void insertNoteTask(Note note){
        new InsertAsyncTask(mNoteDataBase.getNoteDao()).execute(note);
    }

    public void updateNote(Note note){
        new UpdateAsyncTask(mNoteDataBase.getNoteDao()).execute(note);
    }

    public LiveData<List<Note>> retrieveNotesTask(){
        return mNoteDataBase.getNoteDao().getNotes();
    }

    public void deleteNote(Note note){
        new DeleteAsyncTask(mNoteDataBase.getNoteDao()).execute(note);

    }
}
