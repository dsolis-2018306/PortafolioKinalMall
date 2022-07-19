drop database if exists DBKinalMall2018306;
create database DBKinalMall2018306;
use DBKinalMall2018306;

-- Departamentos 

	Create table Departamentos(
    codigoDepartamento int not null auto_increment,
    nombreDepartamento varchar (45) not null,
    primary key PK_codigoDepartamento (codigoDepartamento)
	);
    
-- Cargos

    Create table Cargos(
	codigoCargo int not null auto_increment,
    nombreCargo varchar(45) not null,
    primary key PK_codigoCargo (codigoCargo)
    );
 
-- Horarios 
	
    Create table Horarios(
    codigoHorario int not null auto_increment,
    horarioEntrada varchar(10) not null,
    horarioSalida varchar(10) not null,
    lunes tinyint,
    martes tinyint,
    miercoles tinyint,
    jueves tinyint,
    viernes tinyint,
    primary key PK_codigoHorario (codigoHorario)
    );
   
 -- Administracion  
    
    create table Administracion(
    codigoAdministracion int not null auto_increment,
    direccion varchar(45) not null,
    telefono varchar(8) not null,
    primary key PK_codigoAdministracion (codigoAdministracion)
    );
    
-- Empleados    
    
    create table Empleados(
    codigoEmpleado int not null auto_increment,
    nombreEmpleado varchar(45) not null,
    apellidoEmpleado varchar(45) not null,
    correoElectronico varchar(45) not null,
    telefonoEmpleado varchar(8) not null,
    fechaContratacion date not null,
    sueldo double (10,2)not null,
    codigoDepartamento int not null,
    codigoCargo int not null,
    codigoHorario int not null,
    codigoAdministracion int not null,
    primary key PK_codigoEmpleado (codigoEmpleado),
	constraint FK_Empleados_Departamentos foreign key
		(codigoDepartamento) references Departamentos(codigoDepartamento),
    constraint FK_Empleados_Cargos foreign key
		(codigoCargo) references Cargos(codigoCargo),
	constraint FK_Empleados_Horarios foreign key
		(codigohorario) references Horarios(codigoHorario),
    constraint FK_Empleados_Administracion foreign key
		(codigoAdministracion) references Administracion(codigoAdministracion)    
	);
    
-- Proveedores    
    
    create table Proveedores(
    codigoProveedor int not null auto_increment,
    NITProveedor varchar(45) not null,
    servicioPrestado varchar(45) not null,
    telefonoProveedor varchar(45) not null,
    direccionProveedor varchar(45) not null,
    saldoFavor double(11,2) not null,
    saldoContra double(11,2) not null,
    codigoAdministracion int not null,
    primary key PK_codigoProveedor (codigoProveedor),
    constraint FK_Proveedores_Administracion foreign key
		(codigoAdministracion) references Administracion(codigoAdministracion)
    );
    
-- Cuentas Por Pagar    
    
    create table CuentasPorPagar(
    codigoCuentasPorPagar int not null auto_increment,
    numeroFactura varchar(45) not null,
    fechaLimitePago varchar(45) not null,
    estadoPago varchar(45) not null,
    valorNetoPago double(11,2) not null,
    codigoAdministracion int not null,
    codigoProveedor int not null,
    primary key PK_codigoCuentasPorPagar (codigoCuentasPorPagar ),
    constraint FK_CuentasPorPagar_Administracion foreign key
		(codigoAdministracion) references Administracion(codigoAdministracion),
    constraint FK_CuentasPorPagar_Proveedores foreign key
		(codigoProveedor) references Proveedores(codigoProveedor)
    );
    
-- TipoCliente    
    
    create table TipoCliente(
    codigoTipoCliente int not null auto_increment,
    descripcion varchar(45) not null,
    primary key PK_codigoTipoCliente (codigoTipoCliente)
    );
    
-- Locales    
    
    create table Locales(
    codigoLocal int not null auto_increment,
    saldoFavor double(11,2) default 0.0,
    saldoContra double(11,2) default 0.0,
    mesesPendientes int default 0,
    disponibilidad boolean not null,
    valorLocal double(11,2) not null,
    valorAdministracion double (11,2) not null,
    primary key PK_codigoLocal (codigoLocal)
    );
    
