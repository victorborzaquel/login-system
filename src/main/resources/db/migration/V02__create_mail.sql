create table if not exists public.mail
(
    id           bigint not null
        primary key,
    email_from   varchar(255),
    email_status varchar(255),
    email_to     varchar(255),
    owner_ref    bigint not null,
    send_at      timestamp(6),
    subject      varchar(255),
    text         text
);

create sequence if not exists public.mail_seq
    start with 50
    increment by 50;