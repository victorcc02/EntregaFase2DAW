package es.codeurjc.daw.alphagym.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;

import es.codeurjc.daw.alphagym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import es.codeurjc.daw.alphagym.model.Nutrition;
import es.codeurjc.daw.alphagym.model.NutritionComment;
import es.codeurjc.daw.alphagym.model.Training;
import es.codeurjc.daw.alphagym.model.TrainingComment;
import es.codeurjc.daw.alphagym.model.User;
import jakarta.annotation.PostConstruct;
import javax.sql.rowset.serial.SerialBlob;

@Service
public class DataBaseInit {

    @Autowired  
    private UserService userService;

    @Autowired
    private TrainingService  trainingService;

    @Autowired
    private NutritionService nutritionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TrainingCommentService trainingCommentService;
    @Autowired
    private NutritionCommentService nutritionCommentService;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() throws IOException, SQLException {

        //Users Examples
        User admin = new User("admin", "admin@admin.com", passwordEncoder.encode("adminpass"), "ADMIN", "USER");
        if (!userRepository.existsByEmail("admin@admin.com")) {
            userService.save(admin);
        }

        User user = new User("user", "user@user.com", passwordEncoder.encode("pass"),"USER");
        ClassPathResource imgFile0 = new ClassPathResource("static/images/emptyImage.png");
        byte[] imageBytes0;
        try (InputStream inputStream = imgFile0.getInputStream()) {
            imageBytes0 = inputStream.readAllBytes();
        }
        Blob imageBlob0 = new SerialBlob(imageBytes0);
        user.setImgUser(imageBlob0);

        if (!userRepository.existsByEmail("user@user.com")) {
            userService.save(user);
        }




        //Trainings Examples
        Training chestPlan = new Training("Chest Plan","80%",60,"Increase volume", "Press de banca : 4x8-10\n" +
                "                Press inclinado con mancuernas: 4x10\n" +
                "                Fondos en paralelas: 3x10\n" +
                "                Cruces en polea: 4x12");

        Training armsPlan = new Training("Arms Plan","70%",45,"Increase volume", "Curl con barra: 4x10\n" +
                "                Curl martillo con mancuernas: 3x12\n" +
                "                Press francés: 4x10\n" +
                "                Fondos en paralelas: 3x10");

        Training legsPlan = new Training("Legs Plan","100%",90,"Increase volume", "Sentadillas: 4x8-10\n" +
                "               Peso muerto rumano: 3x12\n" +
                "               Extensiones de cuádriceps: 3x15\n" +
                "               Elevaciones de pantorrilla: 4x15");

        Training absPlan = new Training("Abs Plan", "50%", 30, "Lose weight", "Crunch en máquina: 4x15\n" +
                "                Toques de talón (oblicuos): 3x20\n" +
                "                Rodillo abdominal (ab wheel): 3x10\n" +
                "                Plancha (estática): 3x45s");

        Training backPlan = new Training ("Back Plan", "90%", 90, "Increase volume", "Dominadas: 4x8-10\n" +
                "                Remo con barra: 4x10\n" +
                "                Jalón al pecho en polea: 3x12\n" +
                "                Remo con mancuerna: 3x12" );

        Training shoulderPlan = new Training ("Shoulder Plan", "70%", 60, "Increase volume", "Press militar con barra: 4x10\n" +
                "                Elevaciones laterales: 3x12\n" +
                "                Pájaros (elevaciones posteriores): 3x12\n" +
                "                Encogimientos con barra (trapecios): 4x12");

        Training nuevo1 = new Training("Chest Plan","80%",60,"Increase volume", "Press de banca : 4x8-10\n" +
                "                Press inclinado con mancuernas: 4x10\n" +
                "                Fondos en paralelas: 3x10\n" +
                "                Cruces en polea: 4x12");

        Training nuevo2 = new Training("Arms Plan","70%",45,"Increase volume", "Curl con barra: 4x10\n" +
                "                Curl martillo con mancuernas: 3x12\n" +
                "                Press francés: 4x10\n" +
                "                Fondos en paralelas: 3x10");

        Training nuevo3 = new Training("Legs Plan","100%",90,"Increase volume", "Sentadillas: 4x8-10\n" +
                "               Peso muerto rumano: 3x12\n" +
                "               Extensiones de cuádriceps: 3x15\n" +
                "               Elevaciones de pantorrilla: 4x15");

        Training nuevo4 = new Training("Abs Plan", "50%", 30, "Lose weight", "Crunch en máquina: 4x15\n" +
                "                Toques de talón (oblicuos): 3x20\n" +
                "                Rodillo abdominal (ab wheel): 3x10\n" +
                "                Plancha (estática): 3x45s");

        Training nuevo5 = new Training ("Back Plan", "90%", 90, "Increase volume", "Dominadas: 4x8-10\n" +
                "                Remo con barra: 4x10\n" +
                "                Jalón al pecho en polea: 3x12\n" +
                "                Remo con mancuerna: 3x12" );

        Training nuevo6 = new Training ("Shoulder Plan", "70%", 60, "Increase volume", "Press militar con barra: 4x10\n" +
                "                Elevaciones laterales: 3x12\n" +
                "                Pájaros (elevaciones posteriores): 3x12\n" +
                "                Encogimientos con barra (trapecios): 4x12");


        ClassPathResource imgFile1 = new ClassPathResource("static/images/plan_pecho.jpg");
        byte[] imageBytes1;
        try (InputStream inputStream = imgFile1.getInputStream()) {
            imageBytes1 = inputStream.readAllBytes();
        }
        Blob imageBlob1 = new SerialBlob(imageBytes1);
        chestPlan.setImgTraining(imageBlob1);

        ClassPathResource imgFile2 = new ClassPathResource("static/images/plan_brazo.jpeg");
        byte[] imageBytes2;
        try (InputStream inputStream = imgFile2.getInputStream()) {
            imageBytes2 = inputStream.readAllBytes();
        }
        Blob imageBlob2 = new SerialBlob(imageBytes2);
        armsPlan.setImgTraining(imageBlob2);

        ClassPathResource imgFile3 = new ClassPathResource("static/images/plan_pierna.jpeg");
        byte[] imageBytes3;
        try (InputStream inputStream = imgFile3.getInputStream()) {
            imageBytes3 = inputStream.readAllBytes();
        }
        Blob imageBlob3 = new SerialBlob(imageBytes3);
        legsPlan.setImgTraining(imageBlob3);

        ClassPathResource imgFile4 = new ClassPathResource("static/images/plan_abs.jpg");
        byte[] imageBytes4;
        try (InputStream inputStream = imgFile4.getInputStream()) {
            imageBytes4 = inputStream.readAllBytes();
        }
        Blob imageBlob4 = new SerialBlob(imageBytes4);
        absPlan.setImgTraining(imageBlob4);

        ClassPathResource imgFile5 = new ClassPathResource("static/images/plan_espalda.jpeg");
        byte[] imageBytes5;
        try (InputStream inputStream = imgFile5.getInputStream()) {
            imageBytes5 = inputStream.readAllBytes();
        }
        Blob imageBlob5 = new SerialBlob(imageBytes5);
        backPlan.setImgTraining(imageBlob5);

        ClassPathResource imgFile6 = new ClassPathResource("static/images/plan_hombros.jpeg");
        byte[] imageBytes6;
        try (InputStream inputStream = imgFile6.getInputStream()) {
            imageBytes6 = inputStream.readAllBytes();
        }
        Blob imageBlob6 = new SerialBlob(imageBytes6);
        shoulderPlan.setImgTraining(imageBlob6);

        chestPlan = trainingService.createTraining(chestPlan, null); 
        armsPlan = trainingService.createTraining(armsPlan, null);
        legsPlan = trainingService.createTraining(legsPlan, null);
        absPlan = trainingService.createTraining(absPlan, null);
        backPlan = trainingService.createTraining(backPlan, null);
        shoulderPlan = trainingService.createTraining(shoulderPlan, null);
        nuevo1 = trainingService.createTraining(nuevo1, null);
        nuevo2 = trainingService.createTraining(nuevo2, null);
        nuevo3 = trainingService.createTraining(nuevo3, null);
        nuevo4 = trainingService.createTraining(nuevo4, null);
        nuevo5 = trainingService.createTraining(nuevo5, null);
        nuevo6 = trainingService.createTraining(nuevo6, null);





        //Nutritions Examples
        Nutrition caloricDeficit = new Nutrition ("Caloric Deficit", 100, "Lose weight",
                "Desayuno: 2 huevos + café\n" +
                        "Comida: Ensalada + 100g pechuga de pollo\n" +
                        "Merienda: Rebanada de pan integral\n" +
                        "Cena: Verduras + 150g de pescado");

        Nutrition caloricSurplus = new Nutrition ("Caloric Supurplus", 300, "Increase weight",
                "Desayuno: 6 huevos + 60g de avena con leche\n" +
                        "Comida: Taza de arroz + 150g de carne magra\n" +
                        "Merienda: Batido de proteinas + frutos secos\n" +
                        "Cena: 150g de salmon + 200g de patata");

        Nutrition maintenanceDiet = new Nutrition ("Maintenance Diet", 200, "Maintenance weight",
                "Desayuno: 2 huevos revueltos + 50 g de avena\n" +
                        "Comida: 150 g de pollo + ensalada con aceite\n" +
                        "Merienda: Yogur griego natural con almendras\n" +
                        "Cena: 120 g de salmón + verduras salteadas");

        ClassPathResource imgFile7 = new ClassPathResource("static/images/deficitcalorico.jpeg");
        byte[] imageBytes7;
        try (InputStream inputStream = imgFile7.getInputStream()) {
            imageBytes7 = inputStream.readAllBytes();
        }
        Blob imageBlob7 = new SerialBlob(imageBytes7);
        caloricDeficit.setImgNutrition(imageBlob7);

        ClassPathResource imgFile8 = new ClassPathResource("static/images/volumen.jpeg");
        byte[] imageBytes8;
        try (InputStream inputStream = imgFile8.getInputStream()) {
            imageBytes8 = inputStream.readAllBytes();
        }
        Blob imageBlob8 = new SerialBlob(imageBytes8);
        caloricSurplus.setImgNutrition(imageBlob8);

        ClassPathResource imgFile9 = new ClassPathResource("static/images/mantenimiento.jpg");
        byte[] imageBytes9;
        try (InputStream inputStream = imgFile9.getInputStream()) {
            imageBytes9 = inputStream.readAllBytes();
        }
        Blob imageBlob9 = new SerialBlob(imageBytes9);
        maintenanceDiet.setImgNutrition(imageBlob9);

        caloricDeficit = nutritionService.createNutrition(caloricDeficit, null);
        caloricSurplus = nutritionService.createNutrition(caloricSurplus, null);
        maintenanceDiet = nutritionService.createNutrition(maintenanceDiet, null);

        //training comments examples
        TrainingComment descansos = new TrainingComment("Es importante hacer descansos entre series para evitar fatigar el músculo y optimizar el entrenamiento.", "Descansos");
        TrainingComment hidratación = new TrainingComment("Dado que esta rutina de ejercicios es muy aeróbica, la correcta hidratación es extremadamente importante.", "Hidratación");
        TrainingComment entidad1 = new TrainingComment("Este entrenamiento es perfecto para quienes están comenzando. Las instrucciones son claras y fáciles de seguir.", "Entrenamiento Básico");
        TrainingComment entidad2 = new TrainingComment("Me ha ayudado mucho a mejorar mi fuerza, aunque algunos ejercicios son bastante exigentes. Lo recomiendo para quienes buscan progresar en sus levantamientos.", "Fuerza");
        TrainingComment entidad3 = new TrainingComment("Excelente rutina para quemar calorías, aunque al principio parece abrumadora. Los resultados a largo plazo son visibles.", "Cardio Intenso");
        TrainingComment entidad4 = new TrainingComment("Las series de piernas son intensas, pero se nota la mejora. Requiere algo de tiempo para adaptarse, pero vale la pena.", "Rutina de Piernas");
        TrainingComment entidad5 = new TrainingComment("Los ejercicios para espalda son muy efectivos, aunque los de bíceps podrían ser más variados. Buen enfoque en la técnica.", "Espalda y Bíceps");
        TrainingComment entidad6 = new TrainingComment("Perfecto para trabajar la parte superior del cuerpo. Me encanta la combinación de ejercicios que realmente desafían los músculos.", "Pecho y Tríceps");
        TrainingComment entidad7 = new TrainingComment("Ideal para mejorar la agilidad y la fuerza en actividades cotidianas. Aunque es un poco difícil al principio, te ayuda a moverte mejor.", "Entrenamiento Funcional");
        TrainingComment entidad8 = new TrainingComment("Un entrenamiento increíblemente intenso. Perfecto para quemar grasa rápidamente, pero definitivamente es para quienes tienen una buena base física.", "HIIT");
        TrainingComment entidad9 = new TrainingComment("Este entrenamiento es muy equilibrado y trabaja todo el cuerpo. Si buscas algo integral, es la mejor opción.", "Entrenamiento Full Body");
        TrainingComment entidad10 = new TrainingComment("Un reto constante. Los ejercicios son muy variados y motivan a seguir mejorando, aunque es muy demandante.", "CrossFit");
        TrainingComment entidad11 = new TrainingComment("Excelente para fortalecer el core y mejorar la flexibilidad. Es un buen complemento para otros entrenamientos.", "Pilates");
        TrainingComment entidad12 = new TrainingComment("Me ayuda mucho a relajarte después de entrenamientos intensos. Muy beneficioso para la mente y el cuerpo.", "Yoga");
        TrainingComment entidad13 = new TrainingComment("Trabajo muy específico que realmente se siente en la zona abdominal. Ideal para marcar esa parte del cuerpo.", "Abdominales");
        TrainingComment entidad14 = new TrainingComment("El trabajo en piernas y glúteos es brutal. Sientes la quema durante y después de cada serie. Muy efectivo.", "Glúteos y Piernas");
        TrainingComment entidad15 = new TrainingComment("Aunque al principio no veía resultados, con constancia la flexibilidad mejora mucho. Recomiendo practicarlo regularmente.", "Rutina de Flexibilidad");
        TrainingComment entidad16 = new TrainingComment("Perfecto para empezar, pero a medida que vas avanzando necesitas más intensidad. Muy adecuado para aprender la técnica.", "Entrenamiento para Principiantes");
        TrainingComment entidad17 = new TrainingComment("Es muy desafiante y te empuja al límite, pero los resultados son impresionantes. Es para quienes ya tienen experiencia.", "Entrenamiento para Avanzados");
        TrainingComment entidad18 = new TrainingComment("Muy útil para mejorar la resistencia física. Se siente el progreso después de un par de semanas de práctica.", "Entrenamiento de Resistencia");
        TrainingComment entidad19 = new TrainingComment("Me encanta la variedad de este entrenamiento, que combina fuerza y cardio en un mismo circuito. Muy divertido y efectivo.", "Trabajo en Circuito");
        TrainingComment entidad20 = new TrainingComment("Muy útil para mejorar la potencia, especialmente en deportes. Los ejercicios son muy dinámicos.", "Entrenamiento para la Fuerza Explosiva");
        TrainingComment entidad21 = new TrainingComment("Es un entrenamiento que realmente acelera el metabolismo y se nota en los resultados. Ideal para perder peso.", "Entrenamiento para Quemar Grasa");
        TrainingComment entidad22 = new TrainingComment("Intenso pero muy efectivo para tonificar todo el cuerpo. Asegúrate de estar preparado antes de intentar esta rutina.", "Entrenamiento de Alta Intensidad");
        TrainingComment entidad23 = new TrainingComment("Muy útil para mejorar la velocidad y coordinación. Ideal para quienes practican deportes que requieren rapidez.", "Entrenamiento de Agilidad");
        TrainingComment entidad24 = new TrainingComment("Es un entrenamiento clásico que nunca pasa de moda. Perfecto para ganar masa muscular de manera constante.", "Entrenamiento con Pesas");
        TrainingComment entidad25 = new TrainingComment("Me ha ayudado a mejorar mucho mi postura. Es una rutina simple pero muy efectiva para mantener la espalda en forma.", "Entrenamiento para la Postura");
        TrainingComment entidad26 = new TrainingComment("Una rutina bastante exigente, pero con excelentes resultados a nivel cardiovascular. Muy recomendable para quienes quieren mejorar la salud del corazón.", "Entrenamiento para la Resistencia Cardiovascular");
        TrainingComment entidad27 = new TrainingComment("Muy relajante y útil para la recuperación muscular después de entrenamientos intensos. Ayuda mucho a evitar lesiones.", "Entrenamiento de Estiramiento");
        TrainingComment entidad28 = new TrainingComment("Este entrenamiento es perfecto para definir los músculos sin ganar demasiado volumen. Ideal para quienes buscan una figura más esculpida.", "Entrenamiento para Tonificar");
        TrainingComment entidad29 = new TrainingComment("Es perfecto para quienes nunca han hecho estiramientos. Las posturas son fáciles de seguir, pero igualmente efectivas.", "Entrenamiento de Flexibilidad para Principiantes");
        TrainingComment entidad30 = new TrainingComment("Excelente rutina para fortalecer el core. Se nota mucho la diferencia después de algunas semanas de práctica.", "Entrenamiento para Core");
        

        entidad3.setIsNotified(true);
        entidad5.setIsNotified(true);
        entidad11.setIsNotified(true);

        trainingCommentService.createTrainingComment(descansos, chestPlan,null);
        trainingCommentService.createTrainingComment(hidratación, chestPlan, null);
        trainingCommentService.createTrainingComment(entidad1, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad2, armsPlan,null);
        trainingCommentService.createTrainingComment(entidad3, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad4, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad5, legsPlan,null);
        trainingCommentService.createTrainingComment(entidad6, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad7, absPlan,null);
        trainingCommentService.createTrainingComment(entidad8, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad9, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad10, backPlan,null);
        trainingCommentService.createTrainingComment(entidad11, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad12, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad13, shoulderPlan,null);
        trainingCommentService.createTrainingComment(entidad14, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad15, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad16, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad17, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad18, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad19, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad20, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad21, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad22, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad23, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad24, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad25, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad26, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad27, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad28, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad29, chestPlan,null);
        trainingCommentService.createTrainingComment(entidad30, chestPlan,null);

        //nutrition comment examples
        NutritionComment variedad = new NutritionComment("Es importante comer variado", "Variedad");
        NutritionComment frutas = new NutritionComment("Es necesario comer mucha fruta", "Frutas");
        NutritionComment comentario1 = new NutritionComment("Importante consumir suficiente proteína para la recuperación muscular.", "Proteínas esenciales");
        NutritionComment comentario2 = new NutritionComment("Beber suficiente agua mejora el rendimiento y la recuperación.", "Hidratación");
        NutritionComment comentario3 = new NutritionComment("Los carbohidratos son clave para mantener la energía durante el ejercicio.", "Carbohidratos y energía");
        NutritionComment comentario4 = new NutritionComment("Las grasas saludables son esenciales para la función hormonal y la energía.", "Grasas saludables");
        NutritionComment comentario5 = new NutritionComment("Consumir frutas y verduras aporta nutrientes esenciales para el cuerpo.", "Vitaminas y minerales");
        NutritionComment comentario6 = new NutritionComment("Comer antes y después del entrenamiento mejora el rendimiento y la recuperación.", "Timing de comidas");
        NutritionComment comentario7 = new NutritionComment("Los suplementos pueden ayudar, pero la dieta siempre debe ser la base.", "Suplementación");
        NutritionComment comentario8 = new NutritionComment("Consumir suficiente fibra mejora la digestión y la salud intestinal.", "Fibra y digestión");
        NutritionComment comentario9 = new NutritionComment("Para perder peso, es clave consumir menos calorías de las que se gastan.", "Déficit calórico");
        NutritionComment comentario10 = new NutritionComment("Para ganar masa muscular, es necesario un superávit calórico.", "Superávit calórico");
        NutritionComment comentario11 = new NutritionComment("Un buen desayuno debe incluir proteínas, carbohidratos y grasas saludables.", "Desayuno equilibrado");
        NutritionComment comentario12 = new NutritionComment("El ayuno intermitente puede ayudar a controlar el apetito y mejorar la quema de grasa.", "Ayuno intermitente");
        NutritionComment comentario13 = new NutritionComment("Los electrolitos son clave para la hidratación, especialmente después de entrenar.", "Electrolitos");
        NutritionComment comentario14 = new NutritionComment("Disminuir el azúcar mejora la salud metabólica y reduce la inflamación.", "Reducción de azúcar");
        NutritionComment comentario15 = new NutritionComment("Cada comida debe incluir proteínas, grasas saludables y carbohidratos adecuados.", "Comidas balanceadas");
        NutritionComment comentario16 = new NutritionComment("Incluir proteínas magras ayuda a la regeneración muscular y acelera la recuperación.", "Proteínas magras");
        NutritionComment comentario17 = new NutritionComment("El agua es esencial para mantener el rendimiento físico y mental durante todo el día.", "Hidratación continua");
        NutritionComment comentario18 = new NutritionComment("Los carbohidratos complejos como la avena son ideales para mantener niveles estables de energía.", "Carbohidratos complejos");
        NutritionComment comentario19 = new NutritionComment("Consumir aguacate y frutos secos aporta grasas saludables que benefician el corazón.", "Grasas buenas");
        NutritionComment comentario20 = new NutritionComment("Tomar un multivitamínico puede ayudar a prevenir deficiencias en la dieta.", "Suplementación de vitaminas");
        NutritionComment comentario21 = new NutritionComment("Comer en pequeñas cantidades durante el día puede mejorar la digestión y el metabolismo.", "Comidas frecuentes");
        NutritionComment comentario22 = new NutritionComment("Las verduras de hoja verde son excelentes para aportar fibra y antioxidantes.", "Verduras saludables");
        NutritionComment comentario23 = new NutritionComment("Mantenerse en un déficit calórico moderado es más efectivo para perder peso que hacerlo de manera drástica.", "Déficit moderado");
        NutritionComment comentario24 = new NutritionComment("Para aumentar músculo, los batidos de proteínas pueden ser una buena opción después de entrenar.", "Batidos post entrenamiento");
        NutritionComment comentario25 = new NutritionComment("El desayuno es crucial para iniciar el día con energía y evitar el hambre excesiva.", "Importancia del desayuno");
        NutritionComment comentario26 = new NutritionComment("El ayuno intermitente puede mejorar la sensibilidad a la insulina y la quema de grasa abdominal.", "Beneficios del ayuno");
        NutritionComment comentario27 = new NutritionComment("Los electrolitos ayudan a evitar calambres y mantener el equilibrio de fluidos durante el ejercicio intenso.", "Importancia de los electrolitos");
        NutritionComment comentario28 = new NutritionComment("Reducir el consumo de azúcar refinada puede mejorar la energía y reducir la inflamación.", "Reducción de azúcar refinada");
        NutritionComment comentario29 = new NutritionComment("Mantener un balance adecuado entre nutrientes esenciales ayuda a optimizar el rendimiento físico y mental.", "Balance nutricional");
        NutritionComment comentario30 = new NutritionComment("Es importante realizar un seguimiento de la ingesta de alimentos para asegurar que cubrimos nuestras necesidades nutricionales.", "Monitoreo nutricional");
        
        comentario5.setIsNotified(true);
        comentario7.setIsNotified(true);

        nutritionCommentService.createNutritionComment(variedad,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(frutas,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario1,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario2,caloricSurplus,null);
        nutritionCommentService.createNutritionComment(comentario3,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario4,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario5,caloricSurplus,null);
        nutritionCommentService.createNutritionComment(comentario6,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario7,caloricSurplus,null);
        nutritionCommentService.createNutritionComment(comentario8,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario9,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario10,maintenanceDiet,null);
        nutritionCommentService.createNutritionComment(comentario11,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario12,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario13,maintenanceDiet,null);
        nutritionCommentService.createNutritionComment(comentario14,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario15,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario16,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario17,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario18,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario19,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario20,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario21,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario22,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario23,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario24,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario25,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario26,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario27,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario28,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario29,caloricDeficit,null);
        nutritionCommentService.createNutritionComment(comentario30,caloricDeficit,null);
    }
    
}
