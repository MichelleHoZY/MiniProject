-- drop schema if exists
drop schema if exists streaming;

-- create schema
create schema streaming;

-- use schema
use streaming;

-- create media table
create table imdb_data(
    tconst varchar(10) not null,
    titleType varchar(15) not null,,
    primaryTitle varchar(450) not null,
    originalTitle varchar(450) not null,
    isAdult char(1),
    startYear smallint,
    endYear smallint,
    runtimeMinutes smallint,
    genres varchar(35),

    primary key(tconst)
);

-- index title
create index idx_title on media(primaryTitle, originalTitle);

-- create episode table
create table episodes(
    tconst varchar(10) not null,
    parentTconst varchar(10) not null,
    seasonNumber smallint,
    episodeNumber mediumint,

    primary key(tconst)
);