-- Clientes    
    
    create table Clientes(
    codigoCliente int not null auto_increment,
    nombresClientes varchar(45) not null,
    apellidosClientes varchar(45) not null,
    telefonoCliente varchar(8) not null,
    direccionCliente varchar(60) not null,
    emailCliente varchar(45) not null,
    codigoLocal int not null,
    codigoAdministracion int not null,
    codigoTipoCliente int not null,
    primary key PK_codigoCliente (codigoCliente),
    constraint FK_Clientes_Locales foreign key
		(codigoLocal) references Locales(codigoLocal),
    constraint FK_Clientes_Administracion foreign key
		(codigoAdministracion) references Administracion(codigoAdministracion),
    constraint FK_Clientes_TipoCliente foreign key
		(codigoTipoCliente) references TipoCliente(codigoTipoCliente)
	);
    
-- CuentasPorCobrar    
    
    create table CuentasPorCobrar(
    codigoCuentasPorCobrar int not null auto_increment,
    numeroFactura varchar(45) not null,
    anio int(4) not null,
    mes int (2) not null,
    valorNetoPago double(11,2) not null,
    estadoPago varchar(45) not null,
    codigoAdministracion int not null,
	codigoCliente int not null,
    codigoLocal int not null,
    primary key PK_codigoCuentasPorCobrar (codigoCuentasPorCobrar),
    constraint FK_CuentasPorCobrar_Administracion foreign key
		(codigoAdministracion) references Administracion(codigoAdministracion),
	constraint FK_CuentasPorCobrar_Clientes foreign key
		(codigoCliente) references Clientes(codigoCliente),
	constraint FK_CuentasPorCobrar_Locales foreign key
		(codigoLocal) references Locales(codigoLocal)
	);
    
-- Procedimiento Almacenados
-- Departamento
    
DELIMITER $$
	Create procedure sp_AgregarDepartamento (in nombreDepartamento varchar(45))
		begin 
        insert into Departamentos(nombreDepartamento)
        values (nombreDepartamento);
    END $$
Delimiter ;    

Delimiter $$
	Create Procedure sp_ListarDepartamento()
		begin 
		select
			Departamentos.codigoDepartamento,
            Departamentos.nombreDepartamento
		from Departamentos;
	end $$
Delimiter ;

Delimiter $$
	Create Procedure sp_buscarDepartamento(in numDepartamentos int)
		begin 
        select
			Departamentos.codigoDepartamento,
            Departamentos.nombreDepartamento
		from Departamentos where codigoDepartamento= numDepartamentos;
	end $$
Delimiter ;
Delimiter $$ 
	Create Procedure sp_borrarDepartamentos(in numDepartamento int)
		begin
		delete from Departamentos where codigoDepartamento = numDepartamento;
	end $$
Delimiter ;

Delimiter $$
	Create Procedure sp_EditarDepartemento(in numDepartamento int, in newNombreDepartamento varchar(45))
		begin 
			update Departamentos 
            set nombreDepartamento = newNombreDepartamento
		where codigoDepartamento = numDepartamento;
     end $$
Delimiter ;





-- Procedimiento Almacenados
-- Cargos


Delimiter $$	
    Create procedure sp_AgregarCargo ( in nombreCargo varchar(45))
		begin
		insert into Cargos(nombreCargo)
			values (nombreCargo);
	end$$
Delimiter ;

Delimiter $$ 
	Create Procedure sp_ListarCargo()
		begin 
		select 
			Cargos.codigoCargo,
            Cargos.nombreCargo
		from Cargos;
	end $$
Delimiter;

Delimiter $$
	Create Procedure sp_BuscarCargo (in numCargo int)
		begin
        select 
			Cargo.codigoCargo,
            cargo.nombreCargo
		from Cargos where codigoCargo = numCargo;
	end $$
Delimiter ;

Delimiter $$
	Create Procedure sp_BorrarCargo (in numCargo int)
		begin 
		delete from Cargos where codigoCargo = numCargo;
	end $$
Delimiter ;

Delimiter $$
	Create Procedure sp_EditarCargo (in numCargo int, in newNombreCargo varchar(45))
		begin 
		update Cargos
        set nombreCargo = newNombreCargo
        where codigoCargo = numCargo;
	end $$
Delimiter ;



-- Procedimiento Almacenados
-- Horarios 


Delimiter $$
	Create procedure sp_AgregarHorario ( in horarioEntrada varchar(10), in horarioSalida varchar(10),in lunes tinyint, in martes tinyint, in miercoles tinyint, in jueves tinyint, in viernes tinyint)
		begin
        insert into Horarios(horarioEntrada,horarioSalida,lunes,martes,miercoles,jueves,viernes)
			values (horarioEntrada,horarioSalida,lunes,martes,miercoles,jueves,viernes);
    end$$
Delimiter ;

Delimiter $$
	Create Procedure sp_ListarHorario ()
		begin 
		select 
			Horarios.codigoHorario,
            Horarios.horarioEntrada,
            Horarios.horarioSalida,
            Horarios.lunes,
            Horarios.martes,
            Horarios.miercoles,
            Horarios.jueves,
            Horarios.viernes
		from Horarios;
	end $$
