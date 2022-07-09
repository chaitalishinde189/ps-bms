package com.ps.thm.service;

import com.ps.thm.dao.ShowRepository;
import com.ps.thm.dto.Show;
import com.ps.thm.exception.InvalidRequestException;
import com.ps.thm.exception.ResourceNotFoundException;
import com.ps.thm.helper.DataMapper;
import com.ps.thm.helper.ShowValidator;
import com.ps.thm.model.ShowEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShowServiceImpl implements ShowService {

    private final DataMapper mapper;
    private final ShowRepository showRepository;
    private final ShowValidator showValidator;

    @Override
    public void createShow(Show show) {
        try {
            showValidator.validateShowDetails(show);
            ShowEntity showEntity = mapper.mapToShowEntity(show);
            checkIfShowAlreadyExist(show);
            showRepository.save(showEntity);
        } catch (Exception e) {
            log.error("Error while creating show", e);
            throw e;
        }
    }

    private void checkIfShowAlreadyExist(Show show) {
        Optional<ShowEntity> existingShowEntity = showRepository.findByKeyCityAndKeyMovieIdAndKeyDateAndKeyTheatreIdAndKeyTime(show.getCity(),
                show.getMovieId(), show.getDate(), show.getTheatreId(), show.getTime());

        if (existingShowEntity.isPresent()) {
            throw new InvalidRequestException("Show with same details already exist");
        }

    }

    @Override
    public Show updateShow(UUID showId, Show show) {
        try {
            Optional<ShowEntity> showEntityOptional = showRepository.findByKeyId(showId);
            if (showEntityOptional.isPresent()) {
                showValidator.validateShowDetails(show);

                ShowEntity existingShowEntity = showEntityOptional.get();
                showValidator.validateShowUpdate(show, existingShowEntity);

                ShowEntity newShowEntity = mapper.mapToShowEntity(show);
                newShowEntity.getKey().setId(existingShowEntity.getKey().getId());
                if (!newShowEntity.getKey().equals(existingShowEntity.getKey())) {
                    // Primary key is changed, so delete existing and create new with same id
                    // TODO: need to take care of scenarios where bookings are already made for this show
                    showRepository.delete(existingShowEntity);
                }
                newShowEntity = showRepository.save(newShowEntity);
                return mapper.mapToShow(newShowEntity);
            } else {
                log.error("Show with uuid: {} not found", showId);
                throw new ResourceNotFoundException("Show not found");
            }
        } catch (Exception e) {
            log.error("Error while updating the show with uuid: {}", showId, e);
            throw e;
        }
    }

    @Override
    public List<Show> getAllShowsByFilters(String city, UUID movieId, LocalDate date) {
        try {
            List<ShowEntity> showEntities = showRepository.findByKeyCityAndKeyMovieIdAndKeyDate(city, movieId, date);
            return showEntities.stream()
                    .map(mapper::mapToShow)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error while fetching shows", e);
            throw e;
        }
    }

    @Override
    public void deleteShow(UUID showId) {
        try {
            log.info("Deleting show with uuid: {}", showId);
            Optional<ShowEntity> showEntityOptional = showRepository.findByKeyId(showId);
            if (showEntityOptional.isPresent()) {
                // TODO: need to take care of scenarios where bookings are already made for this show
                showRepository.delete(showEntityOptional.get());
            } else {
                log.error("Show with uuid: {} not found", showId);
                throw new ResourceNotFoundException("Show not found");
            }
        } catch (Exception e) {
            log.error("Error while deleting the show with id: {}", showId, e);
            throw e;
        }
    }

}
