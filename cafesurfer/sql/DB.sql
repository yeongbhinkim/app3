-- 테이블 삭제
DROP TABLE bookmark CASCADE CONSTRAINTS;
DROP TABLE coffeeshop CASCADE CONSTRAINTS;
DROP TABLE hashtag_classification CASCADE CONSTRAINTS;
DROP TABLE membership CASCADE CONSTRAINTS;
DROP TABLE photo CASCADE CONSTRAINTS;
DROP TABLE report CASCADE CONSTRAINTS;
DROP TABLE report_type CASCADE CONSTRAINTS;
DROP TABLE review CASCADE CONSTRAINTS;
DROP TABLE shop_hashtag CASCADE CONSTRAINTS;

-- 시퀀스 삭제
DROP SEQUENCE membership_member_id_seq;
DROP SEQUENCE coffeeshop_shop_id_seq;
DROP SEQUENCE review_review_id_seq;
DROP SEQUENCE hashtag_menu_id_seq;
DROP SEQUENCE hashtag_loc_id_seq;
DROP SEQUENCE hashtag_mood_id_seq;
DROP SEQUENCE hashtag_view_id_seq;

-- 시퀀스 생성
create sequence membership_member_id_seq
    start with 1
    increment by 1
    minvalue 0
    maxvalue 99999999
    nocycle
    nocache;

 create sequence coffeeshop_shop_id_seq
    start with 1
    increment by 1
    minvalue 0
    maxvalue 99999999
    nocycle
    nocache;

create sequence review_review_id_seq
    start with 1
    increment by 1
    minvalue 0
    maxvalue 99999999
    nocycle
    nocache;

 create sequence hashtag_menu_id_seq
    start with 1001
    increment by 1
    minvalue 1000
    maxvalue 2000
    nocycle
    nocache;

 create sequence hashtag_loc_id_seq
    start with 2001
    increment by 1
    minvalue 2000
    maxvalue 3000
    nocycle
    nocache;

 create sequence hashtag_mood_id_seq
    start with 3001
    increment by 1
    minvalue 3001
    maxvalue 4000
    nocycle
    nocache;

 create sequence hashtag_view_id_seq
    start with 4001
    increment by 1
    minvalue 4001
    maxvalue 5000
    nocycle
    nocache;

-- 테이블 생성
CREATE TABLE bookmark (
    bookmark_checked NUMBER DEFAULT 0 NOT NULL,
    bookmark_chkdate TIMESTAMP,
    member_id        NUMBER(5) NOT NULL,
    shop_id          NUMBER(5) NOT NULL
);

COMMENT ON COLUMN bookmark.bookmark_checked IS
    '북마크 체크 여부';

COMMENT ON COLUMN bookmark.bookmark_chkdate IS
    '북마크 체크 일자';

COMMENT ON COLUMN bookmark.member_id IS
    '멤버 식별 ID';

COMMENT ON COLUMN bookmark.shop_id IS
    '커피숍 ID';

CREATE TABLE coffeeshop (
    shop_id             NUMBER(5) NOT NULL,
    shop_name           NVARCHAR2(30) NOT NULL,
    shop_address        VARCHAR2(105) NOT NULL,
    shop_tel            VARCHAR2(15) NOT NULL,
    view_count          NUMBER(5) DEFAULT 0,
    shop_bookmark_count NUMBER(5) DEFAULT 0,
    shop_review_count   NUMBER(5) DEFAULT 0,
    parking             NUMBER DEFAULT 0 NOT NULL,
    "24HOURS"           NUMBER DEFAULT 0 NOT NULL,
    shop_cdate          TIMESTAMP DEFAULT systimestamp NOT NULL
);

COMMENT ON COLUMN coffeeshop.shop_id IS
    '커피숍 ID';

COMMENT ON COLUMN coffeeshop.shop_name IS
    '커피숍 이름';

COMMENT ON COLUMN coffeeshop.shop_address IS
    '커피숍 주소';

COMMENT ON COLUMN coffeeshop.shop_tel IS
    '커피숍 전화번호 (''-'' 포함)';

COMMENT ON COLUMN coffeeshop.view_count IS
    '커피숍 조회 카운트';

COMMENT ON COLUMN coffeeshop.shop_bookmark_count IS
    '북마크 등록 카운트';

COMMENT ON COLUMN coffeeshop.shop_review_count IS
    '등록된 리뷰 개수 카운트';

COMMENT ON COLUMN coffeeshop.parking IS
    '주차시설 여부';

COMMENT ON COLUMN coffeeshop."24HOURS" IS
    '24시간 운영 여부';

COMMENT ON COLUMN coffeeshop.shop_cdate IS
    '커피숍 등록 일자';

