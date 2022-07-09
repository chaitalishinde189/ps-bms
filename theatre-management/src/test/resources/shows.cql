CREATE KEYSPACE test_ps_bms WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 2};

CREATE TABLE test_ps_bms.shows (
    id uuid,
    city text,
    "movie-id" uuid,
    "theatre-id" uuid,
    date date,
    time time,
    format text,
    language text,
    PRIMARY KEY ((city, "movie-id", date), "theatre-id", time, id)
) WITH CLUSTERING ORDER BY ("theatre-id" ASC, time ASC, id ASC);
