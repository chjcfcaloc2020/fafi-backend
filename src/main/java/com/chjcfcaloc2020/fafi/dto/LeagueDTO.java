package com.chjcfcaloc2020.fafi.dto;

import com.chjcfcaloc2020.fafi.common.LeagueFormat;
import com.chjcfcaloc2020.fafi.common.LeagueStatus;
import com.chjcfcaloc2020.fafi.entity.League;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeagueDTO {
    private String id;

    @NotNull(message = "League's name is required")
    @NotBlank(message = "Please type league's name")
    @Size(min = 10, message = "League's name so short, which must be least 10 characters")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Min(value = 2, message = "Team number must be at least 2")
    @Max(value = 100, message = "Team number cannot exceed 100")
    @NotNull(message = "Teamnumber is required")
    @JsonProperty("team_number")
    private Integer teamNumber;

    @NotNull(message = "Teamsize is required")
    @JsonProperty("team_size")
    private Integer teamSize;

    @NotNull(message = "Format is required")
    private LeagueFormat format;

    @Future(message = "Start date must be in the future")
    @NotNull(message = "Start date is required")
    @JsonProperty("start_date")
    private Date startDate;

    @Future(message = "End date must be in the future")
    @NotNull(message = "End date is required")
    @JsonProperty("end_date")
    private Date endDate;

    private LeagueStatus status;
    private String organizer;

    public LeagueDTO(League league) {
        this.id = league.getId();
        this.name = league.getName();
        this.description = league.getDescription();
        this.teamNumber = league.getTeamNumber();
        this.teamSize = league.getTeamSize();
        this.format = league.getFormat();
        this.startDate = league.getStartDate();
        this.endDate = league.getEndDate();
        this.status = league.getStatus();
        this.organizer = league.getOrganizer().getUsername();
    }
}