ALTER TABLE coffeeshop ADD CONSTRAINT coffeeshop_pk PRIMARY KEY ( shop_id );

CREATE TABLE hashtag_classification (
    hashtag_id           NUMBER(5) NOT NULL,
    hashtag_name         VARCHAR2(30) NOT NULL,
    hashtag_search_count NUMBER(5) DEFAULT 0 NOT NULL
);

COMMENT ON COLUMN hashtag_classification.hashtag_id IS
    '해시태그 ID';

COMMENT ON COLUMN hashtag_classification.hashtag_name IS
    '해시태그 이름';

COMMENT ON COLUMN hashtag_classification.hashtag_search_count IS
    '해시태그 검색에 사용된 횟수';

ALTER TABLE hashtag_classification ADD CONSTRAINT hashtag_classification_pk PRIMARY KEY ( hashtag_id );

CREATE TABLE membership (
    member_id     NUMBER(5) NOT NULL,
    member_email  VARCHAR2(40) NOT NULL,
    member_passwd VARCHAR2(20) NOT NULL,
    member_name   VARCHAR2(15) NOT NULL,
    member_gender CHAR(3) NOT NULL,
    member_age    NUMBER(3) NOT NULL,
    member_tel    VARCHAR2(15) NOT NULL,
    member_owner  NUMBER DEFAULT 0
);

ALTER TABLE membership
    ADD CHECK ( member_gender IN ( '남', '여' ) );

COMMENT ON COLUMN membership.member_id IS
    '멤버 식별 ID';

COMMENT ON COLUMN membership.member_email IS
    '멤버 이메일';

COMMENT ON COLUMN membership.member_passwd IS
    '멤버 비밀번호';
    
COMMENT ON COLUMN membership.member_name IS
    '멤버 이름';

COMMENT ON COLUMN membership.member_gender IS
    '멤버 성별';

COMMENT ON COLUMN membership.member_age IS
    '멤버 나이';

COMMENT ON COLUMN membership.member_tel IS
    '멤버 연락처 (''-'' 포함)';

COMMENT ON COLUMN membership.member_owner IS
    '점주회원 식별';

ALTER TABLE membership ADD CONSTRAINT membership_pk PRIMARY KEY ( member_id );

CREATE TABLE photo (
    photo   BLOB,
    shop_id NUMBER(5) NOT NULL
);

COMMENT ON COLUMN photo.photo IS
    '사진';

CREATE TABLE report (
    review_id        NUMBER(5) NOT NULL,
    report_type_code NUMBER(1) NOT NULL
);

COMMENT ON COLUMN report.review_id IS
    '리뷰 식별 ID';

COMMENT ON COLUMN report.report_type_code IS
    '신고 사유 코드
0=스팸(부적절한 홍보), 1=음란물, 2=혐오표현, 3=욕설 및 비방';

CREATE TABLE report_type (
    report_type_code NUMBER(1) NOT NULL,
    report_type_name VARCHAR2(30) NOT NULL
);

COMMENT ON COLUMN report_type.report_type_code IS
    '신고 사유 코드
0=스팸(부적절한 홍보), 1=음란물, 2=혐오표현, 3=욕설 및 비방';

ALTER TABLE report_type ADD CONSTRAINT reporttype_code_pk PRIMARY KEY ( report_type_code );

CREATE TABLE review (
    review_id      NUMBER(5) NOT NULL,
    review_content CLOB NOT NULL,
    review_cdate   TIMESTAMP DEFAULT systimestamp NOT NULL,
    report_count   NUMBER(3) DEFAULT 0,
    member_id      NUMBER(5) NOT NULL,
    shop_id        NUMBER(5) NOT NULL
);

COMMENT ON COLUMN review.review_id IS
    '리뷰 식별 ID';

COMMENT ON COLUMN review.review_content IS
    '리뷰 내용';

COMMENT ON COLUMN review.review_cdate IS
    '리뷰 작성 일자';

COMMENT ON COLUMN review.report_count IS
    '리뷰의 신고된 횟수';

COMMENT ON COLUMN review.member_id IS
    '멤버 식별 ID';

COMMENT ON COLUMN review.shop_id IS
    '커피숍 ID';

ALTER TABLE review ADD CONSTRAINT review_pk PRIMARY KEY ( review_id );

CREATE TABLE shop_hashtag (
    shop_id    NUMBER(5) NOT NULL,
    hashtag_id NUMBER(5) NOT NULL
);

COMMENT ON COLUMN shop_hashtag.shop_id IS
    '커피숍 ID';

COMMENT ON COLUMN shop_hashtag.hashtag_id IS
    '해시태그 ID';

