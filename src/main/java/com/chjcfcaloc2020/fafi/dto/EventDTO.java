package com.chjcfcaloc2020.fafi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventDTO {
    @NotBlank(message = "Please type event's name!")
    @NotNull(message = "Event's name is required")
    private String eventName;
}
