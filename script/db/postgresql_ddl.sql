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

ALTER DEFAULT PRIVILEGES IN SCHEMA ktgame_common GRANT DELETE, UPDATE, SELECT, INSERT ON TABLES TO ktgame_dml;
ALTER DEFAULT PRIVILEGES IN SCHEMA ktgame_common GRANT SELECT ON TABLES TO ktgame_select;

ALTER DEFAULT PRIVILEGES IN SCHEMA ktgame_common GRANT USAGE ON SEQUENCES TO ktgame_dml;
ALTER DEFAULT PRIVILEGES IN SCHEMA ktgame_common GRANT USAGE ON SEQUENCES TO ktgame_select;

ALTER DEFAULT PRIVILEGES IN SCHEMA ktgame_common GRANT EXECUTE ON FUNCTIONS TO ktgame_dml;
ALTER DEFAULT PRIVILEGES IN SCHEMA ktgame_common GRANT EXECUTE ON FUNCTIONS TO ktgame_select;

--======================================================================================================================
    -- 테이블 생성 관련
--======================================================================================================================

-- 월별 파티션 테이블 생성 procedure
CREATE OR REPLACE PROCEDURE ktgame_common.create_partitions(IN table_name text, IN partition_key_column_name text, IN target_year character varying DEFAULT to_char(now(), 'YYYY'::text))
    LANGUAGE plpgsql
AS $procedure$
	declare
        -- DB 이름
schema_nm text := 'ktgame_common';
        partition_this_month text;
        partition_next_month text;
        partition_start_range text;
        partition_end_range text;
        i integer;
begin
        -- 디폴트 파티션 생성
begin
execute 'create table if not exists ' || schema_nm || '.' || table_name || '_default partition of ' || schema_nm || '.' || table_name || ' default';
exception when others then
				raise notice 'exception occurred on creation of default partition';
end;
		i := 1;
        -- 12번 반복 (1월 ~ 12월)
		loop
partition_this_month := right(('0' || i) , 2);
			partition_next_month = right(('0' || (i % 12) +1), 2);
			partition_start_range = '(''' || target_year || '-' || partition_this_month  || '-01 00:00:00.000' || ''')';
		    if i != 12 then
			    partition_end_range = '(''' || target_year || '-' || partition_next_month  || '-01 00:00:00.000' || ''')';
            -- 12월일 경우 target_year + 1
else
			    partition_end_range = '(''' || target_year::integer + 1 || '-' || partition_next_month  || '-01 00:00:00.000' || ''')';
end if;
begin
                -- 파티션 생성 sql
execute 'create table if not exists ' || schema_nm || '.' || table_name || '_' || target_year || partition_this_month || ' partition of ' || schema_nm || '.' || table_name || ' for values from ' || partition_start_range || ' to ' || partition_end_range ;
-- 이미 해당 월에 데이터가 있어 오류가 났을 경우
exception when others then
				    raise notice 'exception occurred on creation of partition: data exists';
end;
			i := i + 1;
			exit when i = 13;
end loop;
end;
$procedure$ ;

-- TEST 용 테이블 생성
CREATE TABLE ktgame_common.product (
                                       id bigint primary key,
                                       description varchar(300),
                                       price double precision
);

-- 실제 테이블 생성

-- 멤버 테이블 생성
CREATE TABLE ktgame_common.tb_ktgame_member (
                                                user_id VARCHAR(20) NOT NULL,
                                                user_name VARCHAR(50) NOT NULL,
                                                created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                                                deleted_at TIMESTAMP,
                                                account_activated_yn VARCHAR(1) NOT NULL DEFAULT 'N',
                                                login_yn VARCHAR(1) NOT NULL DEFAULT 'N'
);
COMMENT ON COLUMN ktgame_common.tb_ktgame_member.user_id IS '유저 고유 ID';
COMMENT ON COLUMN ktgame_common.tb_ktgame_member.user_name IS '유저 이름';
COMMENT ON COLUMN ktgame_common.tb_ktgame_member.created_at IS '유저 계정 생성 일시';
COMMENT ON COLUMN ktgame_common.tb_ktgame_member.deleted_at IS '유저 계정 삭제 일시';
COMMENT ON COLUMN ktgame_common.tb_ktgame_member.account_activated_yn IS '유저 계정 활성화 여부';
COMMENT ON COLUMN ktgame_common.tb_ktgame_member.login_yn IS '유저 로그인 여부';

ALTER TABLE ktgame_common.tb_ktgame_member ADD CONSTRAINT pk_user_id PRIMARY KEY (user_id);

-- 게임 History 테이블 생성 (파티션 적용)
CREATE TABLE ktgame_common.tb_ktgame_game_history (
                                                      game_id VARCHAR(100) NOT NULL ,
                                                      left_user_id VARCHAR(20) NOT NULL,
                                                      right_user_id VARCHAR(20),
                                                      winner_flag VARCHAR(1),
                                                      created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                                                      game_status_code VARCHAR(1) NOT NULL
) PARTITION BY RANGE(created_at);
COMMENT ON COLUMN ktgame_common.tb_ktgame_game_history.game_id IS '게임 고유 ID';
COMMENT ON COLUMN ktgame_common.tb_ktgame_game_history.left_user_id IS '좌측 유저 고유 ID';
COMMENT ON COLUMN ktgame_common.tb_ktgame_game_history.right_user_id IS '우측 유저 고유 ID';
COMMENT ON COLUMN ktgame_common.tb_ktgame_game_history.winner_flag IS '게임 승자 식별 값';
COMMENT ON COLUMN ktgame_common.tb_ktgame_game_history.created_at IS '게임 생성 일시';
COMMENT ON COLUMN ktgame_common.tb_ktgame_game_history.game_status_code IS '게임 상태 식별 값';

ALTER TABLE ktgame_common.tb_ktgame_game_history ADD CONSTRAINT pk_game_id PRIMARY KEY (game_id, created_at);

-- 파티션 테이블 생성 procedure 호출
CALL ktgame_common.create_partitions('tb_ktgame_game_history', 'created_at', '2022');
CALL ktgame_common.create_partitions('tb_ktgame_game_history', 'created_at', '2023');
CALL ktgame_common.create_partitions('tb_ktgame_game_history', 'created_at', '2024');

-- 레이팅 테이블 생성
CREATE TABLE ktgame_common.tb_ktgame_game_rating (
                                                user_id VARCHAR(20) NOT NULL,
                                                win INTEGER,
                                                total INTEGER,
                                                rating_id SERIAL NOT NULL,
                                                CONSTRAINT pk_game_rating_user_id PRIMARY KEY (user_id)
);
COMMENT ON COLUMN ktgame_common.tb_ktgame_game_rating.user_id IS '유저 고유 ID';
COMMENT ON COLUMN ktgame_common.tb_ktgame_game_rating.win IS '유저 승 수';
COMMENT ON COLUMN ktgame_common.tb_ktgame_game_rating.total IS '유저 전체 게임 수';