Delimiter ;

Delimiter $$
	Create Procedure sp_BuscarHorario (in numHorario int)
		begin 
		select
				Horarios.codigoHorario,
                Horarios.horarioEntrada,
                Horarios.horarioSalida,
                Horarios.lunes,
                Horarios.martes,
                Horarios.miercoles,
                Horarios.jueves,
                Horarios.viernes
			from Horarios where codigoHorario = numHorario;
		end $$
Delimiter ;

Delimiter $$
	Create Procedure sp_BorrarHorario (in numHorario int )
		begin 
		Delete from Horarios where codigoHorario = numHorario;
	end$$
 Delimiter ;
 
 Delimiter $$
	Create Procedure sp_EditarHorario (in numHorario int,in newHorarioEntrada varchar(10), in newHorarioSalida varchar(10), in newLunes tinyint,in newMartes tinyint, in newMiercoles tinyint, in newJueves tinyint, in newViernes tinyint )
		begin 
        update Horarios
		set horarioEntrada = newHorarioEntrada,
			horarioSalida = newHorarioSalida,
            lunes = newLunes,
            martes = newMartes,
            miercoles = newMiercoles,
            jueves = newJueves,
            viernes= newViernes
		where codigoHorario = numHorario;
	end$$
Delimiter ;



-- Procedimiento Almacenados
-- Administracion 





Delimiter $$
	Create procedure sp_AgregarAdministracion(in direccion varchar(45), in telefono varchar(8))
		begin
        insert into Administracion (direccion,telefono)
			values (direccion,telefono);
    end$$
Delimiter ;

Delimiter $$
	Create Procedure sp_ListarAdministracion()
		begin
        select
			Administracion.codigoAdministracion,
            Administracion.direccion,
            Administracion.telefono
		from Administracion;
	end $$
Delimiter ;

Delimiter $$ 
	Create Procedure sp_BuscarAdministracion (in numAdministracion int)
		begin
        select 
			Administracion.codigoAdministracion,
            Administracion.direccion,
            Administracion.telefono
		from Administracion where codigoAdministracion = numAdministracion;
	end $$
 Delimiter ;
 
 Delimiter $$ 
	Create Procedure sp_BorrarAdministracion (in numAdministracion int )
		begin
        delete from Administracion where codigoAdministracion = numAdministracion;
	end$$
Delimiter ;

Delimiter $$
	Create Procedure sp_EditarAdministracion (in numAdministracion int, in newDireccion varchar(45), in newTelefono varchar(8))
		begin
        update Administracion
			set direccion = newDireccion,
				telefono = newTelefono
			where codigoAdministracion = numAdministracion;
	end $$
Delimiter ;
    
-- Procedimiento Almacenados
-- Empleados


Delimiter $$
	Create Procedure sp_AgregarEmpleado( nombreEmpleado varchar(45), in apellidoEmpleado varchar(45), in correoElectronico varchar(45), in telefonoEmpleado varchar(8), in fechaContratacion date, in sueldo double(10,2), in codigoDepartamento int, in codigoCargo int, in codigoHorario int, in codigoAdministracion int)
		begin
        insert into Empleados(nombreEmpleado,apellidoEmpleado,correoElectronico,telefonoEmpleado,fechaContratacion,sueldo,codigoDepartamento,codigoCargo,codigoHorario,codigoAdministracion)
			values (nombreEmpleado,apellidoEmpleado,correoElectronico,telefonoEmpleado,fechaContratacion,sueldo,codigoDepartamento,codigoCargo,codigoHorario,codigoAdministracion);
     end $$
Delimiter ;

Delimiter $$ 
	Create Procedure sp_ListarEmpleado()
		begin 
        select
			Empleados.codigoEmpleado,
            Empleados.nombreEmpleado,
            Empleados.apellidoEmpleado,
            Empleados.correoElectronico,
            Empleados.telefonoEmpleado,
            Empleados.fechaContratacion,
            Empleados.sueldo,
            Empleados.codigoDepartamento,
            Empleados.codigoCargo,
            Empleados.codigoHorario,
            Empleados.codigoAdministracion
		from Empleados;
	end $$
Delimiter ;

Delimiter $$
	Create Procedure sp_BuscarEmpleado(in numEmpleado int)
		begin
        select
			Empleados.codigoEmpleado,
            Empleados.nombreEmpleado,
            Empleados.apellidoEmpleado,
            Empleados.correoElectronico,
            Empleados.telefonoEmpleado,
            Empleados.fechaContratacion,
            Empleados.sueldo,
            Empleados.codigoDepartamento,
            Empleados.codigoCargo,
            Empleados.codigoHorario,
            Empleados.codigoAdministracion
		from Empleados where codigoEmpleado = numEmpleado;
	end $$
