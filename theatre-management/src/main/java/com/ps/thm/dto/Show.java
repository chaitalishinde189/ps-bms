package com.ps.thm.dto;

import com.ps.thm.enums.Format;
import com.ps.thm.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Show {
    private UUID id;
    @NotNull(message = "[movieId] should not be null")
    private UUID movieId;
    @NotNull(message = "[theatreId] should not be null")
    private UUID theatreId;
    @NotBlank(message = "[city] should not be blank")
    private String city;
    @NotNull(message = "[date] should not be null")
    private LocalDate date;
    @NotNull(message = "[time] should not be null")
    private LocalTime time;
    @NotNull(message = "[language] should not be null")
    private Language language;
    @NotNull(message = "[format] should not be null")
    private Format format;
}
