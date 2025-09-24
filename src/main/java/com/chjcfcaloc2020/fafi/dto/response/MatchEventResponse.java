package com.chjcfcaloc2020.fafi.dto.response;

import com.chjcfcaloc2020.fafi.dto.MatchEventDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchEventResponse {
    private MatchEventDTO matchEvent;
    private String message;

    public MatchEventResponse(MatchEventDTO matchEvent, String message) {
        this.matchEvent = matchEvent;
        this.message = message;
    }
}
