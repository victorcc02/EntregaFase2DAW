package es.codeurjc.daw.alphagym.controller.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import es.codeurjc.daw.alphagym.dtosedit.Goal;
import es.codeurjc.daw.alphagym.model.Nutrition;
import es.codeurjc.daw.alphagym.model.User;
import es.codeurjc.daw.alphagym.service.NutritionService;
import es.codeurjc.daw.alphagym.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class NutritionController {

    @Autowired
    private NutritionService nutritionService;
    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public void addAttributes(Model model, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            if (user.isPresent()) {
                if (user.get().isRole("USER")) {
                    model.addAttribute("user", true);
                }
                if (user.get().isRole("ADMIN")) {
                    model.addAttribute("admin", true);
                }
            }
            model.addAttribute("logged", true);
            model.addAttribute("userName", principal.getName());
        } else {
            model.addAttribute("logged", false);
        }
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
    }

    @GetMapping("/nutritions")
    public String showAllDiets(Model model) {
        model.addAttribute("nutritions", nutritionService.getAllNutritions());
        return "nutrition";
    }

    @GetMapping("/nutritions/{id}")
    public String detailsNutrition(Model model, @PathVariable Long id, Principal principal) {
        Nutrition nutrition = nutritionService.getNutrition(id);
        if (nutrition == null) {
            return "redirect:/nutritions";
        }
        model.addAttribute("nutrition", nutrition);

        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            if (user.isPresent()) {
                Boolean isAdmin = user.get().isRole("ADMIN");

                Boolean canEdit = isAdmin
                        || (nutrition.getUser() != null && nutrition.getUser().getId().equals(user.get().getId()));
                Boolean isSubscribed = user.get().getNutritions().contains(nutrition);

                model.addAttribute("subscribed", isSubscribed);
                model.addAttribute("logged", true);
                model.addAttribute("admin", user.get().isRole("ADMIN")); // Add the variable "admin"
                model.addAttribute("canEdit", canEdit);
            }
        } else {
            model.addAttribute("logged", false);
            model.addAttribute("admin", false); // If not authenticated, the user is not an admin
        }

        return "showDiet";
    }

    @GetMapping("/nutritions/subscribe/{id}")
    public String subscribeToDiet(@PathVariable Long id, Principal principal) {
        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            if (user.isPresent()) {
                nutritionService.subscribeNutrition(id, user.get());
            }
        }
        return "redirect:/nutritions/" + id;
    }

    @GetMapping("/nutritions/unsubscribe/{id}")
    public String unsubscribeFromDiet(@PathVariable Long id, Principal principal) {
        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            if (user.isPresent()) {
                nutritionService.unsubscribeNutrition(id, user.get());
            }
        }
        return "redirect:/nutritions/" + id;
    }

    @GetMapping("/nutritions/newNutrition")
    public String createNutrition(Model model) {
        model.addAttribute("nutrition", new Nutrition());
        return "newDiet";
    }

    @PostMapping("/nutritions/newNutrition")
    public String createNutritionPost(@ModelAttribute Nutrition nutrition, Principal principal)
            throws SQLException, IOException {
        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            if (user.isPresent()) {
                if (user.get().isRole("ADMIN")) {
                    nutritionService.createNutrition(nutrition, null);
                } else {
                    nutritionService.createNutrition(nutrition, user.get());
                }
                return "redirect:/nutritions";
            }
        }
        return "redirect:/nutritions";
    }

    @GetMapping("/nutritions/editNutrition/{nutritionId}")
    public String editDiet(Model model, @PathVariable Long nutritionId) {
        Nutrition nutrition = nutritionService.getNutrition(nutritionId);
        String originalGoal = nutrition.getGoal();

        List<Goal> goals = new ArrayList<>();
        goals.add(new Goal("Maintain weight", "Maintain weight".equals(originalGoal)));
        goals.add(new Goal("Increase weight", "Increase weight".equals(originalGoal)));
        goals.add(new Goal("Lose weight", "Lose weight".equals(originalGoal)));
        model.addAttribute("goals", goals);

        if (nutrition == null) {
            return "redirect:/nutritions";
        }

        model.addAttribute("nutrition", nutrition);
        return "editDiet";
    }

    @PostMapping("/nutritions/editNutrition/{nutritionId}")
    public String editDietPost(@ModelAttribute Nutrition nutrition, @PathVariable Long nutritionId,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile, Model model,
            Principal principal) {
        try {
            if (principal != null) {
                Optional<User> user = userService.findByEmail(principal.getName());
                Optional<Nutrition> optionalNutrition = nutritionService.findById(nutritionId);
                if (optionalNutrition.isPresent()) {
                    Nutrition existingNutrition = optionalNutrition.get();
                    if (imageFile != null && !imageFile.isEmpty()) {
                        nutrition.setImgNutrition(
                                BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
                        nutrition.setImage(true);
                    } else {
                        nutrition.setImgNutrition(existingNutrition.getImgNutrition()); // keep previous image
                    }
                    if (user.isPresent()) {
                        nutritionService.editDiet(nutritionId, nutrition, user.get());
                    }
                    return "redirect:/nutritions/" + nutritionId;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ha ocurrido un error.");
            return "redirect:/nutritions/editDiet/" + nutritionId + "?error=true";
        }

        return null;
    }

    @GetMapping("/nutritions/delete/{id}")
    public String deleteDietPost(@PathVariable Long id) {
        nutritionService.deleteDiet(id);
        return "redirect:/nutritions";
    }

    @GetMapping("/nutritions/deleteFromList/{id}")
    public String deleteDietFromListPost(@PathVariable Long id, Principal principal) {
        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            if (user.isPresent()) {
                nutritionService.unsubscribeNutrition(id, user.get());
                return "redirect:/account";
            }
        }
        return null;
    }

    @GetMapping("/nutritions/{id}/pdf")
    public ResponseEntity<InputStreamResource> generatePdf(@PathVariable Long id) {
        Nutrition nutrition = nutritionService.findById(id)
                .orElseThrow(() -> new RuntimeException("Nutrition not found"));

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph(nutrition.getName(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("Nutrition Details"));
            document.add(new Paragraph("ID: " + nutrition.getId()));
            document.add(new Paragraph("Name: " + nutrition.getName()));
            document.add(new Paragraph("Calories: " + nutrition.getCalories()));
            document.add(new Paragraph("Goal: " + nutrition.getGoal()));
            document.add(new Paragraph("Description: " + nutrition.getDescription()));

            document.close();

            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            InputStreamResource resource = new InputStreamResource(inputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=nutrition.pdf");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/nutrition/image/{nutritionId}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long nutritionId) throws SQLException {
        Optional<Nutrition> nutrition = nutritionService.findById(nutritionId);

        if (nutrition.isPresent() && nutrition.get().getImgNutrition() != null) {
            Blob imageBlob = nutrition.get().getImgNutrition();
            byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or what ever type is the image
                    .body(imageBytes);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/nutritions/moreDiets")
    public String loadMoreDiets(Model model, @RequestParam(defaultValue = "1") int page) {
        List<Nutrition> diets = nutritionService.getPaginatedDiets(page, 10);
        model.addAttribute("nutritions", diets);
        return "fragments/dietsList";
    }

}
