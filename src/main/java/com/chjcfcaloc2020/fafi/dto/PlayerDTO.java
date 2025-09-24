package com.chjcfcaloc2020.fafi.dto;

import com.chjcfcaloc2020.fafi.entity.Player;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private Long id;

    @NotNull(message = "Player's name is required")
    @NotBlank(message = "Please type player's name")
    @Size(min = 2, max = 50, message = "Player name must be between 2 and 50 characters")
    private String name;

    @Past(message = "Date of birth must be in the past")
    private Date dob;

    private String position;

    @JsonProperty("shirt_number")
    private Integer shirtNumber;

    private String team;

    public PlayerDTO(Player player) {
        this.id = player.getId();
        this.name = player.getName();
        this.dob = player.getDob();
        this.position = player.getPosition();
        this.shirtNumber = player.getShirtNumber();
        this.team = player.getTeam().getId();
    }
}
