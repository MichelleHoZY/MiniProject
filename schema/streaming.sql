-- drop schema if exists
drop schema if exists streaming;

-- create schema
create schema streaming;

-- use schema
use streaming;

-- create media table
create table media(
    title varchar(256) not null,
    original_title varchar(256) not null,
    rating float (3,1) not null,
    overview varchar(1000),
    imdb_id char(30),
    year int not null,
    runtime int not null,
    language char(5),
    leaving int not null,
    poster_path varchar(64) not null,
    search_date date not null,

    primary key(title)    
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