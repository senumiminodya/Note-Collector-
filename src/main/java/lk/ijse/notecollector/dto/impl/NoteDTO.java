package lk.ijse.notecollector.dto.impl;

import lk.ijse.notecollector.dto.NoteStatus;
import lk.ijse.notecollector.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* NoteDTO SuperDTO walin inherit kale seperation of concern walata. */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoteDTO implements NoteStatus {
    private String noteId;
    private String noteTitle;
    private String noteDesc;
    private String createdDate;
    private String priorityLevel;
    private String userId;
}
