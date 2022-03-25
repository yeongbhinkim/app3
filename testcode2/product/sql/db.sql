drop table product;
drop sequence product_product_id_seq;

-- 테이블 생성
create table product (
    product_id          number(5),
    name        varchar2(30),
    quantity    number(5),
    price       number(10)
);

-- 기본키 생성
alter table product add constraint product_pk primary key (product_id);

-- 시퀀스 생성
create sequence product_product_id_seq
start with 1
increment by 1
minvalue 0
maxvalue 99999999
nocycle
nocache;

alter sequence product_product_id_seq nocache;


--등록
insert into product (product_id,name,quantity,price)
values(product_product_id_seq.nextval, '커피123',1230,10012300);

--상세조회
SELECT product_id, name, quantity, price
     FROM product
 where product_id = 2;
 
--수정
update product
    set name ='수정후 제품',
        quantity = 100,
        price = 20000
where product_id = 2;

--삭제
delete FROM product
where product_id = 3;

--전체조회
select * from product;

commit;
rollback;

 
