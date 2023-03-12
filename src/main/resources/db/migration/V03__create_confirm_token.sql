create table if not exists public.confirm_token
(
    id           bigint       not null
        primary key,
    confirmed_at timestamp(6),
    created_at   timestamp(6) not null,
    expires_at   timestamp(6) not null,
    token        varchar(255),
    auth_id      bigint       not null
        constraint fkicl3v6gb1p1njiod1dg1bcgsr
            references public.auth
);

create sequence if not exists public.confirm_token_seq
    start with 50
    increment by 50;
