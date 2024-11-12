
INSERT INTO "public"."categoria_producto" (nombre) VALUES ('Pantalones');
INSERT INTO "public"."categoria_producto" (nombre)  VALUES ('Camisas');
INSERT INTO "public"."categoria_producto" (nombre) VALUES ('Uniformes Deportivos');

INSERT INTO "public"."rol"  (nombre_rol) VALUES ('ADMINISTRADOR');
INSERT INTO "public"."rol"  (nombre_rol) VALUES ('BODEGA');
INSERT INTO "public"."rol" (nombre_rol) VALUES ('PRODUCCION');


INSERT INTO "public"."unidad" (nombre) VALUES ('Cajas');
INSERT INTO "public"."unidad" (nombre) VALUES ('Fardos');
INSERT INTO "public"."unidad" (nombre) VALUES ('Unidades');
INSERT INTO "public"."unidad" (nombre) VALUES ('Rollos');
INSERT INTO "public"."unidad" (nombre) VALUES ('Cubetas');


INSERT INTO "public"."cliente" (direccion, nombre) VALUES ('Calle Principal 123', 'Juan Pérez');
INSERT INTO "public"."cliente" (direccion, nombre) VALUES ('Avenida Libertad 456', 'María García');
INSERT INTO "public"."cliente" (direccion, nombre) VALUES ('Calle del Sol 789', 'Luis Martínez');
INSERT INTO "public"."cliente" (direccion, nombre) VALUES ('Paseo del Parque 210', 'Ana Rodríguez');
INSERT INTO "public"."cliente" (direccion, nombre) VALUES ('Carrera 7 #23-45', 'Pedro Sánchez');
INSERT INTO "public"."cliente" (direccion, nombre) VALUES ('Avenida Bolívar 567', 'Laura López');
INSERT INTO "public"."cliente" (direccion, nombre) VALUES ('Calle Mayor 890', 'Carlos Ruiz');
INSERT INTO "public"."cliente" (direccion, nombre) VALUES ('Calle de la Montaña 12', 'Sofía Hernández');
INSERT INTO "public"."cliente" (direccion, nombre) VALUES ('Calle Estrella 354', 'Diego González');
INSERT INTO "public"."cliente" (direccion, nombre) VALUES ( 'Avenida Central 678', 'Marta Díaz');


INSERT INTO "public"."producto" (cantidad, codigo, descripcion, estado, nombre_producto, tipo_producto, id_categoria_producto,id_unidad) VALUES ( 143, 'PT1', 'Blusa de seda', 't', 'Blusa de seda', 1, 2, 3);
INSERT INTO "public"."producto" (cantidad, codigo, descripcion, estado, nombre_producto, tipo_producto, id_categoria_producto,id_unidad) VALUES ( 90, 'PT2', 'Falda de algodón', 't', 'Falda de algodón', 1, 2, 3);
INSERT INTO "public"."producto" (cantidad, codigo, descripcion, estado, nombre_producto, tipo_producto, id_categoria_producto,id_unidad) VALUES ( 150, 'PT3', 'Saco de lana', 't', 'Saco de lana', 1, 2, 3);
INSERT INTO "public"."producto" (cantidad, codigo, descripcion, estado, nombre_producto, tipo_producto, id_categoria_producto,id_unidad) VALUES ( 80, 'PT4', 'Chaleco de cuero', 't', 'Chaleco de cuero', 1, 2, 3);
INSERT INTO "public"."producto" (cantidad, codigo, descripcion, estado, nombre_producto, tipo_producto, id_categoria_producto,id_unidad) VALUES ( 200, 'PT5', 'Pantalon de lana', 't', 'Bufanda de lana', 1, 2, 3);
INSERT INTO "public"."producto" (cantidad, codigo, descripcion, estado, nombre_producto, tipo_producto, id_categoria_producto,id_unidad) VALUES ( 25, 'PT6', 'Camisa Azul 1989', 't', 'Camisa Azul 1989', 1, 2, 1);

