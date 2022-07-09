package com.ps.thm.helper;

import com.ps.thm.dto.Show;
import com.ps.thm.exception.InvalidRequestException;
import com.ps.thm.model.ShowEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ShowValidator {
    public void validateShowDetails(Show show) {
        // TODO: Validations to check if valid movie, theatre and location
        validateShowDate(show.getDate());
    }

    private void validateShowDate(LocalDate date) {
        if (date.isBefore(LocalDate.now().plusDays(1))) {
            throw new InvalidRequestException("Show should be created at least a day in advance");
        }
    }

    public void validateShowUpdate(Show show, ShowEntity showEntity) {
        if (!show.getCity().equals(showEntity.getKey().getCity())) {
            throw new InvalidRequestException("Show city can not be updated");
        }
        if (!show.getTheatreId().equals(showEntity.getKey().getTheatreId())) {
            throw new InvalidRequestException("Show theatre can not be updated");
        }
    }

}
