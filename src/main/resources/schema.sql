DROP TABLE IF EXISTS company.employee;
DROP TABLE IF EXISTS company.department;
DROP TABLE IF EXISTS company.company;

DROP SEQUENCE IF EXISTS company.employee_id_seq;
DROP SEQUENCE IF EXISTS company.department_id_seq;
DROP SEQUENCE IF EXISTS company.company_id_seq;

CREATE SEQUENCE company.employee_id_seq START WITH 100000;
CREATE SEQUENCE company.department_id_seq START WITH 100000;
CREATE SEQUENCE company.company_id_seq START WITH 100000;

CREATE TABLE company.company
(
    id BIGINT DEFAULT nextval('company.company_id_seq'),
    company_name VARCHAR(100) NOT NULL,

    CONSTRAINT company_pk PRIMARY KEY (id),
    CONSTRAINT company_name_index UNIQUE (company_name)
);

CREATE TABLE company.department
(
    id BIGINT DEFAULT nextval('company.department_id_seq'),
    dept_code VARCHAR(20) NOT NULL,
    dept_name VARCHAR(30) NOT NULL,
    company_id BIGINT NOT NULL,

    CONSTRAINT dept_co_fk FOREIGN KEY (company_id) REFERENCES company.company(id),
    CONSTRAINT department_pk PRIMARY KEY (id),
    CONSTRAINT dept_code_co_index UNIQUE (dept_code, company_id),
    CONSTRAINT dept_name_co_index UNIQUE (dept_name, company_id)
);

CREATE TABLE company.employee
(
    id BIGINT DEFAULT nextval('company.employee_id_seq'),
    first_name VARCHAR(40) NOT NULL,
    last_name VARCHAR(40) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT FALSE,
    start_date DATE NOT NULL,
    title VARCHAR(40) NOT NULL,
    dept_id BIGINT NOT NULL,

    CONSTRAINT emp_dept_fk FOREIGN KEY (dept_id) REFERENCES company.department(id),
    CONSTRAINT employee_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS team.player;
DROP TABLE IF EXISTS team.team;
DROP TABLE IF EXISTS team.league;

DROP SEQUENCE IF EXISTS team.player_id_seq;
DROP SEQUENCE IF EXISTS team.team_id_seq;
DROP SEQUENCE IF EXISTS team.league_id_seq;

CREATE SEQUENCE team.player_id_seq START WITH 100000;
CREATE SEQUENCE team.team_id_seq START WITH 100000;
CREATE SEQUENCE team.league_id_seq START WITH 100000;

CREATE TABLE team.league
(
    id BIGINT DEFAULT nextval('team.league_id_seq'),
    league_name VARCHAR(100) NOT NULL,

    CONSTRAINT league_pk PRIMARY KEY (id),
    CONSTRAINT league_name_index UNIQUE (league_name)
);

CREATE TABLE team.team
(
    id BIGINT DEFAULT nextval('team.team_id_seq'),
    team_code VARCHAR(20) NOT NULL,
    team_name VARCHAR(30) NOT NULL,
    league_id BIGINT NOT NULL,

    CONSTRAINT team_co_fk FOREIGN KEY (league_id) REFERENCES team.league(id),
    CONSTRAINT team_pk PRIMARY KEY (id),
    CONSTRAINT team_code_co_index UNIQUE (team_code, league_id),
    CONSTRAINT team_name_co_index UNIQUE (team_name, league_id)
);

CREATE TABLE team.player
(
    id BIGINT DEFAULT nextval('team.player_id_seq'),
    full_name VARCHAR(100) NOT NULL,
    position VARCHAR(40) NOT NULL,
    team_id BIGINT NOT NULL,

    CONSTRAINT player_team_fk FOREIGN KEY (team_id) REFERENCES team.team(id),
    CONSTRAINT player_pk PRIMARY KEY (id)
);


DROP TABLE IF EXISTS public.event_publication;
CREATE TABLE IF NOT EXISTS public.event_publication
(
    id               UUID NOT NULL,
    listener_id      TEXT NOT NULL,
    event_type       TEXT NOT NULL,
    serialized_event TEXT NOT NULL,
    publication_date TIMESTAMP WITH TIME ZONE NOT NULL,
    completion_date  TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS event_publication_serialized_event_hash_idx ON public.event_publication USING hash(serialized_event);
CREATE INDEX IF NOT EXISTS event_publication_by_completion_date_idx ON public.event_publication (completion_date);

DROP TABLE IF EXISTS public.event_publication_archive;
CREATE TABLE IF NOT EXISTS public.event_publication_archive
(
    id               UUID NOT NULL,
    listener_id      TEXT NOT NULL,
    event_type       TEXT NOT NULL,
    serialized_event TEXT NOT NULL,
    publication_date TIMESTAMP WITH TIME ZONE NOT NULL,
    completion_date  TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS event_publication_arch_serialized_event_hash_idx ON public.event_publication_archive USING hash(serialized_event);
CREATE INDEX IF NOT EXISTS event_publication_arch_by_completion_date_idx ON public.event_publication_archive (completion_date);
