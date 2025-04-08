package es.codeurjc.daw.alphagym.controller.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.daw.alphagym.model.Nutrition;
import es.codeurjc.daw.alphagym.model.NutritionComment;
import es.codeurjc.daw.alphagym.model.User;
import es.codeurjc.daw.alphagym.service.NutritionCommentService;
import es.codeurjc.daw.alphagym.service.NutritionService;
import es.codeurjc.daw.alphagym.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class NutritionCommentController {

    @Autowired
    private NutritionCommentService nutritionCommentService;
    @Autowired
    private UserService userService;
    @Autowired
    private NutritionService nutritionService;

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

    @GetMapping("/nutritionComments/{nutritionId}")
    public String showAllNutritionComments(Model model, @PathVariable Long nutritionId, Principal principal) {
        model.addAttribute("nutrition", nutritionService.getNutrition(nutritionId));
        model.addAttribute("comment", nutritionCommentService.getNutritionComments(nutritionId));
        boolean isAdmin = false;
        Long loggedUserId = null;
        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            if (user.isPresent()) {
                model.addAttribute("logged", true);
                isAdmin = user.get().isRole("ADMIN"); 
                loggedUserId = user.get().getId();
            }
        }

        List<NutritionComment> nutritionComments = nutritionCommentService.getNutritionComments(nutritionId);
        if (!(nutritionComments==null)){
            List<Map<String, Object>> commentList = new ArrayList<>();
            for (NutritionComment nutritionComment : nutritionComments) {
                Map<String, Object> commentMap = new HashMap<>();
                commentMap.put("id", nutritionComment.getId());
                commentMap.put("name", nutritionComment.getName());
                commentMap.put("description", nutritionComment.getDescription());

                //Validate for only admin and author can edit the comment
                boolean canEdit = isAdmin || (nutritionComment.getUser() != null && nutritionComment.getUser().getId().equals(loggedUserId));

                commentMap.put("canEdit", canEdit);
                commentMap.put("isAdmin", isAdmin);
                commentList.add(commentMap);
            }

            model.addAttribute("comment", commentList);
        }else{
            model.addAttribute("comment", nutritionComments);
        }
        return "commentNutrition";
    }

    @GetMapping("/nutritionComments/{nutritionId}/newComment")
    public String newComment(Model model, @PathVariable Long nutritionId) {
        return "newComment";
    }

    @PostMapping("/nutritionComments/{nutritionId}")
    public String createComment(Model model, @PathVariable Long nutritionId, @RequestParam String commentTitle,
            @RequestParam String commentText, Principal principal) {
        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            NutritionComment nutritionComment = new NutritionComment(commentText, commentTitle);
            Nutrition nutrition = nutritionService.getNutrition(nutritionId);
            if (user.isPresent()) {
                if (user.get().isRole("ADMIN")) {
                    nutritionCommentService.createNutritionComment(nutritionComment, nutrition, null);
                } else {
                    nutritionCommentService.createNutritionComment(nutritionComment, nutrition, user.get());
                }
            }
        }
        return "redirect:/nutritionComments/" + nutritionId;
    }

    @GetMapping("/nutritionComments/{nutritionId}/{commentId}/delete")
    public String deleteComment(Model model, @PathVariable Long nutritionId, @PathVariable Long commentId) {
        Nutrition nutrition = nutritionService.getNutrition(nutritionId);
        nutritionCommentService.deleteCommentbyId(nutrition, commentId);
        return "redirect:/nutritionComments/" + nutritionId;
    }

    @GetMapping("/nutritionComments/{nutritionId}/{commentId}/report")
    public String reportComment(Model model, @PathVariable Long nutritionId, @PathVariable Long commentId) {
        nutritionCommentService.reportCommentbyId(commentId);
        return "redirect:/nutritionComments/" + nutritionId;
    }

    @GetMapping("/nutritionComments/{nutritionId}/{commentId}/editcomment")
    public String editComment(Model model, @PathVariable Long nutritionId, @PathVariable Long commentId) {
        model.addAttribute("comment", nutritionCommentService.getCommentById(commentId));
        return "editComment";
    }

    @PostMapping("/nutritionComments/{nutritionId}/{commentId}")
    public String updateComment(Model model, @PathVariable Long nutritionId, @PathVariable Long commentId,
            @RequestParam String commentTitle, @RequestParam String commentText, Principal principal) {
        NutritionComment nutritionComment = nutritionCommentService.getCommentById(commentId);
        if (nutritionComment != null) {
            nutritionComment.setDescription(commentText);
            nutritionComment.setName(commentTitle);
            nutritionCommentService.updateComment(nutritionComment);
        }
        return "redirect:/nutritionComments/" + nutritionId;
    }

    
    @GetMapping("/nutritionComments/{nutritionId}/moreComments")
    public String loadMoreComments2(Model model, @PathVariable Long nutritionId, @RequestParam(defaultValue = "1") int page, Principal principal) {
        List<NutritionComment> comments = nutritionCommentService.getPaginatedComments(nutritionId, page, 10);
        Nutrition nutrition = nutritionService.getNutrition(nutritionId);
        model.addAttribute("nutrition", nutrition);

        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            if (user.isPresent()) {
                model.addAttribute("logged", true);
                List<Map<String, Object>> commentList = new ArrayList<>();
                for (NutritionComment nutritionComment : comments) {
                    Map<String, Object> commentMap = new HashMap<>();
                    commentMap.put("id", nutritionComment.getId());
                    commentMap.put("name", nutritionComment.getName());
                    commentMap.put("description", nutritionComment.getDescription());

                    // Validate for only admin and author can edit the comment
                    boolean canEdit = user.get().isRole("ADMIN") || (nutritionComment.getUser() != null && nutritionComment.getUser().getId().equals(user.get().getId()));

                    commentMap.put("canEdit", canEdit);
                    commentMap.put("isAdmin", user.get().isRole("ADMIN"));
                    commentList.add(commentMap);
                }
                model.addAttribute("comment", commentList);
            }
        }else {
            model.addAttribute("comment", comments);
        }

        return "fragments/commentsNutritionList";
    }
    
    @GetMapping("/nutritionComments/{commentId}/unreport")
    public String unreportComment(Model model, @PathVariable Long commentId) {
        nutritionCommentService.unreportCommentbyId(commentId);
        return "redirect:/admin";
    }

    @GetMapping("/nutritionComments/{commentId}/editcommentAdmin")
    public String editCommentAdmin(Model model, @PathVariable Long commentId) {
        return nutritionCommentService.editCommentAdminService(model, commentId);
    }

    @GetMapping("/nutritionComments/{commentId}/deleteAdmin")
    public String deleteCommentAdmin(Model model, @PathVariable Long commentId) {
        return nutritionCommentService.deleteCommentAdminService(model, commentId);
    }



}
