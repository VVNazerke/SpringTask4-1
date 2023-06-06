package kz.bitlab.task41.controller;

import kz.bitlab.task41.db.DBManager;
import kz.bitlab.task41.model.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String index(Model model){
        ArrayList<Task> tasks = DBManager.getAllTasks();
        model.addAttribute("zadachi", tasks);
        return "index";
    }

    @GetMapping(value = "/details/{id}")
    public String detailsPage(Model model,
                              @PathVariable(name = "id") Long taskId){
        Task task = DBManager.getTaskById(taskId);
        model.addAttribute("zadacha", task);
        return "details";
    }

    @PostMapping(value = "/save")
    public String saveDetails(@RequestParam(name = "s_id") Long id,
                              @RequestParam(name = "s_name") String name,
                              @RequestParam(name = "s_description") String description,
                              @RequestParam(name = "s_deadlineDate") Date deadlineDate,
                              @RequestParam(name = "s_completed") Boolean completed){
        Task task = DBManager.getTaskById(id);
        task.setName(name);
        task.setDeadlineDate(deadlineDate);
        task.setCompleted(completed);
        task.setDescription(description);
        DBManager.saveDetails(task);
        return "redirect:/";
    }

    @PostMapping(value = "/delete")
    public String deleteTask(@RequestParam(name = "d_id") Long id){
        System.out.println("ID:"+id);
        DBManager.deleteTask(id);
        return "redirect:/";
    }

    @PostMapping(value = "/add-task")
    public String addTask(@RequestParam(name = "a_name") String name,
                          @RequestParam(name = "a_description") String description,
                          @RequestParam(name = "a_deadline") Date deadline){
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setDeadlineDate(deadline);
        task.setCompleted(false);
        DBManager.addTask(task);
        return "redirect:/";
    }


}
