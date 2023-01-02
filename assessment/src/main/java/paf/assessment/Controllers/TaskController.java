package paf.assessment.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import paf.assessment.Models.task;
import paf.assessment.Services.TaskRepository;
import paf.assessment.Services.TodoService;
import paf.assessment.Services.TodoSvcException;
import paf.assessment.Services.UserService;

@Controller
@RequestMapping(path="/home")
public class TaskController {
    
    @Autowired
    UserService service;

    @Autowired
    TaskRepository repo;

    @Autowired
    TodoService tdsvc;

    @PostMapping
    public String postHomepage(
    @RequestParam("save") String save,
    @RequestParam("todo") String toDo,
    @RequestParam("delete") String deleted,
    @RequestParam("tester") String tester,
    @RequestParam("username") String username, 
    Model model, 
    @RequestBody MultiValueMap<String, String> form,
    HttpSession sess,
    // If you have th:object in html code, NEED @ModelAttribute
    @ModelAttribute task task) {
        
        System.out.println("User: " + username);
        System.out.println("ModelAttribute Output: " + task.getDescription() + " " + task.getDue_date());

        // Trying out Http Session
        List<task> Tasks = (List<task>)sess.getAttribute("index");
        if (null == Tasks) {
            System.out.println("This is a new session");
            System.out.printf("session id = %s\n", sess.getId());
            Tasks = new ArrayList<>();
            sess.setAttribute("index", Tasks);
        }

        String description = form.getFirst("description");
        String priority = form.getFirst("priority");
        String date = form.getFirst("date");
        
        if(!toDo.equals("")){
            System.out.println("[Controller]: Creating new input To Do Field...");
            Tasks.add(new task(description, priority, date));
        }
        
        System.out.println(form);
        if(deleted.contains(",")) {
            System.out.println("DELETED: " + deleted);
            String[] results = deleted.split(",");
            // Because results is of the form ["", "", "5"] so we need to ignore all whitespace. 
            for (String item : results) {
                if(!item.equals("") & Tasks.size() != 1){
                    int delIndex = Integer.parseInt(item);
                    System.out.println("[Controller]: Deleting " + (delIndex - 1) + " from list of ToDo input fields");
                    Tasks.remove(delIndex -1);
                }       
            }
        }

        model.addAttribute("tasks", Tasks);

        if(!save.equals("")){
            // If only one task was saved
            if(Tasks.size() == 0){
                Tasks.add(new task(description, priority, date));
                int check = repo.insertTask(Tasks.get(0), username);
                System.out.println(check + " task entered into database");
            } 
            // Else, if a list of tasks were saved
             else {
                Tasks.clear();
                for (int i = 0; i < form.get("description").size(); i++) {
                    Tasks.add(new task(form.get("description").get(i),
                                        form.get("priority").get(i), 
                                        form.get("date").get(i)));
                }
                try {
                    tdsvc.upsertTask(username, Tasks);
                } catch (TodoSvcException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
            
            System.out.println(form.get("description"));
            System.out.println(form.get("priority"));
            System.out.println(form.get("date"));

            for (int i = 0; i < form.size(); i++) {
                System.out.println(form.values());
            }

            // When result returns, invalidate the session, so that Tasks array which controls the number of To-Do input fields are cleared.
            sess.invalidate();
            return "result";
        }
        
        
        // Optional<User> opt = service.findUserByUserId("abcd0001");
        
        // if(opt == null){
        //     System.out.println("USER NOT FOUND. Check user id");
        // } else{
        //     System.out.println(opt.get());
        // }


        // User user = new User();
        // user.create("bilibili", "Billy");
        
        // String result = service.insertUser(user);
        // System.out.println(result);

        

        
        return "home_template";
    }

}