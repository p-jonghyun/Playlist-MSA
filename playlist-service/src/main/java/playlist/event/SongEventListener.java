package playlist.event;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import playlist.DTO.AlbumDto;
import playlist.Entity.Album;
import playlist.Entity.Genre;
import playlist.Entity.Locale;
import playlist.Entity.Song;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
@Async
public class SongEventListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @EventListener
    public void handleSongCreatedEvent(SongCreatedEvent songCreatedEvent) {

        ObjectMapper objectMapper = new ObjectMapper();
        Song song = songCreatedEvent.getSong();
        List<Genre> genres = song.getGenres();

        String title = "새로운 노래 출시 알림";

        for(Genre genre : genres) {
            String msg = "구독하신 장르 " + genre + "의 새로운 노래 " + song.getTitle() + " 이 출시되었습니다!";
            AlbumDto.RabbitSenderData data = new AlbumDto.RabbitSenderData(title, msg, String.valueOf(genre));

            String json = null;

            try {
                json = objectMapper.writeValueAsString(data);
                rabbitTemplate.convertAndSend("q.requestUsersWithNoti.genre", json);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @EventListener
    public void handleAlbumCreatedEvent(AlbumCreatedEvent albumCreatedEvent) {

        ObjectMapper objectMapper = new ObjectMapper();
        Album album = albumCreatedEvent.getAlbum();
        List<Locale> locales = album.getLocales();

        String title = "새로운 앨범 출시 알림";
        for(Locale locale : locales) {
            String msg = "구독하신 지 " + locale + "의 새로운 범 " + album.getTitle() + " 이 출시되었습니다!";
            AlbumDto.RabbitSenderData data = new AlbumDto.RabbitSenderData(title, msg, String.valueOf(locale));

            String json = null;

            try {
                json = objectMapper.writeValueAsString(data);
                rabbitTemplate.convertAndSend("q.requestUsersWithNoti.locale", json);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
