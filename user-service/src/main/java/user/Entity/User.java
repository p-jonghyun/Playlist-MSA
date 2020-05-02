package user.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Email
    @Column(unique = true)
    private String email;

    private String name;
    private String password;
    private String phoneNumber;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<GrantedAuthority> authorities = new ArrayList<>();

    @ElementCollection
    private List<Genre> genres = new ArrayList<>();

    @ElementCollection
    private List<Locale> locales = new ArrayList<>();

    private boolean songCreatedNotification = false;
    private boolean albumCreatedNotification = false;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", authorities=" + authorities +
                ", address=" + address +
                '}';
    }

    @Embedded
    private Address address;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Builder
    public User(String email, String name, String password, String phoneNumber, Address address, int userType, String provider,
                List<Genre> genres, List<Locale> locales,
                boolean songCreatedNotification, boolean albumCreatedNotification) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;

        List<GrantedAuthority> authorities = new ArrayList<>();
        switch (userType) {
            case 0:
                authorities.add(new SimpleGrantedAuthority("ADMIN"));
            case 1:
                authorities.add(new SimpleGrantedAuthority("USER"));
                break;
        }
        this.authorities = authorities;
        this.genres = genres;
        this.locales = locales;
        this.songCreatedNotification = songCreatedNotification;
        this.albumCreatedNotification = albumCreatedNotification;
    }

}