Delimiter ;

Delimiter $$ 
	Create Procedure sp_BorrarEmpleado (in numEmpleado int)
		begin
        delete from Empleados where codigoEmpleado = numEmpleado;
	end $$
Delimiter ;
Delimiter $$
	Create Procedure sp_EditarEmpleado (in numEmpleado int, in newNombreEmpleado varchar(45), in newApellidoEmpleado varchar(45), in newCorreoElectronico varchar(45), in newTelefonoEmpleado varchar(8), in newFechaContratacion date, in newSueldo double(10,2))
		begin 
			update Empleados
				set	
					nombreEmpleado = newNombreEmpleado,
					apellidoEmpleado = newApellidoEmpleado,
					correoElectronico = newCorreoElectronico,
					telefonoEmpleado = newTelefonoEmpleado,
					fechaContratacion = newfechaContratacion,
					sueldo = newSueldo
					
		where codigoEmpleado = numEmpleado;
	end $$
Delimiter ;
				
			

-- Procedimiento Almacenados
-- Proveedores



Delimiter $$ 
	Create Procedure sp_AgregarProveedor ( in NITProveedor varchar(45), in servicioPrestado varchar(45),in telefonoProveedor varchar(8), in direccionProveedor varchar(45), in saldoFavor double(11,2), in saldoContra double(11,2), in codigoAdministracion int )
		begin 
        insert into Proveedores(NITProveedor,servicioPrestado,telefonoProveedor,direccionProveedor,saldoFavor,saldoContra,codigoAdministracion)
			values (NITProveedor,servicioPrestado,telefonoProveedor,direccionProveedor,saldoFavor,saldoContra,codigoAdministracion);
	end $$
Delimiter ;

Delimiter $$
	Create Procedure sp_ListarProveedor ()
		Begin 
			select 
            Proveedores.codigoProveedor,
            Proveedores.NITProveedor,
            Proveedores.servicioPrestado,
            Proveedores.telefonoProveedor,
            Proveedores.direccionProveedor,
            Proveedores.saldoFavor,
            Proveedores.saldoContra,
            Proveedores.codigoAdministracion
		from Proveedores;
	end $$
Delimiter ;

Delimiter $$
	Create Procedure sp_BuscarProveedores (in numProveedor int )
		begin 
			select 
			Proveedores.codigoProveedor,
            Proveedores.NITProveedor,
            Proveedores.servicioPrestado,
            Proveedores.telefonoProveedor,
            Proveedores.direccionProveedor,
            Proveedores.saldoFavor,
            Proveedores.saldoContra,
            Proveedores.codigoAdministracion
		from Proveedores where codigoProveedor = numProveedor;
	end $$
Delimiter ;

Delimiter $$ 
	Create Procedure sp_BorrarProveedor (in numProveedor int)
		begin 
			delete from Proveedores where codigoProveedor = numProveedor;
		end $$
Delimiter ;
Delimiter $$
	Create Procedure sp_EditarProveedor (in numProveedor int, in newNITProveedor varchar(45), in newServicioPrestado varchar(45),in newTelefonoProveedor varchar(8), in newDireccionProveedor varchar(45), in newSaldoFavor double(11,2), in newSaldoContra double(11,2) )
		begin 
			update Proveedores
			set 
            NITProveedor = newNITProveedor,
            servicioPrestado = newServicioPrestado,
            telefonoProveedor = newTelefonoProveedor,
            direccionProveedor = newDireccionProveedor,
            saldoFavor = newSaldoFavor,
            saldoContra = newSaldoContra
            
		where codigoProveedor = numProveedor;
	end $$
Delimiter ;
    

	

-- Procedimiento Almacenados
-- Cuentas por pagar



Delimiter $$
	Create Procedure sp_AgregarCuentasPorPagar ( in numeroFactura varchar(45), in fechaLimitePago varchar(45), in estadoPago varchar(45), in valorNetoPago double(11,2), in codigoAdministracion int, in codigoProveedor int)
		begin 
        insert into CuentasPorPagar(numeroFactura,fechaLimitePago,estadoPago,valorNetoPago,codigoAdministracion,codigoProveedor)
			values (numeroFactura,fechaLimitePago,estadoPago,valorNetoPago,codigoAdministracion,codigoProveedor);
    end $$
Delimiter ;

