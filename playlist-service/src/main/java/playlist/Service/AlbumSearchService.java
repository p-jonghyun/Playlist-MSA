package playlist.Service;


import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playlist.Entity.*;
import playlist.Exception.IllegalLocalException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AlbumSearchService extends QuerydslRepositorySupport {

    @Autowired
    EntityManager entityManager;

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    JPAQueryFactory queryFactory;

    public AlbumSearchService() {
        super(Album.class);
        queryFactory = new JPAQueryFactory(entityManager);
    }
    private QAlbum qAlbum = QAlbum.album;
    private QSong qSong = QSong.song;

    public Page<Album> getAlbums(Locale locale, Pageable pageable) {

        JPQLQuery<Album> query;
        switch (locale) {
            case ko: case ja: case en:
                query = from(qAlbum)
                        .where(qAlbum.locales.contains(locale))
                        .leftJoin(qAlbum.songs, QSong.song).fetchJoin()
                        .distinct();

                break;
            case all:
                query = from(qAlbum)
                        .leftJoin(qAlbum.songs, QSong.song).fetchJoin()
                        .distinct();
                break;
            default:
                throw new IllegalLocalException();
        }

        List<Album> albums = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(albums, pageable, query.fetchCount());
    }

    public List<Album> search(String title, Locale locale) {

        JPQLQuery<Album> query;

        switch (locale) {
            case ko: case ja: case en:
                query = from(qAlbum)
                        .where(qAlbum.locales.contains(locale)
                                .and(qAlbum.title.like("%"+title+"%")))
                        .leftJoin(qAlbum.songs, QSong.song).fetchJoin()
                        .distinct();
                break;
            case all:
                query = from(qAlbum)
                        .where((qAlbum.title.like("%"+title+"%")))
                        .leftJoin(qAlbum.songs, QSong.song).fetchJoin()
                        .distinct();
                break;
            default:
                throw new IllegalArgumentException();
        }

        return query.fetch();
    }

}
