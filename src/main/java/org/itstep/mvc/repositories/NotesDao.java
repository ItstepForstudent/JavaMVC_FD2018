package org.itstep.mvc.repositories;

import org.itstep.mvc.entities.Note;

import java.util.List;

public interface NotesDao {
    List<Note> getNotes(String userId);
    void addNote(Note note);
    void delNote(String noteId);
}