Delimiter $$ 
	Create Procedure sp_ListarCuentasPorPagar ()
		begin
			select
				CuentasPorPagar.codigoCuentasPorPagar,
                CuentasPorPagar.numeroFactura,
                CuentasPorPagar.fechaLimitePago,
                CuentasPorPagar.estadoPago,
                CuentasPorPagar.valorNetoPago,
                CuentasPorPagar.codigoAdministracion,
                CuentasPorPagar.codigoProveedor
			from CuentasPorPagar;
		end $$
Delimiter ;

Delimiter $$ 
	Create Procedure sp_BuscarCuentasPorPagar (in numCuentasPorPagar int)
		begin 
			select
				CuentasPorPagar.codigoCuentasPorPagar,
                CuentasPorPagar.numeroFactura,
                CuentasPorPagar.fechaLimitePago,
                CuentasPorPagar.estadoPago,
                CuentasPorPagar.valorNetoPago,
                CuentasPorPagar.codigoAdministracion,
                CuentasPorPagar.codigoProveedor
			from CuentasPorPagar where codigoCuentasPorPagar = numCuentasPorPagar;
		end $$
Delimiter ;

Delimiter $$
	Create 
    Procedure sp_BorrarCuentasPorPagar (in numCuentasPorPagar int)
		begin 
			delete from CuentasPorPagar where codigoCuentasPorPagar = numCuentasPorPagar;
		end $$
Delimiter ;

Delimiter $$
	Create Procedure sp_EditarCuentasPorPagar(in numCuentasPorPagar int,in newNumeroFactura varchar(45) , in newFechaLimitePago date, in newEstadoPago varchar(45), in newValorNetoPago double(11,2))
		begin 
			update CuentasPorPagar
            set 
               numeroFactura = newNumeroFactura,    
               fechaLimitePago = newFechaLimitePago,
               estadoPago = newEstadoPago,
               valorNetoPago = newValorNetoPago
               
		
        where codigoCuentasPorPagar = numCuentasPorPagar;
	end$$
Delimiter ;
				

-- Procedimiento Almacenados
-- TipoCliente


 
Delimiter $$
		Create Procedure sp_AgregarTipoCliente( in descripcion varchar(45))
			begin 
            insert into TipoCliente(descripcion)
				values (descripcion);
         end$$
Delimiter ;

Delimiter $$
	Create Procedure sp_ListarTipoCliente()
	begin 
        select 
			TipoCliente.codigoTipoCliente,
            TipoCliente.descripcion
		from TipoCliente;
	end $$
Delimiter ;
Delimiter $$
  Create Procedure sp_BuscarTipoCliente (in numTipoCliente int)
	begin
		select 
			TipoCliente.codigoTipoCliente,
            TipoCliente.descripcion
		from TipoCliente where codigoTipoCliente = numTipoCliente;
	end$$
Delimiter ;

Delimiter $$
	Create Procedure sp_BorrarTipoCliente (in numTipoCliente int)
		begin 
			Delete from TipoCliente where codigoTipoCliente = numTipoCliente;
		end $$
Delimiter ;

Delimiter $$
	Create Procedure sp_EditarTipoCliente (in numTipoCliente int,in newDescripcion varchar(45))
		begin
			update TipoCliente
				set descripcion = newDescripcion
			where codigoTipoCliente = numTipoCliente;
		end $$
Delimiter ;


-- Procedimiento Almacenados
-- Locales


Delimiter $$
	Create Procedure sp_AgregarLocales (in saldoFavor double(11,2), in saldoContra double(11,2),in mesesPendientes int,in disponibilidad boolean, in valorLocal double(11,2),in valorAdministracion double(11,2))
		begin 
        insert into Locales(saldoFavor,saldoContra,mesesPendientes,disponibilidad,valorLocal,valorAdministracion)
			values (saldoFavor,saldoContra,mesesPendientes,disponibilidad,valorLocal,valorAdministracion);
	end $$
Delimiter ;

Delimiter $$
	Create Procedure sp_ListarLocales ()
		begin
			select
				Locales.codigoLocal,
                Locales.saldoFavor,
                Locales.saldoContra,
                Locales.mesesPendientes,
                Locales.disponibilidad,
                Locales.valorLocal,
                Locales.valorAdministracion
			from Locales;
	end $$
Delimiter ;

Delimiter $$ 
	Create Procedure sp_BuscarLocales (in numLocales int)
		begin 
			select
            Locales.codigoLocal,
                Locales.saldoFavor,
                Locales.saldoContra,
                Locales.mesesPendientes,
                Locales.disponibilidad,
                Locales.valorLocal,
                Locales.valorAdministracion
		from Locales where codigoLocal = numLocales;
	end$$
