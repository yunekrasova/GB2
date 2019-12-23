package com.geekbrains.server.services;

import com.geekbrains.gwt.common.Task;
import com.geekbrains.server.repositories.TaskRepository;
import com.geekbrains.server.repositories.TaskSpecifications;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service(value = "taskService")
@NoArgsConstructor
public class TaskService {
    private TaskRepository storage;

    @Autowired
    public void setStorage(TaskRepository storage) {
        this.storage = storage;
    }


    public List<Task> getTasks(Map<String, String> params) {
        Specification<Task> spec = Specification.where(null);
        if (params.get("caption") != null && params.get("caption").length() != 0) {
            spec = spec.and(TaskSpecifications.captionContains(params.get("caption")));
        }
        if (params.get("status") != null && params.get("status").length() != 0) {
            spec = spec.and(TaskSpecifications.statusEq(Task.Status.valueOf(params.get("status"))));
        }
        if (params.get("assigned")  != null && params.get("assigned").length() != 0) {
            spec = spec.and(TaskSpecifications.assignedContains(params.get("assigned")));
        }
        if (params.get("id")  != null && params.get("id").length() != 0) {
            spec = spec.and(TaskSpecifications.idEq(Long.parseLong(params.get("id"))));
        }
        if (params.get("owner") != null && params.get("owner").length() != 0) {
            spec = spec.and(TaskSpecifications.ownerContains(params.get("owner")));
        }
        if (params.get("description") != null && params.get("description").length() != 0) {
            spec = spec.and(TaskSpecifications.descriptionContains(params.get("owner")));
        }
        return storage.findAll(spec, Sort.by(Sort.Direction.ASC, "id"));
    }

    public List<Task> getTasks() {
        return getTasks(null);
    }

    public Task addEdtTasks(Task task) {
        if (task.getStatus() == null) {
            task.setStatus(Task.Status.CREATED);
        }
        return storage.save(task);
    }

    public void deleteTask(Long id) {
        storage.deleteById(id);
    }

    public Task getTaskById(Long id){
        return storage.findById(id).get();
    }

    public boolean isTaskExists(Long id) {
        return storage.existsById(id);
    }

    public Map<Task.Status, Long> countOfAllStatus() {
        return storage.findAll().stream()
                .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));
    }

    public long countOfStatus(Task.Status status) {
        return countOfAllStatus().get(status);
    }
}
