package album.Service;

import album.Entity.Album;
import album.Entity.Locale;
import album.Entity.QAlbum;
import album.Exception.IllegalLocalException;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
