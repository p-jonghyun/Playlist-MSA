package user.DTO;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import user.Common.Paging.PageDto;
import user.Entity.Address;
import user.Entity.User;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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

        @Builder
        public SignupReq(String email,String name,String password,String phoneNumber, int userType, String address1,  String address2, String zip) {
            this.email = email;
            this.name = name;
            this.password = password;
            this.phoneNumber = phoneNumber;
            this.userType = userType;
            this.address1 = address1;
            this.address2 = address2;
            this.zip = zip;
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

        public UserRes(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.name = user.getName();
            this.phoneNumber = user.getPhoneNumber();
            this.createdAt = user.getCreatedAt();
            this.updatedAt = user.getUpdatedAt();
            this.authorities = user.getAuthorities();
            this.address = user.getAddress();
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
}
