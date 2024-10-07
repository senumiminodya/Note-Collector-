package lk.ijse.notecollector.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.notecollector.customStatusCode.SelectedUserAndNoteErrorStatus;
import lk.ijse.notecollector.dao.NoteDAO;
import lk.ijse.notecollector.dto.NoteStatus;
import lk.ijse.notecollector.dto.impl.NoteDTO;
import lk.ijse.notecollector.entity.impl.NoteEntity;
import lk.ijse.notecollector.exception.DataPersistException;
import lk.ijse.notecollector.exception.NoteNotFoundException;
import lk.ijse.notecollector.service.NoteService;
import lk.ijse.notecollector.util.AppUtil;
import lk.ijse.notecollector.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/* me layer eka athule thama business logic eka liyanne. */
/* controller layer eka service layer eka matha depend wenawa */

@Service /* Methenta @Annotation dannath puluwan. Meke service layer ekata adala functions godak ha */
@Transactional
public class NoteServiceIMPL implements NoteService {
    @Autowired
    private NoteDAO noteDao;
    @Autowired
    private Mapping noteMapping;

    @Override
    public void saveNote(NoteDTO noteDTO) {
        noteDTO.setNoteId(AppUtil.generateNoteId());
        NoteEntity savedNote =
                noteDao.save(noteMapping.toNoteEntity(noteDTO));
        if(savedNote == null){
            throw new DataPersistException("Note not saved");
        }
    }

    public List<NoteDTO> getAllNotes() {
        return noteMapping.asNoteDTOList(noteDao.findAll());
    }

    @Override
    public NoteStatus getNote(String noteId) {
        if(noteDao.existsById(noteId)){
            var selectedUser = noteDao.getReferenceById(noteId);
            return noteMapping.toNoteDTO(selectedUser);
        }else {
            return new SelectedUserAndNoteErrorStatus(2,"Selected note not found");
        }
    }

    @Override
    public void deleteNote(String noteId) {
        Optional<NoteEntity> existedUser = noteDao.findById(noteId);
        if (!existedUser.isPresent()) {
            throw new NoteNotFoundException("Note with id "+noteId+" not found!");
        }else {
            noteDao.deleteById(noteId);
        }
    }

    @Override
    public void updateNote(String noteId, NoteDTO noteDTO) {
        Optional<NoteEntity> findNote = noteDao.findById(noteId);
        if (!findNote.isPresent()){
            throw new NoteNotFoundException("Note not found!");
        } else {
            findNote.get().setNoteTitle(noteDTO.getNoteTitle());
            findNote.get().setNoteDesc(noteDTO.getNoteDesc());
            findNote.get().setCreatedDate(noteDTO.getCreatedDate());
            findNote.get().setPriorityLevel(noteDTO.getPriorityLevel());
        }
    }
}
