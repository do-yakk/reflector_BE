package com.doyak.reflector.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.doyak.reflector.domain.common.BaseEntity;
import com.doyak.reflector.dto.request.UserRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@NonNull
	private String email;
	
	@NonNull
	private String password;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	
	@Override
    public String getUsername() {
        return this.email;
    }
	
	public void updateUser(UserRequest.UserUpdateDTO user, PasswordEncoder passwordEncoder) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            this.password = passwordEncoder.encode(user.getPassword());
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            this.email = user.getEmail();
        }
    }
	
}
