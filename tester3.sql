--테이블 제거
drop table score;
drop table student;
drop table subject;

--테이블 생성
create table student(
    student_id  NUMBER(4),
    name        VARCHAR2(12)
);

create table subject(
    subject_id  NUMBER(3),
    name        VARCHAR2(30)
);

create table  score(
    score_id     NUMBER,
    student_id   NUMBER(4),
    subject_id   NUMBER(3),
    jumsu        number(3)
);
--기본키 생성
alter table student add constraint student_student_id_pk primary key(student_id);
alter table subject add constraint subject_subject_id_pk primary key(subject_id);
alter table score add constraint score_score_id_pk primary key(score_id);

--외래키 생성
alter table score add constraint score_student_id_fkl foreign key(student_id) references student(student_id);
alter table score add constraint score_subject_id_fk2 foreign key(subject_id) references subject(subject_id);

--기타 제약조건
alter table student modify name CONSTRAINT student_name_nn not null;
alter table subject modify name CONSTRAINT subject_name_nn not null;

--시퀀스 생성
drop SEQUENCE student_student_id_seq;
drop SEQUENCE subject_subject_id_seq;
drop SEQUENCE score_score_id_seq;

create SEQUENCE student_student_id_seq start with 1001 INCREMENT by 1;
create SEQUENCE subject_subject_id_seq start with 101 INCREMENT by 1;
create SEQUENCE score_score_id_seq;

--샘플데이터
insert into student values(student_student_id_seq.nextval, '홍길동1');
insert into student values(student_student_id_seq.nextval, '홍길동2');
insert into student values(student_student_id_seq.nextval, '홍길동3');
insert into student values(student_student_id_seq.nextval, '홍길동4');
insert into student values(student_student_id_seq.nextval, '홍길동5');
insert into student values(student_student_id_seq.nextval, '홍길동6');

insert into subject values(subject_subject_id_seq.nextval,'국어');
insert into subject values(subject_subject_id_seq.nextval,'영어');
insert into subject values(subject_subject_id_seq.nextval,'수학');

insert into score values(score_score_id_seq.nextval,1001,101,90); 
insert into score values(score_score_id_seq.nextval,1001,102,80);
insert into score values(score_score_id_seq.nextval,1002,102,70);
insert into score values(score_score_id_seq.nextval,1003,101,80);
insert into score values(score_score_id_seq.nextval,1003,102,80);
insert into score values(score_score_id_seq.nextval,1003,103,80);
insert into score values(score_score_id_seq.nextval,1004,101,50);
insert into score values(score_score_id_seq.nextval,1006,101,90);

commit;

SELECT t2.student_id, t2.name, t3.name, t1.jumsu 
    FROM score t1, student t2, subject t3
    where t1.student_id = t2.student_id
    and   t1.subject_id = t3.subject_id
order by t2.name, t3.name;

--5번 김영빈
select t1.student_id, t1.name
    from student t1, score t2, subject t3
    where t1.student_id = t2.student_id
    and   t2.subject_id= t3.subject_id
    and   t2.jumsu >=80
    and   t2.subject_id = 101;
    
--6번   
select t1.name
    from student t1, score t2, subject t3
   where t1.student_id = t2.student_id
    and   t2.subject_id= t3.subject_id
   and t2.subject_id= (select subject_id
                        from( select subject_id,avg(jumsu)
                         from score
                         group by subject_id
                         order by avg(jumsu) desc)
                           where rownum = 1);

--8번
select rownum, name
from(select t1.name
    from  student t1 ,score t2
    where t1.student_id = t2.student_id
    and t2.subject_id = 103
    order by t2.jumsu desc)
where rownum = 1;

--
--1권영경	평균 상위 3명의 학번과 이름, 평균을 구하시오.
select student_id, name, avg(jumsu)
    from(select t1.student_id, t1.name, avg(t2.jumsu)
            from student t1 ,score t2
            group by t1.student_id, t1.name
            order by avg(t2.jumsu))
            where rownum >=3;




select name "이름", avg "평균"
  from (select t2.student_id, t2.name, avg(t1.jumsu) avg
            from score t1, student t2
            where t1.student_id = t2.student_id
         group by t2.student_id, t2.name
         order by avg desc) u1
 where rownum <= 3;
