package com.otu.springboottaskmgmt.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.otu.springboottaskmgmt.model.Task;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @GetMapping
    public String showTaskForm() {
        return "taskForm";
    }

    @PostMapping
    public String submitTaskForm(@RequestParam String name, HttpSession session) {
        Task task = new Task();
        task.setName(name);

        List<Task> tasks = (List<Task>) session.getAttribute("tasks");
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
        session.setAttribute("tasks", tasks);

        return "redirect:/tasks/success";
    }

    @GetMapping("/success")
    public String showSuccessPage(HttpSession session, Model model) {
        List<Task> tasks = (List<Task>) session.getAttribute("tasks");
        if (tasks != null && !tasks.isEmpty()) {
            tasks.forEach(t -> System.out.println("Task Name: " + t.getName()));
        } else {
            System.out.println("No tasks found.");
        }
        model.addAttribute("tasks", tasks);
        return "taskSuccess";
    }
}