Delimiter ;

Delimiter $$
	Create Procedure sp_BorrarLocales (in numLocales int)
		begin 
			Delete from Locales where codigoLocal = numLocales;
	end$$
Delimiter ;

Delimiter $$
	Create Procedure sp_EditarLocales (in numLocales int, in newSaldoFavor double(11,2), in newSaldoContra double(11,2),in newMesesPendientes int,in newDisponibilidad boolean, in newValorLocal double(11,2),in newValorAdministracion double(11,2))
			begin 
				update Locales
			set
				saldoFavor = newSaldoFavor,
                saldoContra = newSaldoContra,
                mesesPendientes = newMesesPendientes,
                disponibilidad = newDisponibilidad,
                valorLocal = newValorLocal,
                valorAdministracion = newValorAdministracion 
			where codigoLocal = numLocales;
	end $$
Delimiter ;




-- Procedimiento Almacenados
-- Clientes


Delimiter $$
	Create Procedure sp_AgregarCliente (in nombresClientes varchar(45),in apellidosClientes varchar(45), in telefonoCliente varchar(8), in direccionCliente varchar(60), in emailCliente varchar(45), in codigoLocal int, in codigoAdministracion int, in codigoTipoCliente int)
		begin
		insert into Clientes(nombresClientes,apellidosClientes,telefonoCliente,direccionCliente,emailCliente,codigoLocal,codigoAdministracion,codigoTipoCliente)
			values (nombresClientes,apellidosClientes,telefonoCliente,direccionCliente,emailCliente,codigoLocal,codigoAdministracion,codigoTipoCliente);
	end $$
delimiter ;

Delimiter $$
	Create Procedure sp_ListarCliente ()
		begin 
			select 
				Clientes.codigoCliente,
                Clientes.nombresClientes,
                Clientes.apellidosClientes,
                Clientes.telefonoCliente,
                Clientes.direccionCliente,
                Clientes.emailCliente,
                Clientes.codigoLocal,
                Clientes.codigoAdministracion,
                Clientes.codigoTipoCliente
		from Clientes;
	end $$
Delimiter ;

Delimiter $$
	Create Procedure sp_buscarClientes (in numClientes int)
		begin 
			select 
				Clientes.codigoCliente,
                Clientes.nombresClientes,
                Clientes.apellidosClientes,
                Clientes.telefonoCliente,
                Clientes.direccionCliente,
                Clientes.emailCliente,
                Clientes.codigoLocal,
                Clientes.codigoAdministracion,
                Clientes.codigoTipoCliente
		 from Clientes where codigoCliente = numClientes;
	end$$
Delimiter ;

Delimiter $$
	Create Procedure sp_BorrarClientes (in numClientes int)
		begin
			Delete from Clientes where codigoCliente = numClientes;
	end$$
Delimiter ;

Delimiter $$
	Create Procedure sp_EditarClientes(in numClientes int,in newNombresCliente varchar(45),in newApellidosCliente varchar(45), in newTelefonoCliente varchar(8), in newDireccionCliente varchar(60), in newEmailCliente varchar(45))
		begin 
			update Clientes
            set 
				
                nombresClientes = newNombresCliente,
                apellidosClientes = newApellidosCliente,
                telefonoCliente = newTelefonoCliente,
                direccionCliente = newDireccionCliente,
                emailCliente = newEmailCliente
               
                
		where codigoCliente = numClientes;
	end$$
Delimiter ;    


    
-- Procedimiento Almacenados
-- CuentasPorCobrar



Delimiter $$
	Create Procedure sp_AgregarCuentasPorCobrar (in numeroFactura varchar(45),in anio year, in mes int, in valorNetoPago double(11,2), in estadoPago varchar(45), in codigoAdministracion int, in codigoCliente int, in codigoLocal int)
		begin
        insert into CuentasPorCobrar(numeroFactura,anio,mes,valorNetoPago,estadoPago,codigoAdministracion,codigoCliente,codigoLocal)
			values (numeroFactura,anio,mes,valorNetoPago,estadoPago,codigoAdministracion,codigoCliente,codigoLocal);
	end $$
delimiter ;    

Delimiter $$	
	Create procedure sp_ListarCuentasPorCobrar ()
		begin 
        select
			CuentasPorCobrar.codigoCuentasPorCobrar,
            CuentasPorCobrar.numeroFactura,
            CuentasPorCobrar.anio,
            CuentasPorCobrar.mes,
            CuentasPorCobrar.valorNetoPago,
            CuentasPorCobrar.estadoPago,
            CuentasPorCobrar.codigoAdministracion,
            CuentasPorCobrar.codigoCliente,
            CuentasPorCobrar.codigoLocal
		from CuentasPorCobrar;
	end $$
