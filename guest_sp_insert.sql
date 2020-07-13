create or replace procedure guest_sp_insert(
p_sabun in number,
p_name in varchar2,
p_title in varchar2,
p_pay in number,
p_hit in number,
p_email in varchar2)
is
begin
insert into guest(sabun,name,title, wdate, pay, hit, email)
values(p_sabun,p_name,p_title,sysdate,p_pay,p_hit,p_email);
end;
/
