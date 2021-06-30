/*----------------VISTA EMPLEADO----------------*/
create view Vis_empleado 
as
/*select Cedula , Apellido1 as 'Apellido 1', Apellido2, Nombre1, YEAR(CURDATE())-YEAR(F_nac)+if(date_format(curdate(),'%m-%d')>date_format(F_nac,'%m-%d'),0,-1) AS 'Edad',Tipo_sangre,Telefono1,Telefono2 from empleado order by Apellido1 asc;*/
select Cedula, Apellido1, Apellido2, Nombre1,(select Cargo from CARGO_EMPL C where C.Id_cargo=E.Id_cargo) as 'Cargo' ,TIMESTAMPDIFF(YEAR,F_nac,CURDATE()) AS 'Edad' ,Tipo_sangre ,Telefono1 ,Telefono2 from empleado E order by Apellido1 asc;
/*select*from Vis_empleado;*/


create view Vis_puesto
as
select Id_puesto, N_delta, Nombre_puesto, Telefono, e_mail from PUESTO order by N_delta asc;
/*select*from Vis_puesto;
describe Vis_puesto;
select*from puesto;
select*from DIR_PUESTO;
Select*from CLIENTE_PUESTO;*/
create view Vis_Usuario
as
select Id_User, Usuario, Contra, (select Rol from ROL where Id_Rol=USUARIO.Id_Rol ) 'Rol' from USUARIO;
/*select * from Vis_Usuario;
describe Vis_Usuario;*/


/*create view Vis_horario 
as*/
/*Delta, PUESTO,NOMBRE,APELLIDO LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMIGO*/
/*select P.N_delta, P.Fecha, EMP.Apellido1 from PUESTO P, EMPLEADO EMP where P.Id_empl=EMP.Id_empl order by N_delta asc;
select*from Vis_horario;

select*from horario;
select day(Fecha) as 'num', dayname(Fecha) as 'dia' from horario where Id_empl=1;

select Fecha,Id_empl from horario where Fecha between '2021-01-5' and '2021-01-25'; */


