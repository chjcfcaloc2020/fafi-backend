package com.chjcfcaloc2020.fafi.dto;

import com.chjcfcaloc2020.fafi.entity.Team;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    private String id;

    @NotNull(message = "Team's name is required")
    @NotBlank(message = "Please type team's name")
    private String name;

    private String logo;

    @NotNull(message = "Coach's name is required")
    @NotBlank(message = "Please type coach's name")
    @JsonProperty("coach_name")
    private String coachName;

    private String manager;

    public TeamDTO(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.logo = team.getLogo();
        this.coachName = team.getCoachName();
        this.manager = team.getManager().getUsername();
    }
}
