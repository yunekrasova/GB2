package com.geekbrains.gwt.client;

import com.geekbrains.gwt.common.TaskDTO;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/")
public interface  TaskClient extends RestService {
    @GET
    @Path("tasks")
    void getAllTasks(MethodCallback<List<TaskDTO>> items);

    @GET
    @Path("tasks?id={id}&caption={caption}&owner={owner}&assigned={assigned}&status={status}&descriprion={descriprion}")
    void getTasks(
            @PathParam("id") String id,
            @PathParam("caption") String caption,
            @PathParam("owner") String owner,
            @PathParam("assigned") String assigned,
            @PathParam("status") String status,
            @PathParam("descriprion") String descriprion,
            MethodCallback<List<TaskDTO>> items
    );

    @DELETE
    @Path("tasks?id={id}")
    void removeTask(@PathParam("id") String id, MethodCallback<Void> result);
}