ALTER TABLE bookmark
    ADD CONSTRAINT bookmark_coffeeshop_fk FOREIGN KEY ( shop_id )
        REFERENCES coffeeshop ( shop_id );

ALTER TABLE bookmark
    ADD CONSTRAINT bookmark_membership_fk FOREIGN KEY ( member_id )
        REFERENCES membership ( member_id );

ALTER TABLE photo
    ADD CONSTRAINT photo_coffeeshop_fk FOREIGN KEY ( shop_id )
        REFERENCES coffeeshop ( shop_id );

ALTER TABLE report
    ADD CONSTRAINT report_reporttype_code_fk FOREIGN KEY ( report_type_code )
        REFERENCES report_type ( report_type_code );

ALTER TABLE report
    ADD CONSTRAINT report_review_fk FOREIGN KEY ( review_id )
        REFERENCES review ( review_id );

ALTER TABLE review
    ADD CONSTRAINT review_coffeeshop_fk FOREIGN KEY ( shop_id )
        REFERENCES coffeeshop ( shop_id );

ALTER TABLE review
    ADD CONSTRAINT review_membership_fk FOREIGN KEY ( member_id )
        REFERENCES membership ( member_id );

ALTER TABLE shop_hashtag
    ADD CONSTRAINT shop_hashtag_classification_fk FOREIGN KEY ( hashtag_id )
        REFERENCES hashtag_classification ( hashtag_id );

ALTER TABLE shop_hashtag
    ADD CONSTRAINT shop_hashtag_coffeeshop_fk FOREIGN KEY ( shop_id )
        REFERENCES coffeeshop ( shop_id );
        

-------------------------------------------------------------------

-- 데이터 CRUD

 -- 회원정보
 -- insert
 insert into membership
 values(membership_member_id_seq.nextval, '홍길순', 'hong3@kh.com', '여', 20, '010-5678-7891', default);

 -- update
 update membership set member_tel='010-4567-8901'
 where member_id = 1;

 -- delete
 delete from membership
 where member_id = 1;

-- read
 select * from membership;

 -- 커피숍 정보
 -- insert
 insert into coffeeshop
 values(coffeeshop_shop_id_seq.nextval, '카페 솔츠', '울산 남구 봉월로8번길 18 1층', '052-710-5252', default, default, default, 1, 0, default);

 -- update
 update coffeeshop set shop_name = '스타벅스'
 where shop_id = 1;

 -- delete
 delete from coffeeshop
 where shop_id = 1;

 -- read
 select * from coffeeshop;

 -- 리뷰
 -- insert
 insert into review
 values(review_review_id_seq.nextval, '홍길순이 카페 솔츠에 남긴 댓글2', default, default, 2, 2);

 -- delete
 delete from review
 where review_id = 1;

 select * from review;

 -- 신고 유형 (값 고정)
 -- insert
 insert into report_type
 values(0, '스팸(부적절한 홍보)');

 insert into report_type
 values(1, '음란물');

 insert into report_type
 values(2, '혐오표현');

 insert into report_type
 values(3, '욕설 및 비방');

 --read
 select * from report_type;

 -- 리뷰 신고
 -- insert
 insert into report
 values(5, 0);

 -- 신고를 받을 경우 review테이블의 repot_count증가

 -- read (신고된 리뷰의 내용과 신고 유형 확인)
 select t1.review_content "리뷰 내용", t2.report_type_name "신고 사유"
 from review t1, report_type t2, report t3
 where t1.review_id = t3.review_id
 and t3.report_type_code_id = t2.report_type_code;

 -- 찜하기
 -- insert
 insert into bookmark
 values(0, null, 2, 2);

 -- update
 update bookmark set bookmark_checked = 1, bookmark_chkdate = systimestamp
 where coffeeshop_shop_id = 1
 and membership_member_id = 1;

 select * from bookmark;

 -- 해시태그 식별
 -- (메뉴 1001-2000)
 -- insert
 insert into hashtag_classification
 values(hashtag_menu_id_seq.nextval, '캬라멜 마끼아또', default);

 -- read
 select * from hashtag_classification;

 -- (지역 2001-3000)
 insert into hashtag_classification
 values(hashtag_loc_id_seq.nextval, '울산 울주군', default);

 -- (분위기 3001-4000)
 insert into hashtag_classification
 values(hashtag_mood_id_seq.nextval, '따뜻함', default);

 -- (뷰 4001-5000)
 insert into hashtag_classification
 values(hashtag_view_id_seq.nextval, '오션뷰', default);

  -- read
 select * from hashtag_classification
 order by hashtag_id;

 -- 커피숍 해시태그
 -- insert
 insert into shop_hashtag
 values(1, 1002);
        