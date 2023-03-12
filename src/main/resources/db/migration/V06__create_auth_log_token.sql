create table public.auth_log_tokens
(
    auth_id       bigint not null
        constraint fktkofhy8gluxhp8ctgxk9x6jo5
            references public.auth,
    log_tokens_id bigint not null
        constraint uk_ptcjos83kc5u7qxqwb4t8hho2
            unique
        constraint fkhmy6djc7xexb4hyah264m45pt
            references public.log_token
);

create sequence if not exists public.auth_log_tokens_seq
    start with 50
    increment by 50;
