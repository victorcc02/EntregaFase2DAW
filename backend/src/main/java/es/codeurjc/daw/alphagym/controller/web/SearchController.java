package es.codeurjc.daw.alphagym.controller.web;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.daw.alphagym.model.Nutrition;
import es.codeurjc.daw.alphagym.model.Training;
import es.codeurjc.daw.alphagym.model.User;
import es.codeurjc.daw.alphagym.service.NutritionService;
import es.codeurjc.daw.alphagym.service.TrainingService;
import es.codeurjc.daw.alphagym.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SearchController {
    @Autowired
    private UserService userService;

    @Autowired
    private NutritionService nutritionService;

    @Autowired
    private TrainingService trainingService;

    @ModelAttribute("user")
    public void addAttributes(Model model, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            Optional <User> user = userService.findByEmail(principal.getName()); 
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

    @GetMapping("/search")     
    public String searchProducts(@RequestParam(required = false)String search, Model model, HttpServletRequest request) {
        
        String principal = request.getUserPrincipal().getName();
        Optional<User> admin = userService.findByEmail(principal);

        if (admin.isPresent()) {

           model.addAttribute("searchQuery", search != null ? search : "");

            //Search for trainings and nutrition related to the search term
            List<Training> trainings = trainingService.findByName(search);
            List<Nutrition> nutritions = nutritionService.findByName(search);

            model.addAttribute("trainings", trainings);
            model.addAttribute("nutritions", nutritions);

            return "search";
        }
        return "error"; 
    }
    
}
