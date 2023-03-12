create table if not exists public.auth
(
    id       bigint  not null
        primary key,
    email    varchar(255),
    enabled  boolean not null,
    locked   boolean,
    password varchar(255),
    role     varchar(255)
);

create sequence if not exists public.auth_seq
    start with 50
    increment by 50;
