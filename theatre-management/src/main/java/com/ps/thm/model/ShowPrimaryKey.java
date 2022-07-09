package com.ps.thm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@PrimaryKeyClass
@EqualsAndHashCode(exclude = {"id"})
public class ShowPrimaryKey {
    @PrimaryKeyColumn(name = "city", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String city;
    @PrimaryKeyColumn(name = "movie-id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private UUID movieId;
    @PrimaryKeyColumn(name = "date", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
    private LocalDate date;
    @PrimaryKeyColumn(name = "theatre-id", ordinal = 3, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private UUID theatreId;
    @PrimaryKeyColumn(name = "time", ordinal = 4, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private LocalTime time;
    @PrimaryKeyColumn(name = "id", ordinal = 5, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private UUID id;
}
