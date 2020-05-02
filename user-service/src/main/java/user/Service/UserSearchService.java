package user.Service;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import user.Entity.QUser;
import user.Entity.User;

import java.util.List;

@Service
public class UserSearchService extends QuerydslRepositorySupport {


    public UserSearchService() {
        super(User.class);
    }

    private QUser qUser = QUser.user;
    public Page<User> pageUsers(Pageable pageable) {

        JPQLQuery<User> query = from(qUser);

        List<User> users = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(users, pageable, query.fetchCount());

    }
}
