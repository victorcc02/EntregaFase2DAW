# AlphaGym

## 📋 Integrantes del equipo de desarrollo

| Nombre        | Apellidos           | Correo Uni  | Cuenta Github|
| ------------- |:-------------:| :---------:|:---------:|
|  Catalin   | Mazarache | c.mazarache.2021@alumnos.urjc.es | CataUrjc|
|  Adrián   | Esteban Martin      |   a.estebanm.2021@alumnos.urjc.es | aadri-2003 |
| Jonathan Xavier | Medina Salas    |   jx.medina@alumnos.urjc.es | XdeXavi |
| Adrián | Dueñas Mínguez  |   a.duenas.2021@alumnos.urjc.es | AdriDM-urjc |
| Víctor | Candel Casado     |   v.candel.2020@alumnos.urjc.es  | victorcc02 |

## 🔗 Herramientas de coordinación
Usamos **Trello** para la organización del equipo. Puedes acceder al tablero público aquí: [GymBros Trello](https://trello.com/w/daw051)

## 🏋️‍♂️ Entidades
Las principales entidades de la aplicación son:
- **Usuario**: Clientes del gimnasio que pueden acceder a entrenamientos y planes de nutrición.
- **Nutrición**: Planes alimenticios personalizados según los objetivos del usuario.
- **Entrenamiento**: Rutinas de ejercicios personalizadas para cada usuario.
- **Comentario Entrenamiento**: Los usuarios pueden publicar comentarios sobre los entrenamientos.
- **Comentario Nutrición**: Los usuarios pueden publicar comentarios sobre la nutrición.
  
![image](https://github.com/user-attachments/assets/4bc5973a-0ea9-4801-81b8-f486ba7dab55)




## 🔑 Permisos de los usuarios
- **Anónimo**: Puede ver los diferentes entreamientos y rutinas disponibles, no puede acceder a los comentarios personalizados de entrenamiento ni a los comentarios de nutrición, puede registrarse
- **Registrado**: Puede ver los diferentes comentarios publicados(Comentario), puede publicar un comentario(Comentario),  puede acceder a sus planes de entrenamiento(Entrenamiento), puede solicitar planes personalizados o automáticos de entrenamiento(Entrenamiento), puede acceder a sus planes de nutrición(Nutrición), puede solicitar planes personalizados o automáticos de nutrición(Nutrición).
- **Admin**: Tiene todos los permisos de un usuario registrado y permisos para crear planes de entrenamiento y de nutrición.

## 🖼️ Imágenes
Las siguientes entidades tendrán imágenes asociadas:
- **Usuario**: Los usuarios podrán tener foto de perfil.
- **Entrenamiento**: Los entrenamientos tendrán fotos asignadas.
- **Nutrición**: La nutrición tendrá imágenes asociadas.

## 📊 Gráficos
Se mostrarán los siguientes gráficos:
- **Nutrición**: El plan de nutrición tendrá un gráfico de sectores donde mostrará información de la dieta.

## 🛠️ Tecnología complementaria
- **Entrenamiento**: Generará PDFs con planes de entrenamiento.
- **Nutrición**: Generará PDFs con planes de nutrición.


## 🤖 Algoritmo o consulta avanzada
- **Entrenamiento**: Hacer un algoritmo que genere un plan de entrenamiento en base a los objetivos y datos de información del usuario.
- **Nutrición**: Hacer un algoritmo que genere un plan de nutrición en base a los objetivos y datos de información del usuario.

# Fase 1 
## 💻 Pantallas

## Pantalla de Inicio:
Nuestra pantalla de inicio de AlphaGym presenta una interfaz acogedora con opciones de navegación. Destacamos las diferentes opciones que presenta nuestro gimnsaio, como rutinas y dietas. Además de la ubicación y más datos informativos sobre nuestro gimnasio.

![127 0 0 1_5500_index html](https://github.com/user-attachments/assets/432209d9-30a9-4233-8721-9afb64a6fedb)

## Pantalla de Inicio de Sesión:
En esta pantalla los usuarios deben poner el correo electrónico y la contraseña correspondiente a sus cuentas, una vez rellenados el usuario clickará en el botón "Login", el usuario tiene la opción de cambiar la contraseña (pulsando en el enlace "Forgot Password?" en caso de que no la recuerde. Los usuarios que no tengan cuenta podrán crearse una a través del link "Sign up".

![image](https://github.com/user-attachments/assets/cf555160-6061-4a81-8612-3a77a37c7018)

## Pantalla de Registro:
Nuestra pantalla de registro se basa en una interfaz muy intuitiva ya que el usuario debe rellenar el formulario que se puede ver a la derecha de la pantalla. El usuario tiene que rellenar el formulario con su nombre completo, un correo electrónico y una contraseña que se le pide que repita para verificar que es la contraseña puesta es correcta. Finalmente hace click en el botón de "Submit" para dar de alta su cuenta.

![127 0 0 1_5500_register html](https://github.com/user-attachments/assets/4efaed7f-74d4-4213-8f7f-ab3bdd4fe278)

## Pantalla de Rutinas:
El usuario puede ver todas las rutinas que ofrece nuestro gimnasio y añadir las suyas propias, esta última opción solo en el caso de que estén registrados. Todos los usuarios podrán acceder a los detalles de la rutina que deseen haciendo click en el botón de dicha rutina.

![image](https://github.com/user-attachments/assets/80b080aa-c31e-4fdc-9677-72d79fc7b6df)

## Pantalla de Detalles de Rutinas:
Dependiendo del tipo de usuario que sea y los permisos que tenga el usuario podrá realizar diferentes acciones en esta pantalla como editar, borrar, comentar y ver otros comentarios o suscribirse a una rutina. Por último, existe un botón para que el usuario pueda regresar a la pantalla anterior.

![image](https://github.com/user-attachments/assets/77d11fd5-2193-44b5-9ac1-6bced72c89c9)

## Pantalla para Añadir Rutinas: 
Esta pantalla será lo que verá el usuario cuando decida añadir una rutina, para ello deberá rellenar un formulario con diferentes campos: el nombre, la intensidad, el objetivo y los ejercicios de la rutina. También tiene la opción de añadir algún comentario a la hora de crearla. Una vez rellenados todos los datos el usuario subirá su rutina a través del botón "Save Routine".

![image](https://github.com/user-attachments/assets/c85c2921-3e5f-4861-8637-3b3f0cd14086)

## Pantalla de Dietas:
El usuario puede ver todas las dietas que ofrece nuestro gimnasio y añadir sus propias dietas, esta última opción solo en el caso de que estén registrados. Todos los usuarios podrán acceder a los detalles de la dieta que deseen haciendo click en el botón de dicha dieta.

![image](https://github.com/user-attachments/assets/57745212-ff0f-42fe-bd9d-698726841611)

## Pantalla de Detalles de Dietas:
Dependiendo del tipo de usuario que sea y los permisos que tenga el usuario podrá realizar diferentes acciones en esta pantalla como editar, borrar, comentar y ver otros comentarios o suscribirse a una dieta. Por último, hay un botón para que el usuario pueda regresar a la pantalla anterior.

![image](https://github.com/user-attachments/assets/87aedbb4-b887-4de8-80fa-9643fd5de293)

## Pantalla para Añadir Dietas:
Esta pantalla será lo que verá el usuario cuando decida añadir una nueva dieta, para ello deberá rellenar un formulario con diferentes campos: el nombre, el número de calorías, el objetivo y las comidas de la rutina. También tiene la opción de añadir algún comentario a la hora de crearla. Una vez rellenados todos los datos el usuario subirá su dieta a través del botón "Save Diet".

![image](https://github.com/user-attachments/assets/4ac22f67-1211-48cb-a159-88fc4ae28c7a)

## Pantalla para Ver Comentarios: 
Esta pantalla podrán usarla todos los usuarios. En ella los usuarios verán los diferentes comentarios realizados sobre una dieta o una rutina. Cada comentario tiene un botón para notificar al administrador en el caso de que el usuario considere inapropiado el comentario. El dueño podra modificar sus comentarios y los admin podran eliminar y editar todos los comentarios.
Habrá una interfaz ligeramente diferente para los comentarios sobre dietas y sobre entrenamientos.

![image](https://github.com/user-attachments/assets/1c4b3d5d-d57b-4a57-afbb-38ede200053e)
![image](https://github.com/user-attachments/assets/6a09f9ca-a0eb-44d0-896a-100cbf1d2952)

## Pantalla para Editar Comentarios: 
Se necesitarán ciertos privilegios para acceder a esta página. Los usuarios podrán editar sus propios comentarios y los admin podrán editar todos.

![image](https://github.com/user-attachments/assets/955c9ca5-6be2-475e-9157-c0a6ae8f0cdd)

## Pantalla para Añadir Comentarios: 
Al igual que la pantalla anterior se necesitan ciertos privilegios para acceder a esta página. Los usuarios podrán realizar comentarios acerca de una rutina o dieta rellenando el formulario que consta de un nombre para el comentario y el propio comentario.

![image](https://github.com/user-attachments/assets/d6bfca46-7d2a-4d65-9802-18f92592fc10)

## Pantalla de Administrador: 
A esta pantalla solo tendrán acceso los administradores. Aquí pueden ver los datos de su perfil, las notificaciones recibidas sobre algún comentario y todas las rutinas y dietas que existen con opción a editarlas o borrarlas en caso de que así lo crean.

![image](https://github.com/user-attachments/assets/9571227d-bf9d-46e2-8d47-fd76d92cee0d)

## Pantalla de Usuario: 
El usuario verá los datos de su perfil y las rutinas y las dietas a las que está suscrito. Desde esta página también puede borrar alguna dieta o rutina a las que esté suscrito.

![127 0 0 1_5500_account html (1)](https://github.com/user-attachments/assets/168866e3-02c8-4f48-90a5-9d61db63ab51)

## Pantalla de error: 
En el caso de que el usuario intente acceder a una pantalla para la que no tenga permisos o surga algún problema con la página se mostrará esta pantalla.

![image](https://github.com/user-attachments/assets/fcd2c7d5-5d1f-4ab1-b42d-9ebfe7d4caaf)


## Diagrama de navegación: 
- **Azul**: Todos los usuarios.
- **Verde**: Usuario registrado y admin.
- **Rojo**: Solo admin.
- **Nota**: Desde todas las páginas se puede acceder a la pantalla de error.

![image](https://github.com/user-attachments/assets/17edecec-d86c-4738-a6df-55e3969d6ebd)

## Instrucciones de ejecución: 
### 🛠️ Prerequisites  
| Technology    | Version  | Description  |
|--------------|----------|-------------|
| *Java*      | 21.0.5   | Programming language used for backend development. |
| *Spring Boot* | 3.4.3  | Framework for building Java-based enterprise applications. |
| *MySQL*     | 8.0.33   | Relational database management system for data storage. |
| *Maven*     | 3.8.3+   | Build automation tool used for managing project dependencies. |

## 🏋️‍♂️ AlphaGym - Installation and Setup  

### 📥 Clone the Repository  
To get the source code, clone the repository using the following command:  

sh
git clone https://github.com/CodeURJC-DAW-2024-25/webapp05.git AlphaGym --branch main --depth 1
cd ./AlphaGym/backend


🗄️ Install MySQL
Download and install MySQL from the official site: [MySQL Downloads](https://dev.mysql.com/downloads/).
Create a new database named gymdb:

sql
CREATE DATABASE gymdb;


⚙️ Configure Database Connection
Edit the src/main/resources/application.properties file and make sure to set the following parameters according to your MySQL configuration:

properties
spring.datasource.url=jdbc:mysql://localhost/gymdb
spring.datasource.username=root
spring.datasource.password=pass
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE


🏗️ Build the Project with Maven
Run the following command based on your operating system:

🔹 Linux / macOS:
### 🔹 Linux / macOS:
sh
./mvnw clean install


### 🔹 Windows:
sh
.\mvnw.cmd clean install


🚀 Run the Application
To start the server, execute:

### 🔹 Linux / macOS:
sh
./mvnw spring-boot:run


### 🔹 Windows:
sh
.\mvnw.cmd spring-boot:run


🌐 Access the Application
Open your browser and go to:
➡️ https://localhost:8443

---

## Diagrama de las entidades de la base de datos: 
Este es el diagrama generado por MySQL Workbench con las entidades que tenemos configuradas en la base de datos y la relacion entre ellas:

![image](https://github.com/user-attachments/assets/7681880b-314a-408d-a5eb-6a743a78afd0)

## Diagrama de clases y templates: 
Este diagrama proporciona información general sobre la estructura de la aplicación y de cómo interactuan entre ellas:

![image](https://github.com/user-attachments/assets/6664eea7-6ffe-4a6d-87b7-a2bddd2af63e)

Rosa: Templates
Verde: @Controller
Rojo: @Service
Azul: @Repository
Amarillo: Entidades


## Participacion de los miembros

### Adrián Dueñas Minguez

#### Descripcion general:
Principalmente he participado en los comentarios, en su gestión, creación, edición... También he contribuido a otras tareas menores.

#### Mis 5 commits mas relevantes

| Commit | Descripción                          | Link                                                                                                                                                                                                 |
| ------ | ------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| #1     | Create comments                      | [https://github.com/CodeURJC-DAW-2023-24/webapp06/commit/1079ce988111789ac53597399cd1cc77affc1b58](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/6c4ff4eb3b8c1fab6a0a94c2f46d5bddf79527ad) |
| #2     | Reported comments management by admin| [https://github.com/CodeURJC-DAW-2023-24/webapp06/commit/30dc1d5295eaf569c70949df9d4022b0c87b9b4a](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/ece055ffa4fa6694f6ea7f4777ad4ec4a8d3b648) |
| #3     | Ajax development                     | [https://github.com/CodeURJC-DAW-2023-24/webapp06/commit/0acc1f72e4650b2b1999ea068373f52b02d2425b](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/7b0adc21272d4aad93f67e09bdeda9f4770757c5) |
| #4     | Report and delete comments           | [https://github.com/CodeURJC-DAW-2023-24/webapp06/commit/329d4733cb21f99717ba764d50bceb7457a65f13](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/c127cd78daff735e6894d2504fce7f5b41e704de) |
| #5     | Edit comments                        | [https://github.com/CodeURJC-DAW-2023-24/webapp06/commit/728a361bad3b80b9e13621c33d415272a9fec7ac](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/48b416a8149d41227c63d2b5ec3c116146f1125e) |

#### Los 5 ficheros que más he modificado

| Número fichero | Fichero                                                                                                                                                                                    |
| -------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| #1             | [NutritionCommentController.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/NutritionCommentController.java)    |
| #2             | [TrainingCommentController.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/TrainingCommentController.java)      |
| #3             | [NutritionCommentService.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/service/NutritionCommentService.java)             |
| #4             | [TrainingCommentService.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/service/TrainingCommentService.java)               |
| #5             | [file.js](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/resources/static/js/file.js)                                                                         |


### Catalin Mazarache

#### Descripcion general:
Principalmente he participado en los comentarios de training y nutrition junto con la función de cargar más comentarios de training y nutrition de js.

#### Mis 5 commits mas relevantes

| Commit | Descripción                          | Link                                                                                                                                                                                                 |
| ------ | ------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| #1     | AJAX Show more comments for training & nutrition  | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/2c33df6661f1fedd0130cdcda2ebc07efcbc37a5](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/2c33df6661f1fedd0130cdcda2ebc07efcbc37a5)|
| #2     | Create Nutrition Comment  | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/c8b54dae898a752b09bd30624e9b7a81db4d7d99](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/c8b54dae898a752b09bd30624e9b7a81db4d7d99) |
| #3     | Initialize Nutrition Comments  | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/d79d2b07955dbddff5c55a5c7f17a13694b9a497](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/d79d2b07955dbddff5c55a5c7f17a13694b9a497)|
| #4     | Manage Training Comments | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/cddae00ea4148a9ce6a1eb961ec03ca218d085a9](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/cddae00ea4148a9ce6a1eb961ec03ca218d085a9)|
| #5     | Manage First Comments | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/5fec306b00b5f0fc940439c61fe1c924cbac9548](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/5fec306b00b5f0fc940439c61fe1c924cbac9548) |

#### Los 5 ficheros que más he modificado

| Número fichero | Fichero                                                                                                                                                                                    |
| -------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| #1             | [NutritionCommentController.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/NutritionCommentController.java)    |
| #2             | [TrainingCommentController.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/TrainingCommentController.java)      |
| #3             | [NutritionCommentService.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/service/NutritionCommentService.java)             |
| #4             | [TrainingCommentService.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/service/TrainingCommentService.java)               |
| #5             | [file.js](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/resources/static/js/file.js)                                                                         |


### Adrián Esteban Martín

#### Descripcion general:
Mi tarea principal ha sido la entidad "Nutrition", su creación, su edición, de que manera se deben mostrar... Tambien he ayudado en otras tareas, por ejemplo, la tecnología complementaria.

#### Mis 5 commits más relevantes

| Commit | Descripción                          | Link                                                                                                                                                                                                 |
| ------ | ------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| #1     | Start controller and service of nutrition  | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/559d4227e0e38ade0a2fc4ddb6df1c77f6dcd4ca](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/559d4227e0e38ade0a2fc4ddb6df1c77f6dcd4ca)|
| #2     | Fixed Nutrition and add function in to the controller and service | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/6fb4271b6d1a26866a68f45645a05b1a8d22a6c3#diff-687baae317c329d62fdb56208c5b8f1658f894cf5ee7f909da435bad4acd3696](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/6fb4271b6d1a26866a68f45645a05b1a8d22a6c3#diff-687baae317c329d62fdb56208c5b8f1658f894cf5ee7f909da435bad4acd3696) |
| #3     | Nutritions functions  | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/27712db6edfb4523b0877009e16c4447443ac9d5](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/27712db6edfb4523b0877009e16c4447443ac9d5)|
| #4     | Add functionality to nutrition | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/999fc83cd66741f2a48ac8c0b4caed297c0844f9](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/999fc83cd66741f2a48ac8c0b4caed297c0844f9)|
| #5     | Add function to generate PDFs | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/b475466286327e7bc224d2f938b32c57741b0431](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/b475466286327e7bc224d2f938b32c57741b0431) |

#### Los 5 ficheros que más he modificado

| Número fichero | Fichero                                                                                                                                                                                    |
| -------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| #1             | [NutritionController.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/NutritionController.java)    |
| #2             | [NutritionService.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/service/NutritionService.java)      |
| #3             | [Nutrition.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/model/Nutrition.java)             |
| #4             | [NutritionRepository.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/repository/NutritionRepository.java)               |
| #5             | [file.js](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/resources/static/js/file.js)   

### Jonathan Xavier Medina Salas 

#### Descripcion general:
Mis tareas han sido crear, gestionar la base de datos MySQL, toda la parte de Security, el model User, el UserContoller, el UserRepository, el UserService, la subida de imágenes y la búsqueda avanzada.

#### Mis 5 commits mas relevantes

| Commit | Descripción                          | Link                                                                                                                                                                                                 |
| ------ | ------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| #1     | Create Search                        | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/16f2bcd9c6332e5b8123d78eee8a4d9073b0dc2e](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/16f2bcd9c6332e5b8123d78eee8a4d9073b0dc2e) |
| #2     | Register                             | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/cb9a694851e37bd38dd32fb9962a0853d7d9236b](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/cb9a694851e37bd38dd32fb9962a0853d7d9236b) |
| #3     | Security                             | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/482796ce86833c2175ba172c9e07269d38157c91](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/482796ce86833c2175ba172c9e07269d38157c91) |
| #4     | GymDB MySQL                          | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/e580c3c881d300be38425bfa8b16a7daa0eb2a0c](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/e580c3c881d300be38425bfa8b16a7daa0eb2a0c) |
| #5     | Update Image                         | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/ddcc58e02f5a5d6aa52d89117623b746eaa44607](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/ddcc58e02f5a5d6aa52d89117623b746eaa44607) |

#### Los 5 ficheros que más he modificado

| Número fichero |  Fichero                                                                                                                                                                    |
| -------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------  |
| #1             | [WebSecurityConfig.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/security/WebSecurityConfig.java)        |
| #2             | [UserController.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/UserController.java)            |
| #3             | [SearchController.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/SearchController.java)        |
| #4             | [User.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/model/User.java)                                     |
| #5             | [UserService.js](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/service/UserService.java)                       |

### Víctor Candel Casado

#### Descripcion general:
Mis tareas han sido gestionar la entidad "Training" y las entidades de las que esta dependia, asi como el manejo de imagenes tanto de "Training" como de "Nutrition". Por último, gestionar las posibilidades de la aplicación dependiendo del tipo de usuario tanto en la entidad ya mencionada, como en los comentarios de ambas.

#### Mis 5 commits mas relevantes

| Commit | Descripción                          | Link                                                                                                                                                                                                 |
| ------ | ------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| #1     | Image Nutrition and Training         | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/ef7568ae746eb4e127634e31cc629804c45740f8](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/ef7568ae746eb4e127634e31cc629804c45740f8) |
| #2     | Difference between type of user      | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/03f2c69a538238e9c01acd3587d9ea5ae6b0cdf8](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/03f2c69a538238e9c01acd3587d9ea5ae6b0cdf8) |
| #3     | Suscribe funcionality                | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/5a9b3543427c77ae6ca1707ca1757a0fad62187c](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/5a9b3543427c77ae6ca1707ca1757a0fad62187c) |
| #4     | account training suscribed funcionality| [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/6c6e813ac5c3344fa396f8b01c32c55088029ae7](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/6c6e813ac5c3344fa396f8b01c32c55088029ae7) |
| #5     | CRED training                        | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/808be3f2b4a2ac254f15a8b2c97c406327e9c4b1](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/808be3f2b4a2ac254f15a8b2c97c406327e9c4b1) |

#### Los 5 ficheros que más he modificado

| Número fichero | Fichero                                                                                                                                                                                    |
| -------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| #1             | [editRoutine.html](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/resources/templates/editRoutine.html)   |
| #2             | [trainingController.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/trainingController.java)                            |
| #3             | [trainingService.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/trainingService.java)                        |
| #4             | [Training.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/model/Training.java)                                                     |
| #5             | [file.js](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/resources/static/js/file.js)                                       |


# Fase 2

## Documentación de la API REST

Se puede acceder a la documentación de la API de 2 formas, a traves de consultar directamente el .yaml o través del .html.

[Fichero .yaml](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/api-docs/api-docs.yaml)

[Fichero .html](https://raw.githack.com/CodeURJC-DAW-2024-25/webapp05/main/api-docs/api-docs.html)


## Creacion de la imagen y contenedores docker
Se necesita tener instalado docker. Para empezar debemos acceder al proyecto en el cmd 

Posteriormente acceder a la carpeta docker y ejecutar ->  ./create_Image.ps1 

Previamente se ha debido realizar "mvn package" para generar la carpeta tarjet, que contendra el archivo .jar

Cabe destacar que se necesitara el nombre del usuario de la cuenta de docker para lanzar la imagen, dicho nombre se introducira en el create_Image.ps1 sustituyendo vcandel:

![image](https://github.com/user-attachments/assets/5291a1b3-a952-4cfe-8c16-2eec34ed020f)

Para crear los contenedores correspondientes en la misma ruta del cmd introduciremos -> docker compose up 


## Diagrama de clases y templates: 
Este diagrama proporciona información general sobre la estructura de la aplicación y de cómo interactuan entre ellas. Tambien incorpora los nuevos REST Controller:

![Diagrama](https://github.com/user-attachments/assets/96c20d5a-7767-44b1-bf2a-ed6fb0435a1c)


Rosa: Templates
Verde: @Controller y @RestController
Rojo: @Service
Azul: @Repository
Amarillo: Entidades


## Participacion de los miembros

### Adrián Dueñas Minguez

#### Descripcion general:
Principalmente he participado en la gestion de las API Rest de los comentarios. También me he asegurado de arreglar los bugs que fueran surgiendo con el desarrollo.

#### Mis 5 commits mas relevantes

| Commit | Descripción                          | Link                                                                                                                                                                                                 |
| ------ | ------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| #1     | Create TrainingCommentRestController | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/2557b04163befa30e756f7c37c9c57323ac70bc1](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/2557b04163befa30e756f7c37c9c57323ac70bc1) |
| #2     | Post & delete training comments      | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/c66162e9e2bbc31baaa5ef6d2c65ee196e79b663](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/c66162e9e2bbc31baaa5ef6d2c65ee196e79b663) |
| #3     | Info for graphic visualization       | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/42eaf125e9f0a72b30921bc1bb384329b1dede12](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/42eaf125e9f0a72b30921bc1bb384329b1dede12) |
| #4     | Admin web functions fixed            | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/a03c983ef29ed7622d75a0787b0d6e041cbe9606](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/a03c983ef29ed7622d75a0787b0d6e041cbe9606) |
| #5     | Report & unreport training comments  | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/9f3b7a7bb752f045b4bdc95623bbc7446952ffef](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/9f3b7a7bb752f045b4bdc95623bbc7446952ffef) |

#### Los 5 ficheros que más he modificado

| Número fichero | Fichero                                                                                                                                                                                                         |
| -------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| #1             | [TrainingCommentRestcontroller.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/rest/TrainingCommentRestcontroller.java)              |
| #2             | [TrainingCommentService.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/service/TrainingCommentService.java)                                    |
| #3             | [NutritionCommentRestController.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/rest/NutritionCommentRestController.java)            |
| #4             | [NutritionCommentService.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/service/NutritionCommentService.java)                                  |
| #5             | [UserRestController.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/rest/UserRestController.java)                                    |

### Catalin Mazarache

#### Descripcion general:
Me he encargado de la parte Rest y Service de Nutrition Comments. He corregido errores que se han dado a lo largo de la fase 2 en otras clases.

#### Mis 5 commits mas relevantes

| Commit | Descripción                          | Link                                                                                                                                                                                                 |
| ------ | ------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| #1     | Initialize Rest Controller Nutrition Comment | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/dce6d4ac0c5ef820ebdda8e0fb6f242b1ef49cf6](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/dce6d4ac0c5ef820ebdda8e0fb6f242b1ef49cf6) |
| #2     | Added methods for handling NutritionComment in both NutritionCommentRestController and NutritionCommentService     | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/4875a2b35abfb4fa8be4147e0518f11b26a2aec8](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/4875a2b35abfb4fa8be4147e0518f11b26a2aec8) |
| #3     | Implemented report and unreport comments in service and REST controller of NutritionComment  | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/36ba22e941a8ce56f36f56826f375fbc68f931b9](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/36ba22e941a8ce56f36f56826f375fbc68f931b9) |
| #4     | Updated login method with email authentication and response codes  | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/a94c482a2ac06985cb336da1a348b176867788a1](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/a94c482a2ac06985cb336da1a348b176867788a1) |
| #5     | Added @operation and @ApiResponses annotations  | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/3daf71f1adc6e96bf737eb6eb7a2dd9089489d4f](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/3daf71f1adc6e96bf737eb6eb7a2dd9089489d4f) |

#### Los 5 ficheros que más he modificado

| Número fichero | Fichero                                                                                                                                                                                                         |
| -------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| #1             | [NutritionCommentRestController.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/rest/NutritionCommentRestController.java)              |
| #2             | [NutritionCommentService.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/service/NutritionCommentService.java)                                    |
| #3             | [UserService.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/service/UserService.java)            |
| #4             | [UserRestController.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/rest/UserRestController.java)                                  |
| #5             | [TrainingRestController.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/rest/TrainingRestController.java)                                    |

### Víctor Candel Casado

#### Descripcion general:
En esta segunda fase me he encargado de solucionar lagun error de la entrega anterior asi como ha manejar la entidad "Training" en la Api Rest, también he protegido rutas de la parte Rest y he creado tanto la imagen como los contenedores docker.

#### Mis 5 commits mas relevantes

| Commit | Descripción                          | Link                                                                                                                                                                                                 |
| ------ | ------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| #1     | Push docker image and docker compose   | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/57ef7e94c8590029b8e89e321b2d328fd12a5e21](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/57ef7e94c8590029b8e89e321b2d328fd12a5e21) |
| #2     | Security for api and verification of canEdit any item and implemente persistence to the db   | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/b63b4ea8e6ee316bc6c2c1f3a69c866867cbbdda](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/b63b4ea8e6ee316bc6c2c1f3a69c866867cbbdda) |
| #3     | Initialize Rest for Training (CRED)  | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/a3481161c39f6ca3904d9f01ce690006f8ff83c4](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/a3481161c39f6ca3904d9f01ce690006f8ff83c4) |
| #4     | Fix error| [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/e2ce91014b511292c7b4f9298afa9fa536f5006b](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/e2ce91014b511292c7b4f9298afa9fa536f5006b) |
| #5     | Add @operation and @ApiResponses in NutritionRestController  | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/03fc324c7ee03ad8d04364c4344cda282744f36e](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/03fc324c7ee03ad8d04364c4344cda282744f36e) |

#### Los 5 ficheros que más he modificado

| Número fichero | Fichero                                                                                                                                                                                                         |
| -------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| #1             | [docker](https://github.com/CodeURJC-DAW-2024-25/webapp05/tree/main/docker)              |
| #2             | [TrainingRestController.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/rest/TrainingRestController.java)                                    |
| #3             | [TrainingService.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/service/TrainingService.java)            |
| #4             | [WebSecurityConfig.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/security/WebSecurityConfig.java)                                  |
| #5             | [TrainingMapper.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/dto/TrainingMapper.java)                                    |

### Jonathan Xavier Medina Salas

Durante esta fase me he encargado de hacer la API Rest de la entidad User, de integrar a la aplicacion la seguridad de API Rest y dar solución a problemas generales.


#### Mis 5 commits mas relevantes

| Commit | Descripción                          | Link                                                                                                                                                                                                 |
| ------ | ------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| #1     | add UserRestController with UserDTO        | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/170badab13c752c858116ee45a38e87776e202ce] (https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/170badab13c752c858116ee45a38e87776e202ce) |
| #2     | add ApiResponses in UserRestController     | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/4de61b35ee19fd81b0f4ee3b39d7d6fee913e8a6] (https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/4de61b35ee19fd81b0f4ee3b39d7d6fee913e8a6) |
| #3     | add RestSecurity                           | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/b6d09a1ed52cf9f2aeadae63447042e5c54fad0d] (https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/b6d09a1ed52cf9f2aeadae63447042e5c54fad0d) |
| #4     | update pom and fix usermapper              | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/a93296335b4fccea2c15e85b9578b4449fb23aae] (https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/a93296335b4fccea2c15e85b9578b4449fb23aae) |
| #5     | fix userRestController                     | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/ccef8b971ae6502289bb34f9442de662b5734e88] (https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/ccef8b971ae6502289bb34f9442de662b5734e88) |

#### Los 5 ficheros que más he modificado

| Número fichero | Fichero                                                                                                                                                                                                         |
| -------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| #1             | [UserRestController.java]()     | (https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/rest/UserRestController.java)
| #2             | [WebSecurityConfig.java]()      | (https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/security/WebSecurityConfig.java)
| #3             | [UserMapper.java]()             | (https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/dto/UserMapper.java)
| #4             | [LoginController.java]()        | (https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/rest/auth/LoginController.java)                                  
| #5             | [UserDTO.java]()                | (https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/dto/UserDTO.java)

### Adrián Esteban Martín

#### Descripcion general: 
Sobretodo he trabajado en la API Rest de la entidad de "Nutrition" aunque también he realizado cambios para otras funciones


#### Mis 5 commits mas relevantes

| Commit | Descripción                          | Link                                                                                                                                                                                                 |
| ------ | ------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| #1     | Start of the REST of nutrition | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/ad4b7a6ce5d85b0a0f3ab596f735ab0e27f8ea61](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/ad4b7a6ce5d85b0a0f3ab596f735ab0e27f8ea61) |
| #2     | Add REST to nutrition image     | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/878ef1c6b01a2113d2c7c650225341c82adc640e](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/878ef1c6b01a2113d2c7c650225341c82adc640e) |
| #3     | Add Mapper and DTO functions | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/d5596c8857705a293bb2e6f5e2058be28befedd2](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/d5596c8857705a293bb2e6f5e2058be28befedd2) |
| #4     | Paginable Nutrition and Training  | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/3e5840b70ae4d444bd7779ed166398dfc8c62cc0](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/3e5840b70ae4d444bd7779ed166398dfc8c62cc0) |
| #5     | Add @operation and @ApiResponses in NutritionRestController  | [https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/03fc324c7ee03ad8d04364c4344cda282744f36e](https://github.com/CodeURJC-DAW-2024-25/webapp05/commit/03fc324c7ee03ad8d04364c4344cda282744f36e) |

#### Los 5 ficheros que más he modificado

| Número fichero | Fichero                                                                                                                                                                                                         |
| -------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| #1             | [NutritionRestController.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/controller/rest/NutritionRestController.java)              |
| #2             | [NutritionService.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/service/NutritionService.java)                                    |
| #3             | [NutritionRepository.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/repository/NutritionRepository.java)            |
| #4             | [NutritionMapper.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/dto/NutritionMapper.java)                                  |
| #5             | [NutritionDTO.java](https://github.com/CodeURJC-DAW-2024-25/webapp05/blob/main/backend/src/main/java/es/codeurjc/daw/alphagym/dto/NutritionDTO.java)                                    |




