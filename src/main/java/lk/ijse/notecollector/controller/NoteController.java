package lk.ijse.notecollector.controller;

import lk.ijse.notecollector.customStatusCode.SelectedUserAndNoteErrorStatus;
import lk.ijse.notecollector.dto.NoteStatus;
import lk.ijse.notecollector.dto.impl.NoteDTO;
import lk.ijse.notecollector.exception.DataPersistException;
import lk.ijse.notecollector.exception.NoteNotFoundException;
import lk.ijse.notecollector.exception.UserNotFoundException;
import lk.ijse.notecollector.service.NoteService;
import lk.ijse.notecollector.util.RegexProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/notes") /* api-api end point ekak, v1-version, notes-url eka resolve vena class eke name */
public class NoteController {
    @Autowired /* dependency inject karana annotation eka */
    private NoteService noteService;/* Mema thama service eka controller ekata inject karanne. */
    /* Me karala thiyenne dependency injection.
        (mema kalama loose couple walatai architecture eka pahadiliwa thiyagannai puluwan.
        Habai dependency inject karanna one run time.) */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveNote(@RequestBody NoteDTO noteDTO) { /* @RequestBody - meken kiyanne request eke body eke ena NoteDTO eka alla ganna */
        try {
            noteService.saveNote(noteDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{noteID}",produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteStatus getSelectedNote(@PathVariable ("noteID") String noteId) {
        if (!RegexProcess.noteIdMatcher(noteId)) {
            return new SelectedUserAndNoteErrorStatus(1,"Note ID is not valid");
        }
        return noteService.getNote(noteId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NoteDTO> getALlNotes(){
        return noteService.getAllNotes();
    }

    @DeleteMapping(value = "/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable("noteId") String noteId) {
        try {
            if (!RegexProcess.noteIdMatcher(noteId)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            noteService.deleteNote(noteId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (UserNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(value = "/{noteId}")
    public ResponseEntity<Void> updateNote(@PathVariable ("noteId") String noteId, @RequestBody NoteDTO updatedNoteDTO) {
        noteService.updateNote(noteId, updatedNoteDTO);
        //validations
        try {
            if (!RegexProcess.noteIdMatcher(noteId) || updatedNoteDTO==null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            noteService.updateNote(noteId,updatedNoteDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoteNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
