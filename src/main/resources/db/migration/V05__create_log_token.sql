create table if not exists public.log_token
(
    id             bigint not null
        primary key,
    expired        boolean,
    log_token_type varchar(255),
    revoked        boolean,
    token          varchar(255),
    auth_id        bigint
        constraint fk2dymxce30son4fs72k6gl7xse
            references public.auth
);

create sequence if not exists public.log_token_seq
    start with 50
    increment by 50;
