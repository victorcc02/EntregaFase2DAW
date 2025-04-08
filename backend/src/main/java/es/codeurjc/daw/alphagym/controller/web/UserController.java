package es.codeurjc.daw.alphagym.controller.web;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Optional;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import es.codeurjc.daw.alphagym.model.User;
import es.codeurjc.daw.alphagym.service.NutritionCommentService;
import es.codeurjc.daw.alphagym.service.TrainingCommentService;
import es.codeurjc.daw.alphagym.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TrainingCommentService trainingCommentService;

    @Autowired
    private NutritionCommentService nutritionCommentService;

    @ModelAttribute("user")
    public void addAttributes(Model model, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            Optional <User> user = userService.findByEmail(principal.getName()); //se usa getName porque asi se hace desde security
            if (user.isPresent()){
                if (user.get().isRole("USER")){
                    model.addAttribute("user", true);
                }
                if (user.get().isRole("ADMIN")){
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

    @GetMapping("/index")
    public String index(Model model, HttpServletRequest request) {

        return "index";
    }
    
    @GetMapping("/register")
    public String register(Model model, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal == null) {
            return "register";
        } else {
            return "redirect:/index";
        }
    }
    
    @PostMapping("/register")
    public String createUser(@RequestParam String name,@RequestParam String email, 
            @RequestParam String password, Model model) {

        try {
            // Check if user already exists with the given email
            Optional<User> existingUser = userService.findByEmail(email);
           
            if (existingUser.isPresent()) {
                // User exists, so we return an error message
                model.addAttribute("error", "El email ya está en uso");
                return "register"; 
            }

            // Create the user
            User user = userService.createUser(name, email, password, "USER");

            // Save the user in the database
            userService.save(user);

            return "redirect:/login"; 

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ha ocurrido un error.");
            return "login"; 
        }
    }
    
    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal == null) {
            return "login";
        } else {
            return "redirect:/index";
        }
    }

    @GetMapping("/account")
    public String profile(Model model, HttpServletRequest request) {

        String principal = request.getUserPrincipal().getName();
        Optional<User> user = userService.findByEmail(principal);

        if (user.isPresent()) {
            
            model.addAttribute("user", user.get());
            model.addAttribute("trainings", user.get().getTrainings());
            model.addAttribute("nutritions", user.get().getNutritions());
            
            return "account";

        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/account/image")
	public ResponseEntity<Object> downloadImage(Principal principal) throws SQLException {
        if (principal != null) {

            Optional<User> user = userService.findByEmail(principal.getName());

            if (user.isPresent() && user.get().getImgUser() != null) {

                Resource file = new InputStreamResource(user.get().getImgUser().getBinaryStream());

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                        .contentLength(user.get().getImgUser().length())
                        .body(file);
                } 
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/editAccount")
    public String editAccount(Model model, @RequestParam String name, 
                          @RequestParam String email, @RequestParam MultipartFile imageField, 
                          Principal principal, HttpServletRequest request) 
                          throws IOException {

        try {
        
            Optional<User> userOptional = userService.findByEmail(principal.getName());

            if (userOptional.isPresent()) {
                User updateUser = userOptional.get();

                boolean emailChanged = !updateUser.getEmail().equals(email);
				updateUser.setEmail(email);

                if (name != null && !name.trim().isEmpty()) {
                    updateUser.setName(name);
                    userService.updateUserName(updateUser.getId(), name);
                }

                if (email != null && !email.trim().isEmpty()) {
                    updateUser.setEmail(email);
                    userService.updateUserEmail(updateUser.getId(), email);
                }

                if (imageField != null && !imageField.isEmpty()) {
					updateUser.setImgUser(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
                    updateUser.setImage(true);  
				}

                userService.save(updateUser);

                if (emailChanged) {
					request.getSession().invalidate();
					SecurityContextHolder.clearContext();
					return "redirect:/login";
				}

                return "redirect:/account"; 

            } else {
                model.addAttribute("error", "Usuario no encontrado");
                return "editAccount"; 
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ha ocurrido un error inesperado.");
            return "editAccount"; 
        }
    }
    
    @GetMapping("/admin")
    public String admin(Model model, HttpServletRequest request) {

        String principal = request.getUserPrincipal().getName();
        Optional<User> admin = userService.findByEmail(principal);

        if (admin.isPresent()) {

            Long[] reportsArray1 = trainingCommentService.getReportAmmmounts(); 
            Long[] reportsArray2 = nutritionCommentService.getReportAmmmounts();

            model.addAttribute("admin", admin.get());
            model.addAttribute("reportedCount", reportsArray1[0] + reportsArray2[0]);
            model.addAttribute("notReportedCount", reportsArray1[1] + reportsArray2[1]);
            model.addAttribute("trainingComments", trainingCommentService.getReportedComments());
            model.addAttribute("nutritionComments", nutritionCommentService.getReportedComments());
        
            return "admin";

        } else {
            return "redirect:/login";
        }
    }
    
}