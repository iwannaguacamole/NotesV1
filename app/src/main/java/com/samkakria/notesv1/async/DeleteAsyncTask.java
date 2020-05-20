package com.samkakria.notesv1.async;

import android.os.AsyncTask;
import android.util.Log;

import com.samkakria.notesv1.Models.Note;
import com.samkakria.notesv1.Persistence.NoteDao;

public class DeleteAsyncTask extends AsyncTask<Note,Void, Void> {
    private static final String TAG = "InsertAsyncTask";

    private NoteDao mNoteDao;
    public DeleteAsyncTask(NoteDao dao){
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        Log.d(TAG,"doInBackgroundThread:"+Thread.currentThread().getName());
        mNoteDao.delete(notes);
        return null;
    }
}
