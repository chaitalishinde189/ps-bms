package com.ps.thm.controller;

import com.ps.thm.dto.Show;
import com.ps.thm.service.ShowService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shows")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShowController {

    private final ShowService showService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createShow(@Valid @RequestBody Show show) {
        log.info("Received request to create show for theatre uuid: {}", show.getTheatreId());
        showService.createShow(show);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Show> getAllShows(@NotBlank @RequestParam String city,
                                  @NotNull @RequestParam UUID movieId,
                                  @NotBlank @RequestParam String date) {
        return showService.getAllShowsByFilters(city, movieId, LocalDate.parse(date));
    }

    @PutMapping("/{showId}")
    @ResponseStatus(HttpStatus.OK)
    public Show updateShow(@NotNull @PathVariable UUID showId, @Valid @RequestBody Show show) {
        log.info("Received request to update show with uuid: {}", showId);
        return showService.updateShow(showId, show);
    }

    @DeleteMapping("/{showId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShow(@NotNull @PathVariable UUID showId) {
        showService.deleteShow(showId);
    }

}