--2김강현	국어 점수가 가장 낮은 사람의 평균을 구하시오
select n2.student_id "학번", n2.name "이름", avg(n1.jumsu) "평균"
  from score n1, student n2, subject n3
 where n1.student_id = n2.student_id 
   and n1.subject_id = n3.subject_id
   and n2.student_id = (
                        select o2.student_id
                          from score o1, student o2, subject o3
                         where o1.student_id = o2.student_id 
                           and o1.subject_id = o3.subject_id
                           and o3.name = '국어'
                           and o1.jumsu = ( select min(t1.jumsu)
                                              from score t1, subject t2
                                             where t1.subject_id = t2.subject_id
                                               and t2.name = '국어') )
group by n2.student_id,n2.name
order by avg(n1.jumsu) ;
                       
--3김무년	각 과목별로 최고점수를 받은 사람의 이름과 점수를 출력하시오.
select o3.name, o2.student_id "학번", o2.name "이름", o1.jumsu "점수"
  from score o1, student o2, subject o3
 where o1.student_id = o2.student_id
   and o1.subject_id = o3.subject_id
   and (o1.subject_id, o1.jumsu) in ( select t2.subject_id, max(t1.jumsu)
                                        from score t1, subject t2, student t3
                                       where t1.student_id = t3.student_id
                                         and t1.subject_id = t2.subject_id
                                    group by t2.subject_id )
order by o3.name;

--4김소라	영어점수가 80점 미만인 학생의 이름을 출력하시오
select t3.name "이름", t1.jumsu "영어점수"
  from score t1, subject t2, student t3
 where t1.student_id = t3.student_id
   and t1.subject_id = t2.subject_id
   and t2.name = '영어'
   and t1.jumsu < 80;
   
--5김영빈	국어 점수가 80점 이상인 학생의 학번과 이름을 출력하세요
select t3.name "이름", t2.name "과목", t1.jumsu "점수"
  from score t1, subject t2, student t3
 where t1.student_id = t3.student_id
   and t1.subject_id = t2.subject_id
   and t2.name = '국어'
   and t1.jumsu >= 80;

--6김재엽	평균이 가장 높은 과목을 수강하는 학생의 이름을 구하시오.
select o3.name
  from score o1, subject o2, student o3
 where o1.student_id = o3.student_id
   and o1.subject_id = o2.subject_id
   and o1.subject_id in (   select subject_id
                              from (select t2.subject_id, avg(t1.jumsu) avg
                                      from score t1, subject t2, student t3
                                     where t1.student_id = t3.student_id
                                       and t1.subject_id = t2.subject_id
                                  group by t2.subject_id
                                  order by avg(t1.jumsu) desc )
                             where rownum = 1 );
                             
--7박성모	각 과목별 최하점을 받은 사람의 학번, 이름, 점수를 출력하시오.
select o3.name, o2.student_id "학번", o2.name "이름", o1.jumsu "점수"
  from score o1, student o2, subject o3
 where o1.student_id = o2.student_id
   and o1.subject_id = o3.subject_id
   and (o1.subject_id, o1.jumsu) in ( select t2.subject_id, min(t1.jumsu)
                                        from score t1, subject t2, student t3
                                       where t1.student_id = t3.student_id
                                         and t1.subject_id = t2.subject_id
                                    group by t2.subject_id )
order by o3.name;
--8박현근	수학 점수가 가장 높은 사람 이름을 출력하시오.
select o2.student_id "학번", o2.name "이름", o1.jumsu "점수"
  from score o1, student o2 , subject o3
 where o1.student_id = o2.student_id
   and o1.subject_id = o3.subject_id
   and o3.name = '수학'
   and o1.jumsu = ( select max(i1.jumsu)
                       from score i1, subject i2
                      where i1.subject_id = i2.subject_id
                        and i2.name = '수학' );
--9배지희	점수가 없는 학생의 학번과 이름을 출력하시오.
select t1.student_id "학번", t1.name "이름"
  from student t1 left outer join score t2
                  on t1.student_id = t2.student_id
 where t2.student_id is null; 
 
--left outer join은 페이지를 붙이는 느낌?
 
 
--10유기상	과목별 석차를 구하시오.
   select t2.name "과목", t3.name "이름", t1.jumsu "석차",
          rank() over (partition by t2.subject_id order by t1.jumsu desc) "순위"        
     from score t1, subject t2, student t3
    where t1.student_id = t3.student_id
      and t1.subject_id = t2.subject_id;

