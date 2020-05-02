package user.DTO;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import user.Common.Paging.PageDto;
import user.Entity.Address;
import user.Entity.Genre;
import user.Entity.Locale;
import user.Entity.User;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserDto {

    @Getter
    @NoArgsConstructor
    public static class SignupReq {

        @NotNull
        private String email;
        @NotNull
        private String name;
        @NotNull
        private String password;
        @NotNull
        private String phoneNumber;
        @NotNull
        private int userType;
        @NotNull
        private String address1;
        @NotNull
        private String address2;
        @NotNull
        private String zip;
        @NotNull
        private List<Genre> genres;
        @NotNull
        private List<Locale> locales;
        @NotNull
        private boolean songCreatedNotification;
        @NotNull
        private boolean albumCreatedNotification;

        @Builder
        public SignupReq(String email,String name,String password,String phoneNumber, int userType,
                         String address1,  String address2, String zip, List<Genre> genres, List<Locale> locales,
                         boolean songCreatedNotification, boolean albumCreatedNotification) {
            this.email = email;
            this.name = name;
            this.password = password;
            this.phoneNumber = phoneNumber;
            this.userType = userType;
            this.address1 = address1;
            this.address2 = address2;
            this.zip = zip;
            this.genres = genres;
            this.locales = locales;
            this.songCreatedNotification = songCreatedNotification;
            this.albumCreatedNotification = albumCreatedNotification;
        }

        public User toEntity(String password) {

            Address address = Address.builder()
                    .address1(this.address1)
                    .address2(this.address2)
                    .zip(this.zip)
                    .build();

            return User.builder()
                    .email(this.email)
                    .name(this.name)
                    .password(password)
                    .phoneNumber(this.phoneNumber)
                    .address(address)
                    .userType(userType)
                    .genres(genres)
                    .locales(locales)
                    .songCreatedNotification(songCreatedNotification)
                    .albumCreatedNotification(albumCreatedNotification)
                    .build();
        }

    }

    @Getter
    @NoArgsConstructor
    public static class SignupRes {
        private Long id;
        public SignupRes(User user) {
            this.id = user.getId();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class LoginReq {

        @NotNull
        private String email;
        @NotNull
        private String password;

        @Builder
        public LoginReq(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserRes {

        private Long id;
        private String email;
        private String name;
        private String phoneNumber;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        Collection<? extends GrantedAuthority> authorities;
        private Address address;

        private List<Genre> genres;
        private List<Locale> locales;
        private boolean songCreatedNotification;
        private boolean albumCreatedNotification;

        public UserRes(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.name = user.getName();
            this.phoneNumber = user.getPhoneNumber();
            this.createdAt = user.getCreatedAt();
            this.updatedAt = user.getUpdatedAt();
            this.authorities = user.getAuthorities();
            this.address = user.getAddress();
            this.genres = user.getGenres();
            this.locales = user.getLocales();
            this.songCreatedNotification = user.isSongCreatedNotification();
            this.albumCreatedNotification = user.isAlbumCreatedNotification();
        }

    }

    @Getter
    public static class PageUserRes {
        private PageDto pages;
        private List<UserRes> users;

        public PageUserRes(PageDto pages, List<UserRes> users) {
            this.pages = pages;
            this.users = users;
        }
    }

    @Getter
    public static class RabbitUsers {
        private List<Long> users = new ArrayList<>();
        private String title;
        private String msg;
        private String criteria;

        public RabbitUsers(List<User> users, String title, String msg, String criteria) {
            for(User user: users) this.users.add(user.getId());
            this.title = title;
            this.msg = msg;
            this.criteria = criteria;
        }
    }

    @Data
    public static class RabbitData {
        private String title;
        private String msg;
        private String criteria;

    }
}
