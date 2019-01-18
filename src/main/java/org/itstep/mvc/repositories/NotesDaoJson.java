package org.itstep.mvc.repositories;

import org.itstep.mvc.core.annotation.Component;
import org.itstep.mvc.entities.Note;

import java.util.List;

@Component
public class NotesDaoJson implements NotesDao {
    @Override
    public List<Note> getNotes(String userId) {
        return null;
    }

    @Override
    public void addNote(Note note) {

    }

    @Override
    public void delNote(String noteId) {

    }
}
