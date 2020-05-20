package com.samkakria.notesv1.Persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.samkakria.notesv1.Models.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    long[] insertNotes(Note... notes);

    @Query("Select * FROM notes")
    LiveData<List<Note>> getNotes();

    @Query("Select * FROM notes where title LIKE :title")
    List<Note> getNotesWithCustomQuery(String title);

    @Delete
    int delete(Note... notes);

    @Update
    int update(Note... notes);

}
