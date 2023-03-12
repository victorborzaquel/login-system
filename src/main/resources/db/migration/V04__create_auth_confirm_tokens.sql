create table public.auth_confirm_tokens
(
    auth_id           bigint not null
        constraint fkqaph5akb59xr59eg01c9xje6x
            references public.auth,
    confirm_tokens_id bigint not null
        constraint uk_747fg8wwy37mbexofuxy4sf1g
            unique
        constraint fk9mbq1regb511c6pdoq00pbr7n
            references public.confirm_token
);

create sequence if not exists public.auth_confirm_tokens_seq
    start with 50
    increment by 50;
