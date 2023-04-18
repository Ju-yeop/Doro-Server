package com.example.DoroServer.domain.token.entity;

import com.example.DoroServer.domain.base.BaseEntity;
import com.example.DoroServer.domain.token.dto.TokenDto;
import com.example.DoroServer.domain.user.entity.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Token extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "token_id")
    private Long id;

    private String token; // FCM token

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void addUser(User user) {
        this.user = user;
    }

    public TokenDto toDto() {
        return TokenDto.builder()
                .token(token)
                .build();
    }
}
