package user.Repository;

import org.springframework.data.jpa.repository.EntityGraph;
import user.Entity.Genre;
import user.Entity.User;

import java.util.List;

public interface UserRepsositoryExtension {

    User findByGenresIn(List<Genre> genres);
}