Delimiter ;
Delimiter $$
	Create Procedure sp_BuscarCuentasPorCobrar (in numCuentasPorCobrar int)
		begin 
        select
			CuentasPorCobrar.codigoCuentasPorCobrar,
            CuentasPorCobrar.numeroFactura,
            CuentasPorCobrar.anio,
            CuentasPorCobrar.mes,
            CuentasPorCobrar.valorNetoPago,
            CuentasPorCobrar.estadoPago,
            CuentasPorCobrar.codigoAdministracion,
            CuentasPorCobrar.codigoCliente,
            CuentasPorCobrar.codigoLocal
		from CuentasPorCobrar where codigoCuentasPorCobrar = numCuentasPorCobrar;
	end$$
Delimiter ;

Delimiter $$
	Create procedure sp_BorrarCuentasPorCobrar (in numCuentasPorCobrar int)
		begin
		Delete from CuentasPorCobrar where codigoCuentasPorCobrar = numCuentasPorCobrar;
	end$$
Delimiter ;
Delimiter $$
	Create Procedure sp_EditarCuentasPorCobrar (in numCuentasPorCobrar int, in newNumeroFactura varchar(45),in newAnio year, in newMes int, in newValorNetoPago double(11,2), in newEstadoPago varchar(45))
		begin
		update CuentasPorCobrar
		set 
			
            numeroFactura = newNumeroFactura,
            anio = newAnio,
            mes = newMes,
            valorNetoPago = newValorNetoPago,
            estadoPago = newEstadoPago
            
        where codigoCuentasPorCobrar = numCuentasPorCobrar;
	end$$
Delimiter ;



call sp_AgregarAdministracion('Mixco,Guatemala', 45679823);
call sp_AgregarAdministracion('Zona 10 de Guatemala', 6579823);
call sp_ListarAdministracion();
          
call sp_AgregarTipoCliente ('Hola');
call sp_AgregarLocales (34,34,2,true,3,4);
call sp_AgregarLocales (34,34,2,true,3,4);
call sp_AgregarTipoCliente ('Buenas Noches');
call sp_AgregarTipoCliente ('Aleluya');

call sp_AgregarDepartamento('Adminstracion');

call sp_AgregarProveedor('657535-k','Local','56789367','avenida las americas Zona 10 local 3',1250.00,00,1);
call sp_AgregarProveedor('657535-f','alimento','56789367','zona 5 avenida flor casa54-0',50.00,00,2);
call sp_AgregarProveedor('4568289','alimento','756562','casa maribella condominio flores zona 10',500.00,00,1);
call sp_AgregarProveedor('4731723','farmacia','64528913','avenida las americas zona 10 local 35',00.00,1420.00,2);
call sp_AgregarProveedor('0925382','plasticos','21386151','calle 3 zona 4 local 35',00.00,500.00,1);
call sp_AgregarProveedor('7163246','electricista','22345712','zona 1 18 calle local 5',00.00,4000.00,2);
call sp_AgregarProveedor('3178643','agua','22002864','colonia los villansicos casa 450 zona 18',00.00,00.00,1);
call sp_AgregarProveedor('3189542','seguridad','22568132','4ta avenida 08-55 local 34',1340.00,00.00,2);
call sp_AgregarProveedor('9837973','agendas','24567345','local 10 cenma ',750.00,00.00,1);
call sp_AgregarProveedor('8756322','AireAcondicionado','87450912','local 34 mercado central zona 1',00.00,00.00,2);
call sp_listarProveedor(); -- Diego Solis

Delimiter $$
	Create Procedure sp_SaldoLiquidoProveedor(in codProveedor int)
    begin 
		select (saldoFavor-saldoContra) as  SaldoLiquido
        from Proveedores
			where codigoProveedor = codProveedor;
            
	end $$
Delimiter ;

call sp_SaldoLiquidoProveedor(1); -- Diego Solis 2018306

-- drop procedure sp_ProveedoresTodo;
-- Delimiter $$
-- Create procedure sp_ProveedoresTodo () 
			-- select NITProveedor,servicioPrestado,telefonoProveedor,direccionProveedor,saldoFavor,saldoContra,codigoAdministracion, (saldoFavor-saldoContra) as  SaldoLiquido
				-- from Proveedores;
	-- end $$
-- Delimiter ;


 -- call sp_ProveedoresTodo();
        
