package com.geekbrains.server.controllers;

import com.geekbrains.gwt.common.Task;
import com.geekbrains.gwt.common.TaskDTO;
import com.geekbrains.server.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class TaskController {
    private TaskService taskService;

    public Task parseDTO(TaskDTO taskDTO) {
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setCaption(taskDTO.getCaption());
        task.setOwner(taskDTO.getOwner());
        task.setAssigned(taskDTO.getAssigned());
        task.setStatus(taskDTO.getStatus());
        task.setDescription(taskDTO.getDescription());

        return task;
    }

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("/tasks")
    public List<TaskDTO> getAllTasks(@RequestParam Map<String, String> params) {
        return taskService.getTasks(params).stream()
                .map(task -> task.toDto())
                .collect(Collectors.toList());
    }

    @DeleteMapping("/tasks")
    public void deleteTask(@PathParam("id") Long id) {
        taskService.deleteTask(id);
    }


    @PostMapping("/tasks/put")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateTask(@ModelAttribute("task") TaskDTO taskDTO) {
        taskService.addEdtTasks(parseDTO(taskDTO));
    }

    @PostMapping("/tasks/post")
    public void addTask(@ModelAttribute("task") TaskDTO taskDTO) {
        taskService.addEdtTasks(parseDTO(taskDTO));
    }
}
