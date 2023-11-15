CREATE TABLE IF NOT EXISTS public.password_policies(
    id uuid NOT NULL,
    created_by uuid,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    number_of_characters integer NOT NULL,
    number_of_lowercase_characters integer NOT NULL,
    number_of_numeric_characters integer NOT NULL,
    number_of_special_characters integer NOT NULL,
    number_of_uppercase_characters integer NOT NULL,
    password_reset_days integer NOT NULL,
    CONSTRAINT password_policies_pkey PRIMARY KEY (id),
    CONSTRAINT uk_kna4wo7k6sww2hn9sobuf08k4 UNIQUE (name)
);
CREATE TABLE IF NOT EXISTS public.permissions(
    id uuid NOT NULL,
    action character varying(255) COLLATE pg_catalog."default",
    authority character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    permission character varying(255) COLLATE pg_catalog."default",
    subject character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT permissions_pkey PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS public.roles(
    id uuid NOT NULL,
    created_by uuid,
    description character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS public.role_permissions(
    role_id uuid NOT NULL,
    permission_id uuid NOT NULL,
    CONSTRAINT role_permissions_pkey PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fkegdk29eiy7mdtefy5c7eirr6e FOREIGN KEY (permission_id)
    REFERENCES public.permissions (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fkn5fotdgk8d1xvo8nav9uv3muc FOREIGN KEY (role_id)
    REFERENCES public.roles (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);
CREATE TABLE IF NOT EXISTS public.users(
    id uuid NOT NULL,
    change_password_next_login boolean,
    created_by uuid,
    email_address character varying(255) COLLATE pg_catalog."default",
    employee_id uuid,
    password character varying(255) COLLATE pg_catalog."default",
    status character varying(255) COLLATE pg_catalog."default",
    username character varying(255) COLLATE pg_catalog."default",
    password_policy uuid NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT fk127jd4ga6joquwal2ap9qjq40 FOREIGN KEY (password_policy)
    REFERENCES public.password_policies (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT users_status_check CHECK (status::text = ANY (ARRAY['ACTIVE'::character varying, 'DEACTIVATED'::character varying]::text[]))
);
CREATE TABLE IF NOT EXISTS public.user_roles(
    user_id uuid NOT NULL,
    role_id uuid NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id)
    REFERENCES public.roles (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);
CREATE TABLE IF NOT EXISTS public.sessions(
    id uuid NOT NULL,
    expected_termination_time timestamp(6) with time zone,
    ip_address character varying(255) COLLATE pg_catalog."default",
    is_active boolean,
    started_at timestamp(6) with time zone,
    terminated_at timestamp(6) with time zone,
    token character varying(510) COLLATE pg_catalog."default",
    user_id uuid,
    CONSTRAINT sessions_pkey PRIMARY KEY (id)
);