call sp_AgregarLocales (1500.00,0.00,0,true,500,1200);
call sp_AgregarLocales (1500.00,0.00,4,false,500,1200);
call sp_AgregarLocales (1500.00,0.00,5,true,500,1200);
call sp_AgregarLocales (3000.00,0.00,0,false,500,1200);
call sp_AgregarLocales (1500.00,0.00,1,true,500,1200);
call sp_AgregarLocales (4000.00,0.00,0,false,500,1200);
call sp_AgregarLocales (3000.00,0.00,0,true,500,1200);
call sp_AgregarLocales (1500.00,0.00,0,false,500,1200);
call sp_AgregarLocales (0.00,1250.00,2,true,500,1200);
call sp_AgregarLocales (0.00,1400,5,false,500,1200);   

call sp_listarLocales();     -- Diego Solis 2018306

Delimiter $$
	Create Procedure sp_rentaPendiente()
		begin
			select saldoFavor,saldoContra,mesesPendientes,disponibilidad,valorLocal,valorAdministracion,(valorLocal*mesesPendientes)-(saldoFavor-saldoContra) as RentaPendiente
				from Locales;
	end $$
Delimiter ;

call sp_rentaPendiente; -- Diego Solis 2018306

call sp_AgregarCliente('Diego','Solis','45672312','Guatemala','yahoo@Gmail.com',1,1,1);
call sp_AgregarCliente('Pedro','Herrera','56753456','Guatemala','yahoo@Gmail.com',1,1,1);


call sp_ListarCliente();

Delimiter $$
	Create Procedure sp_MesesPendientes (in codLocal int)
		begin 
			select codigoLocal,saldoFavor,saldoContra,mesesPendientes,disponibilidad,valorLocal,((mesesPendientes*valorLocal)-(saldoFavor-saldoContra))as Pendiente
				from Locales where codigoLocal = codLocal;
		end$$
Delimiter ;

call sp_MesesPendientes(1); -- Diego Solis 2018306


Delimiter $$
	Create Procedure sp_ContarLocales()
		begin
			select count(codigoLocal) as LocalesTotal
				from Locales;
		end$$
Delimiter ;

call sp_ContarLocales; -- DiegoSolis 2018306

Delimiter $$ 
	Create procedure sp_ContarDisponibles()
		begin
			select count(disponibilidad) as Disponibles
				from Locales	where disponibilidad = true;
		end$$
Delimiter ;

call sp_ContarDisponibles; -- DIego Solis 2018306

Delimiter $$
	Create Procedure sp_ContarNoDisponibles()
		begin 
			select count(disponibilidad) as NODisponibles
				from Locales where disponibilidad = false;
	end $$

Delimiter ;
call sp_AgregarHorario('12:00','12:00',true,false,true,true,false);

call sp_ContarNoDisponibles; -- Diego Solis
call sp_AgregarCuentasPorPagar('5677432','1990-09-09','Pendiente',22.50,1,1);


ALTER USER 'root'@'localhost' identified with mysql_native_password by 'admin';

select * from TipoCliente inner join Clientes on
    TipoCliente.codigoTipoCliente = Clientes.codigoTipoCliente
    where TipoCliente.descripcion ='hola';
    
Create Table Usuario(
	codigoUsuario int not null auto_increment,
    nombreUsuario varchar(100)not null,
    apellidoUsuario varchar(100) not null,
    usuarioLogin varchar(50),
    contrasena varchar(50)not null,
    primary key PK_codigoUsuario (codigoUsuario)
);
Delimiter $$
	Create procedure sp_AgregarUsuario(in nombreUsuario varchar(100),in apellidoUsuario varchar (100),in usuarioLogin varchar(50), in contrasena varchar(50))
		begin 
			insert into Usuario(nombreUsuario, apellidoUsuario, usuarioLogin, contrasena)
				values(nombreUsuario, apellidoUsuario, usuarioLogin, contrasena);
        end $$
Delimiter ;
Delimiter $$
	Create Procedure sp_ListarUsuarios()
		begin 
			select 
            U.codigoUsuario,
            U.nombreUsuario,
            U.apellidoUsuario,
            U.usuarioLogin,
            U.contrasena
            from Usuario U;
        end$$
Delimiter ;
call sp_ListarUsuarios();    

call sp_AgregarUsuario('Diego','Solis','dsolis','1792');

select * from Usuario;
	create table login(
		usuarioMaster varchar(50) not null,
		passwordLogin varchar(50) not null,
		primary key PK_usuarioMaster (usuarioMaster)
);

call sp_AgregarCargo('Administrador') ;
call sp_AgregarCuentasPorCobrar('gty56',2003,09,500.00,'Pendiente','1','1','1');
call sp_AgregarEmpleado('Daniel','Juarez','djuarez@gmail.com','78906543','1990-09-09',4500.00,'1','1','1','1');