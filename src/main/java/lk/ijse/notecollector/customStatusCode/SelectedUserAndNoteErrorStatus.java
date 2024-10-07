package lk.ijse.notecollector.customStatusCode;

import lk.ijse.notecollector.dto.NoteStatus;
import lk.ijse.notecollector.dto.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedUserAndNoteErrorStatus implements UserStatus, NoteStatus {
    private int status;
    private String statusMessage;
}
