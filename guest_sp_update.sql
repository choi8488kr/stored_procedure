create or replace procedure guest_sp_update(
p_sabun in number,
p_name in varchar2,
p_title in varchar2,
p_pay in number,
p_hit in number,
p_email in varchar2)
is
vn_cnt number :=0;
begin
select count(*)
into vn_cnt
from guest
where sabun=p_sabun;
if vn_cnt=0 then
insert into guest(sabun,name,title, wdate, pay, hit, email)
values(p_sabun,p_name,p_title,sysdate,p_pay,p_hit,p_email);
else
update guest
set   sabun=p_sabun,
       name=p_name,
       title=p_title,
       pay=p_pay,
       hit=p_hit,
       email=p_email
where sabun=p_sabun;
end if;
commit;
end;
/