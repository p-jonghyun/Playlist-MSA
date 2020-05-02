package playlist.Service;


import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playlist.Entity.Album;
import playlist.Entity.Locale;
import playlist.Entity.QAlbum;
import playlist.Exception.IllegalLocalException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AlbumSearchService extends QuerydslRepositorySupport {

    public AlbumSearchService() {
        super(Album.class);
    }
    private QAlbum qAlbum = QAlbum.album;

    public Page<Album> getAlbums(Locale locale, Pageable pageable) {

        JPQLQuery<Album> query;
        switch (locale) {
            case ko: case ja: case en:
                query = from(qAlbum)
                        .where(qAlbum.locales.contains(locale));
                break;
            case all:
                query = from(qAlbum).fetchAll();
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
                                .and(qAlbum.title.like("%"+title+"%")));
                break;
            case all:
                query = from(qAlbum)
                        .where(qAlbum.title.like("%"+title+"%"));
                break;
            default:
                throw new IllegalArgumentException();
        }

        return query.fetch();
    }
}
