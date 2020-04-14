package playlist.Repository;

import com.sun.tools.javac.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import playlist.Entity.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findByUserId(Long userId);
}