INSERT INTO "public"."producto" (cantidad, codigo, descripcion, estado, nombre_producto, tipo_producto, id_categoria_producto,id_unidad) VALUES ( 200, 'MP1', 'Cuero genuino', 't', 'Cuero genuino', 2, NULL, 1);
INSERT INTO "public"."producto" (cantidad, codigo, descripcion, estado, nombre_producto, tipo_producto, id_categoria_producto,id_unidad) VALUES ( 200, 'MP2', 'Tela de poliéster', 't', 'Tela de poliéster', 2, NULL, 1);
INSERT INTO "public"."producto" (cantidad, codigo, descripcion, estado, nombre_producto, tipo_producto, id_categoria_producto,id_unidad) VALUES ( 250, 'MP3', 'Algodón orgánico', 't', 'Algodón orgánico', 2, NULL, 2);
INSERT INTO "public"."producto" (cantidad, codigo, descripcion, estado, nombre_producto, tipo_producto, id_categoria_producto,id_unidad) VALUES ( 180, 'MP4', 'Lana merino', 't', 'Lana merino', 2, NULL, 2);
INSERT INTO "public"."producto" (cantidad, codigo, descripcion, estado, nombre_producto, tipo_producto, id_categoria_producto,id_unidad) VALUES ( 120, 'MP5', 'Tela de lana', 't', 'Tela de lana', 2, NULL, 2);
INSERT INTO "public"."producto" (cantidad, codigo, descripcion, estado, nombre_producto, tipo_producto, id_categoria_producto,id_unidad) VALUES ( 400, 'MP6', 'Algodón reciclado', 't', 'Algodón reciclado', 2, NULL, 1);
INSERT INTO "public"."producto" (cantidad, codigo, descripcion, estado, nombre_producto, tipo_producto, id_categoria_producto,id_unidad) VALUES ( 220, 'MP7', 'Tela de seda artificial', 't', 'Tela de seda artificial', 2, NULL, 1);
INSERT INTO "public"."producto" (cantidad, codigo, descripcion, estado, nombre_producto, tipo_producto, id_categoria_producto,id_unidad) VALUES ( 270, 'MP8', 'Tela de nylon', 't', 'Tela de nylon', 2, NULL, 1);
INSERT INTO "public"."producto" (cantidad, codigo, descripcion, estado, nombre_producto, tipo_producto, id_categoria_producto,id_unidad) VALUES ( 320, 'MP9', 'Tela de rayón', 't', 'Tela de rayón', 2, NULL, 1);
INSERT INTO "public"."producto" (cantidad, codigo, descripcion, estado, nombre_producto, tipo_producto, id_categoria_producto,id_unidad) VALUES ( 400, 'MP10', 'Tela de lino orgánico', 't', 'Tela de lino orgánico', 2, NULL, 3);
INSERT INTO "public"."producto" (cantidad, codigo, descripcion, estado, nombre_producto, tipo_producto, id_categoria_producto,id_unidad) VALUES ( 180, 'MP11', 'Hilo de seda natural', 't', 'Hilo de seda natural', 2, NULL, 3);

INSERT INTO "public"."usuario" (email,nombre,nombre_usuario,password,token_password,rol_id) VALUES ('administrador@ues.edu.sv', 'ADMINISTRADOR', 'administrador', '$2a$10$nQ8eHiX.nnkPtj1JNFQODOdXjdT6X/rgd6MPyctFw4r9eQLnlKvbS', NULL, 1);
INSERT INTO "public"."usuario" (email,nombre,nombre_usuario,password,token_password,rol_id) VALUES ('bodega@ues.edu.sv', 'BODEGA', 'bodega', '$2a$10$nQ8eHiX.nnkPtj1JNFQODOdXjdT6X/rgd6MPyctFw4r9eQLnlKvbS', NULL, 2);
INSERT INTO "public"."usuario" (email,nombre,nombre_usuario,password,token_password,rol_id) VALUES ('produccion@ues.edu.sv', 'PRODUCCION', 'produccion', '$2a$10$nQ8eHiX.nnkPtj1JNFQODOdXjdT6X/rgd6MPyctFw4r9eQLnlKvbS', NULL, 3);
