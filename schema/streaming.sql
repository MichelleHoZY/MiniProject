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
create index idx_title on media(title);

-- create actors table
create table actors(
    id int auto_increment not null,
    name varchar(64) not null,
    title varchar(256) not null,

    primary key(id),
    constraint fk_title
    foreign key(title)
    references media(title)
);

-- create streaming table
create table streaming_sites(
    id int auto_increment not null,
    title varchar(256) not null,
    streaming_site varchar(64) not null,

    primary key(id),
    constraint fk_title_2
    foreign key(title)
    references media(title)
);