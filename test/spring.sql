--회원
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
insert into member values(member_member_id_seq.nextval, 'test123@kh.com', '123412', '테스터2','여자','골프','서울');

select * from member;
commit;
-----------------------------------------------------------------------
--공지사항

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

--회원찾기
SELECT email
     FROM member
 where nickname = '테스터1';
-----------------------------------------------------------------------
drop TABLE bbs;
drop TABLE code;


CREATE table code(
    code_id   VARCHAR2(11),                         --코드
    decode    VARCHAR2(30),                         --코드명
    discript  clob,                                 --코드설명
    pcode_id  varchar2(11),                         --상위코드
    useyn     CHAR(1) default 'Y',       --사용여부 (사용:'Y',미사용:'N')
    cdate    TIMESTAMP default systimestamp,       --생성일시
    udate    TIMESTAMP default systimestamp        --수정일시
);

--기본키
alter table code add constraint code_code_id_pk primary key (code_id);

--외래키
alter table code add constraint bbs_pcode_id_fk
    foreign key(pcode_id) references code(code_id);

--제약조건
alter table code modify decode constraint code_decode_nn not null;
alter table code modify useyn constraint code_useyn_nn not null;
alter table code add constraint code_useyn_ck check(useyn in ('Y','N'));

--샘플데이터 of code
insert into code (code_id,decode,pcode_id,useyn) values ('F01','첨부',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F0101','파일','F01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F0102','이미지','F01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F0103','프로파일','F01','Y');

insert into code (code_id,decode,pcode_id,useyn) values ('B01','게시판',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0101','Spring','B01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0102','Database','B01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0103','Q_A','B01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0104','자유게시판','B01','Y');
commit;

select t1.pcode_id, t2.decode, t1.code_id, t1.decode
    from code t1, code t2
 where t1.pcode_id = t2.code_id
    and t1.useyn = 'Y';
    
select t1.code_id code, t1.decode decode
  from code t1, code t2
 where t1.pcode_id = t2.code_id
   and t1.useyn = 'Y'
   and t1.pcode_id = 'B01';

select t1.pcode_id pcode, t2.decode pdecode, t1.code_id ccode, t1.decode cdecode
  from code t1, code t2
 where t1.pcode_id = t2.code_id
   and t1.useyn = 'Y';

CREATE table bbs(
    bbs_id      NUMBER(10),                  --게시글 번호
    bcategory    VARCHAR2(11),               --분류카테고리
    title       VARCHAR2(150),               --제목
    email       VARCHAR2(50),                --email
    nickname    VARCHAR2(30),                --별칭
    hit         NUMBER(5) DEFAULT 0,         --조회수
    bcontent     CLOB,                       --본문
    pbbs_id     NUMBER(10),                  --부모 게시글번호
    bgroup     NUMBER(10),                   --답글그룹
    step        NUMBER(3) DEFAULT 0,         --답글단계
    bindent      NUMBER(3) DEFAULT 0,        --답글들여쓰기
    status      CHAR(1),                     --답글상태 (삭제:'D', 임시정장:'I')
    cdate       TIMESTAMP default systimestamp,      --생성일시
    udate       TIMESTAMP default systimestamp       --수정일시
);

--기본키
alter table bbs add constraint bbs_bbs_id_pk primary key (bbs_id);
--외래키
alter table bbs add constraint bbs_bcategory_fk
    foreign key(bcategory) references code(code_id);
alter table bbs add constraint bbs_pbbs_id_fk
    foreign key(pbbs_id) references bbs(bbs_id);
alter table bbs add constraint bbs_email_fk
    foreign key(email) references member(email);    
    
--제약조건
alter table bbs modify bcategory constraint bbs_bcategory_nn not null;
alter table bbs modify title constraint bbs_title_nn not null;
alter table bbs modify email constraint bbs_email_nn not null;
alter table bbs modify nickname constraint bbs_nickname_nn not null;
alter table bbs modify bcontent constraint bbs_bcontent_nn not null;

--시퀀스
drop sequence bbs_bbs_id_seq;
CREATE sequence bbs_bbs_id_seq;
--널뛰기금지
alter sequence bbs_bbs_id_seq nocache;
--샘플
insert into bbs (bbs_id,bcategory,title,email,nickname,bcontent,bgroup)
    values (bbs_bbs_id_seq.nextval,'B0101','제목2','test1@kh.com','별칭','본문3', bbs_bbs_id_seq.currval);
commit;

--목록
SELECT
    bbs_id,
    bcategory,
    title,
    email,
    nickname,
    hit,
    bcontent,
    pbbs_id,
    bgroup,
    step,
    bindent,
    status,
    cdate,
    udate
FROM
    bbs;
    
--조회   
SELECT
    bbs_id,
    bcategory,
    title,
    email,
    nickname,
    hit,
    bcontent,
    pbbs_id,
    bgroup,
    step,
    bindent,
    status,
    cdate,
    udate
FROM
    bbs
where bbs_id = 2;

--삭제
DELETE FROM bbs
WHERE
        bbs_id = 11;
select * from bbs;
rollback;

--수정    
 update bbs
    set bcategory = 'B0102',
        title ='수정후 제목2',
        bcontent = '수정후 본문2',
        udate = systimestamp
 where bbs_id = 1;   
 select * from bbs;
rollback;

SELECT COUNT(bbs_id) FROM bbs;

----답글1   
-- update bbs
--    set step = step + 1,
--        bindent = 1          
-- where bbs_id = 1;   
----답글2
-- update bbs
--    set step = step(bbs_id) + 1,
--        bindent = bindent(bbs_id) + 1          
-- where bbs_id = 21;  
-- select * from bbs;
rollback;


update bbs
   set hit = hit + 1
   where bbs_id = 1;
   
commit;

SELECT COUNT(bbs_id) FROM bbs;

-------------------------------------------------------------------
drop table uploadfile;
create table uploadfile(
    uploadfile_id   number(10),     --파일아이디
    code            varchar2(11),   --분류코드
    rid             number(10),     --참조번호(게시글번호등)    
    store_filename  varchar2(100),   --서버보관파일명
    upload_filename varchar2(100),   --업로드파일명(유저가 업로드한파일명)
    fsize           varchar2(45),   --업로드파일크기(단위byte)
    ftype           varchar2(100),   --파일유형(mimetype)
    cdate           timestamp default systimestamp, --등록일시
    udate           timestamp default systimestamp  --수정일시
);
--기본키
alter table uploadfile add constraint uploadfile_uploadfile_id_pk primary key(uploadfile_id);

--외래키
alter table uploadfile add constraint uploadfile_uploadfile_id_fk 
    foreign key(code) references code(code_id);
    
--제약조건
alter table uploadfile modify code constraint uploadfile_code_nn not null;
alter table uploadfile modify rid constraint uploadfile_rid_nn not null;
alter table uploadfile modify store_filename constraint uploadfile_store_filename_nn not null;
alter table uploadfile modify upload_filename constraint uploadfile_upload_filename_nn not null;
alter table uploadfile modify fsize constraint uploadfile_fsize_nn not null;
alter table uploadfile modify ftype constraint uploadfile_ftype_nn not null;
    
--시퀀스
drop sequence uploadfile_uploadfile_id_seq;
create sequence uploadfile_uploadfile_id_seq;

--조회
select * from member;

select * from notice;

select * from bbs;

select * from code;

INSERT INTO uploadfile (
    uploadfile_id,
    code,
    rid,
    store_filename,
    upload_filename,
    fsize,
    ftype  
) VALUES (
    uploadfile_uploadfile_id_seq.nextval,
    'F0101',
    35,
    'xxx-yyy-zz.png',
    '커피.png',
    100,
   'image/png'   
);
DELETE from uploadfile;
-----------------------------
select t1.no "번호", t1.email "이메일", t1.nickname "별칭"
from(
    select
        row_number() over (order by member_id) no,
        email,
        nickname
    from member) t1
where t1.no between 2 and 4;
-----------------------------

SELECT t1.*
    from(
SELECT
    ROW_NUMBER() OVER (ORDER BY bgroup DESC, step ASC) no,
    bbs_id,
    bcategory,
    title,
    email,
    nickname,
    hit,
    bcontent,
    pbbs_id,
    bgroup,
    step,
    bindent,
    status,
    cdate,
    udate
FROM
    bbs
    where bcategory ='B0101' ) t1
where t1.no BETWEEN 11 and 20;





ROLLBACK;
DELETE from bbs;
select * from uploadfile;
select * from member;
commit;
