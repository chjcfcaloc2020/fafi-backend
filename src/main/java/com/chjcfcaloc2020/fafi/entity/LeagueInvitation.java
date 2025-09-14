package com.chjcfcaloc2020.fafi.entity;

import com.chjcfcaloc2020.fafi.common.LeagueInvitationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "league_invitation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueInvitation {
    @EmbeddedId
    private LeagueInvitationPK id;

    @ManyToOne
    @MapsId("leagueId")
    @JoinColumn(name = "league_id", nullable = false)
    private League league;

    @ManyToOne
    @MapsId("managerId")
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    @Enumerated(EnumType.STRING)
    private LeagueInvitationStatus status;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;
}