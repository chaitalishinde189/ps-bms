package com.ps.thm.dao;

import com.ps.thm.model.ShowEntity;
import com.ps.thm.model.ShowPrimaryKey;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShowRepository extends CassandraRepository<ShowEntity, ShowPrimaryKey> {
    List<ShowEntity> findByKeyCityAndKeyMovieIdAndKeyDate(String city, UUID movieId, LocalDate date);

    Optional<ShowEntity> findByKeyCityAndKeyMovieIdAndKeyDateAndKeyTheatreIdAndKeyTime(String city, UUID movieId, LocalDate date, UUID theatreId, LocalTime time);

    @AllowFiltering
    Optional<ShowEntity> findByKeyId(UUID id);
}
