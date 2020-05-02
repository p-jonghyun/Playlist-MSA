package user.Repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import user.Entity.Genre;
import user.Entity.Locale;
import user.Entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    List<User> findByGenresInAndSongCreatedNotification(List<Genre> genres, boolean flag);
    List<User> findByLocalesInAndAlbumCreatedNotification(List<Locale> locales, boolean flag);
}
