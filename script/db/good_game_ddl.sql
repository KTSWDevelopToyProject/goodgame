-- database 생성
CREATE DATABASE ktgame_database;

-- role 생성
CREATE ROLE ktgame_admin;
CREATE ROLE ktgame_dml;
CREATE ROLE ktgame_select;

-- user 생성
CREATE USER ktgame WITH PASSWORD 'ktgame';
CREATE USER ktgame_user WITH PASSWORD 'ktgame@user';
CREATE USER ktgame_reader WITH PASSWORD 'ktgame@reader';

-- user 에게 role 부여
GRANT ktgame_admin TO ktgame;
GRANT ktgame_dml TO ktgame_user;
GRANT ktgame_select TO ktgame_reader;

-- user 별 schema search_path 적용
ALTER USER ktgame SET search_path TO ktgame_common;
ALTER USER ktgame_user SET search_path TO ktgame_common;
ALTER USER ktgame_reader SET search_path TO ktgame_common;

-- schema 생성
CREATE SCHEMA ktgame_common AUTHORIZATION ktgame_admin;

-- schema 권한 관련 설정
GRANT ALL ON SCHEMA ktgame_common TO ktgame_admin;
GRANT USAGE ON SCHEMA ktgame_common TO ktgame_dml;
GRANT USAGE ON SCHEMA ktgame_common TO ktgame_select;

ALTER DEFAULT PRIVILEGES IN SCHEMA ktgame_common GRANT ALL ON TABLES TO ktgame_admin;
ALTER DEFAULT PRIVILEGES IN SCHEMA ktgame_common GRANT ALL ON SEQUENCES TO ktgame_admin;
ALTER DEFAULT PRIVILEGES IN SCHEMA ktgame_common GRANT ALL ON FUNCTIONS TO ktgame_admin;

ALTER DEFAULT PRIVILEGES IN SCHEMA ktgame_common GRANT DELETE, UPDATE, SELECT, INSERT ON TABLES TO ktgame_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA ktgame_common GRANT SELECT ON TABLES TO ktgame_select;

ALTER DEFAULT PRIVILEGES IN SCHEMA ktgame_common GRANT USAGE ON SEQUENCES TO ktgame_dml;
ALTER DEFAULT PRIVILEGES IN SCHEMA ktgame_common GRANT USAGE ON SEQUENCES TO ktgame_select;

ALTER DEFAULT PRIVILEGES IN SCHEMA ktgame_common GRANT EXECUTE ON FUNCTIONS TO ktgame_dml;
ALTER DEFAULT PRIVILEGES IN SCHEMA ktgame_common GRANT EXECUTE ON FUNCTIONS TO ktgame_select;