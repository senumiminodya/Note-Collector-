package lk.ijse.notecollector.service;

import lk.ijse.notecollector.dto.UserStatus;
import lk.ijse.notecollector.dto.impl.NoteDTO;
import lk.ijse.notecollector.dto.impl.UserDTO;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserStatus getUser(String userId);
    void deleteUser(String userId);
    void updateUser(String userId, UserDTO userDTO);
}