--11이규민	국어 1등 점수와 영어 꼴등점수 차이 값을 구하시오.
select (
    select max(t1.jumsu)
      from score t1, subject t2
     where t1.subject_id = t2.subject_id
       and t2.name = '국어' )
    -
    (select min(t1.jumsu)
      from score t1, subject t2
     where t1.subject_id = t2.subject_id
       and t2.name = '영어') "차이"
from dual;
--12이준혁	과목별 평균보다 높은 점수를 받은 학생들의 이름을 출력하시오.
select o2.name "이름", o3.name "과목", o1.jumsu "점수"
  from score o1, student o2, subject o3
 where o1.student_id = o2.student_id
   and o1.subject_id = o3.subject_id
   and o1.jumsu > (
            select avg(t1.jumsu)
              from score t1, subject t2
             where t1.subject_id = t2.subject_id
               and t1.subject_id = o1.subject_id
          group by t2.subject_id,t2.name ); 

--13이한봄	국어 점수가 가장 높은 학생부터 낮은 학생 순으로 학생별 성적을 정렬하시오.
select t2.student_id "학번", t2.name "이름", t1.jumsu "점수"
  from score t1, student t2, subject t3
 where t1.student_id = t2.student_id
   and t1.subject_id = t3.subject_id
   and t3.name = '국어'
order by t1.jumsu desc;   

--14전은우	점수가 없는 학생의 학번과 이름을 출력하시오.
select t1.student_id "학번", t1.name "이름"
  from student t1 left outer join score t2
                  on t1.student_id = t2.student_id
 where t2.student_id is null;
 
--15조세령	평균 70점 이하인 학생의 학번, 이름을 출력하시오.
   select t2.student_id "학번", t2.name "이름", avg(t1.jumsu) "평균" 
     from score t1, student t2
    where t1.student_id = t2.student_id 
 group by t2.student_id, t2.name
   having avg(t1.jumsu) <= 70;
   
--16최수빈	영어과목중에 가장 낮은 점수를 받은 사람의 학번과 이름을 출력하시오.
select o2.student_id "학번", o2.name "이름", o1.jumsu "점수"
  from score o1, student o2 , subject o3
 where o1.student_id = o2.student_id
   and o1.subject_id = o3.subject_id
   and o3.name = '영어'
   and o1.jumsu = ( select min(i1.jumsu)
                       from score i1, subject i2
                      where i1.subject_id = i2.subject_id
                        and i2.name = '영어' );
   
--17최재훈	국어의 평균값과 영어의 평균값을 산출하고 국어의 평균값보다 점수가 작은 사람들의 합과 영어의 평균값보다 점수가 작은 사람들의 합의 차이를 절대값으로 구하시오 (모든 값은 소수첫번째에서 올림이다) / 전 못 풉니다
select abs((select sum(jumsu)
          from score o1, student o2, subject o3
         where o1.student_id = o2.student_id
           and o1.subject_id = o3.subject_id
           and o3.name = '국어'
           and o1.jumsu < ( select avg(t1.jumsu)
                              from score t1, subject t2
                             where t1.subject_id = t2.subject_id
                               and t2.name = '국어' )
           )-  
           (select sum(jumsu)
              from score o1, student o2, subject o3
             where o1.student_id = o2.student_id
               and o1.subject_id = o3.subject_id
               and o3.name = '영어'   
               and o1.jumsu < ( select avg(t1.jumsu)
                                  from score t1, subject t2
                                 where t1.subject_id = t2.subject_id
                                   and t2.name = '영어' ))) "차이"
from dual;                               
   

--18하태우	평균점수 70점~90점 사이에 있는 학생의 이름과 성적을 성적순으로 출력하시오.
select o2.name "이름", avg(o1.jumsu) "점수"
  from score o1, student o2, subject o3
 where o1.student_id = o2.student_id
   and o1.subject_id = o3.subject_id
   and o2.student_id in (  select t2.student_id
                            from score t1, student t2, subject t3
                             where t1.student_id = t2.student_id
                             and t1.subject_id = t3.subject_id
                            group by t2.student_id
                            having avg(jumsu) between 70 and 90 )
group by o2.student_id, o2.name
order by avg(o1.jumsu) desc;
    
   

select * from student;
select * from subject;
select * from score;





