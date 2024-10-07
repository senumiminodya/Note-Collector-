package lk.ijse.notecollector.dao;

import lk.ijse.notecollector.dto.impl.UserDTO;
import lk.ijse.notecollector.entity.impl.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository /* Meya dao layer eke kenek kiyanna danne.
@componenet annotation eka meta annotate wela thiyenne */
public interface UserDAO extends JpaRepository<UserEntity, String> {

}
