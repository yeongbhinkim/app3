drop table member;

create table member (
    member_id   number,         --내부 관리 아이디
    email       varchar2(50),   --로긴 아이디
    passwd      varchar2(12),   --로긴 비밀번호
    nickname    varchar2(30),   --별칭
    gender      varchar2(6),    --성별
    hobby       varchar2(300),  --취미
    region      varchar2(30)    --지역
);
--기본키생성
alter table member add Constraint member_member_id_pk primary key (member_id);

--제약조건
alter table member modify email constraint member_passwd_uk unique;
alter table member modify email constraint member_passwd_nn not null;
alter table member add constraint member_gender_ck check (gender in ('남자','여자'));

--시퀀스
drop sequence member_member_id_seq;
create sequence member_member_id_seq;

desc member;

insert into member values(member_member_id_seq.nextval, 'test1@kh.com', '1234', '테스터1','남자','등산','울산');
insert into member values(member_member_id_seq.nextval, 'test2@kh.com', '1234', '테스터2','여자','골프','서울');

select * from member;
commit;



drop table notice;
create table notice(
    notice_id number(8),
    subject  varchar2(100),
    content  clob,
    author   varchar2(12),
    hit      number(5) default 0,
    cdata    timestamp default systimestamp,
    udata   timestamp
    );
    
    desc notice;

--기본키생성
alter table notice add Constraint notice_notice_id_pk primary key (notice_id);

--제약조건 not null
alter table notice modify subject constraint notice_subject_nn not null;
alter table notice modify content constraint notice_content_nn not null;
alter table notice modify author constraint notice_author_nn not null;

--시퀀스
drop sequence notice_notice_id_seq;

create sequence notice_notice_id_seq
start With 1
increment by 1
minvalue 0
maxvalue 99999999
nocycle;

--등록
insert into notice (notice_id,subject,content,author)
values(notice_notice_id_seq.nextval, '제목1','본문1','작성자1');

insert into notice (notice_id,subject,content,author)
values(notice_notice_id_seq.nextval, '제목2','본문2','작성자2');

--널뛰기금지
alter sequence notice_notice_id_seq nocache;

--상세조회
SELECT subject, content, author, cdata
     FROM notice
 where notice_id = 1;
 
--수정
update notice
    set subject ='수정후 제목',
        content = '수정후 본문',
        udate = systimestamp
 where notice_id = 1;

--삭제
delete FROM notice
 where notice_id = 1;

--조회수
update notice
    set hit = hit +1
 where notice_id = 1;

--전체조회
select * from notice;



commit;
rollback;







