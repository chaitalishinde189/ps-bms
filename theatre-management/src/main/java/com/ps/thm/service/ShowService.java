package com.ps.thm.service;

import com.ps.thm.dto.Show;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ShowService {
    void createShow(Show show);

    Show updateShow(UUID showId, Show show);

    List<Show> getAllShowsByFilters(String city, UUID movieId, LocalDate date);

    void deleteShow(UUID showId);

}
