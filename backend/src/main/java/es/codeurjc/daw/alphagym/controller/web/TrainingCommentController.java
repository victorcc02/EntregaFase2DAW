package es.codeurjc.daw.alphagym.controller.web;

import java.security.Principal;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import es.codeurjc.daw.alphagym.model.Training;
import es.codeurjc.daw.alphagym.model.TrainingComment;
import es.codeurjc.daw.alphagym.model.User;
import es.codeurjc.daw.alphagym.service.TrainingCommentService;
import es.codeurjc.daw.alphagym.service.TrainingService;
import es.codeurjc.daw.alphagym.service.UserService;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class TrainingCommentController {

    @Autowired
    private UserService userService;
    @Autowired
    private TrainingCommentService trainingCommentService;
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

    @GetMapping("/trainingComments/{trainingId}")
    public String showAllTrainingComments(Model model, @PathVariable Long trainingId, Principal principal) {
        model.addAttribute("training", trainingService.getTraining(trainingId));

        boolean isAdmin = false;
        Long loggedUserId = null;

        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            if (user.isPresent()) {
                model.addAttribute("logged", true);
                isAdmin = user.get().isRole("ADMIN");  // Validate if the user is an admin
                loggedUserId = user.get().getId();
            }
        }

        List<TrainingComment> trainingComments = trainingCommentService.getTrainingComments(trainingId);
        if (!(trainingComments == null)){
            List<Map<String, Object>> commentList = new ArrayList<>();

            for (TrainingComment trainingComment : trainingComments) {
                Map<String, Object> commentMap = new HashMap<>();
                commentMap.put("id", trainingComment.getId());
                commentMap.put("name", trainingComment.getName());
                commentMap.put("description", trainingComment.getDescription());

                //Validate for only admin and author can edit the comment
                boolean canEdit = isAdmin || (trainingComment.getUser() != null && trainingComment.getUser().getId().equals(loggedUserId));

                commentMap.put("canEdit", canEdit);
                commentMap.put("isAdmin", isAdmin);
                commentList.add(commentMap);
            }
            model.addAttribute("comment", commentList);
        } else {
            model.addAttribute("comment", trainingComments);
        }

        return "commentTraining";
    }

    @GetMapping("/trainingComments/{trainingId}/newComment")
    public String newComment(Model model, @PathVariable Long trainingId){
        return "newComment";
    }

    @PostMapping("/trainingComments/{trainingId}")
    public String createComment(Model model,@PathVariable Long trainingId, @RequestParam String commentTitle,@RequestParam String commentText, Principal principal){
        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            TrainingComment trainingComment = new TrainingComment(commentText, commentTitle);
            Training training = trainingService.getTraining(trainingId);

            if (user.isPresent()) {
                if (user.get().isRole("ADMIN")) {
                    trainingCommentService.createTrainingComment(trainingComment, training, null);
                }else{
                    trainingCommentService.createTrainingComment(trainingComment, training, user.get());
                }
            }

        }
        return "redirect:/trainingComments/" + trainingId;
    }

    @GetMapping("/trainingComments/{trainingId}/{commentId}/delete")
    public String deleteComment(Model model, @PathVariable Long trainingId, @PathVariable Long commentId){
        Training training = trainingService.getTraining(trainingId);
        trainingCommentService.deleteCommentbyId(training, commentId);
        return "redirect:/trainingComments/" + trainingId;
    }

    @GetMapping("/trainingComments/{trainingId}/{commentId}/report")
    public String reportComment(Model model, @PathVariable Long trainingId, @PathVariable Long commentId){
        trainingCommentService.reportCommentbyId(commentId);
        return "redirect:/trainingComments/" + trainingId;
    }

    @GetMapping("/trainingComments/{trainingId}/{commentId}/editcomment")
    public String editComment(Model model, @PathVariable Long trainingId, @PathVariable Long commentId) {
        model.addAttribute("comment", trainingCommentService.getCommentById(commentId));
        return "editComment";
    }

    @PostMapping("/trainingComments/{trainingId}/{commentId}")
    public String updateComment(Model model, @PathVariable Long trainingId, @PathVariable Long commentId,
                                @RequestParam String commentTitle, @RequestParam String commentText) {
        TrainingComment trainingComment = trainingCommentService.getCommentById(commentId);
        if (trainingComment != null) {
            trainingComment.setDescription(commentText);
            trainingComment.setName(commentTitle);
            trainingCommentService.updateComment(trainingComment);
        }
        return "redirect:/trainingComments/" + trainingId;
    }

    @GetMapping("/trainingComments/{commentId}/unreport")
    public String unreportComment(Model model, @PathVariable Long commentId) {
        trainingCommentService.unreportCommentbyId(commentId);
        return "redirect:/admin";
    }

    @GetMapping("/trainingComments/{commentId}/editcommentAdmin")
    public String editCommentAdmin(Model model, @PathVariable Long commentId) {
        return trainingCommentService.editCommentAdminService(model, commentId);
    }

    @GetMapping("/trainingComments/{commentId}/deleteAdmin")
    public String deleteCommentAdmin(Model model, @PathVariable Long commentId) {
        return trainingCommentService.deleteCommentAdminService(model, commentId);
    }

    @GetMapping("/trainingComments/{trainingId}/moreComments")
    public String loadMoreComments2(Model model, @PathVariable Long trainingId, @RequestParam(defaultValue = "1") int page, Principal principal) {
        List<TrainingComment> comments = trainingCommentService.getPaginatedComments(trainingId, page, 10);
        Training training = trainingService.getTraining(trainingId);
        model.addAttribute("training", training);

        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            if (user.isPresent()) {
                model.addAttribute("logged", true);
                List<Map<String, Object>> commentList = new ArrayList<>();
                for (TrainingComment trainingComment : comments) {
                    Map<String, Object> commentMap = new HashMap<>();
                    commentMap.put("id", trainingComment.getId());
                    commentMap.put("name", trainingComment.getName());
                    commentMap.put("description", trainingComment.getDescription());

                    //Validate for only admin and author can edit the comment
                    boolean canEdit = user.get().isRole("ADMIN") || (trainingComment.getUser() != null && trainingComment.getUser().getId().equals(user.get().getId()));

                    commentMap.put("canEdit", canEdit);
                    commentMap.put("isAdmin", user.get().isRole("ADMIN"));
                    commentList.add(commentMap);
                }
                model.addAttribute("comment", commentList);
            }
        }else {
            model.addAttribute("comment", comments);
        }

        return "fragments/commentsTrainingList";
    }

}