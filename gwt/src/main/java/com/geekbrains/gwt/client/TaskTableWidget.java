package com.geekbrains.gwt.client;

import com.geekbrains.gwt.common.Task;
import com.geekbrains.gwt.common.TaskDTO;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class TaskTableWidget extends Composite {
    @UiField
    CellTable<TaskDTO> table;

    private TaskClient taskClient;

    @UiTemplate("TaskTable.ui.xml")
    interface ItemsTableBinder extends UiBinder<Widget, TaskTableWidget> {
    }

    private static ItemsTableBinder uiBinder = GWT.create(ItemsTableBinder.class);

    private ViewTaskPanelWidget viewTaskPanelWidget;

    private TaskTableWidget taskTableWidget;

    public TaskTableWidget() {
        taskTableWidget = this;
        initWidget(uiBinder.createAndBindUi(this));

        TextColumn<TaskDTO> idColumn = new TextColumn<TaskDTO>() {
            @Override
            public String getValue(TaskDTO taskDto) {
                return taskDto.getId().toString();
            }
        };
        table.addColumn(idColumn, "ID");

        TextColumn<TaskDTO> captionColumn = new TextColumn<TaskDTO>() {
            @Override
            public String getValue(TaskDTO taskDto) {
                return taskDto.getCaption();
            }
        };
        table.addColumn(captionColumn, "Caption");


        TextColumn<TaskDTO> ownerColumn = new TextColumn<TaskDTO>() {
            @Override
            public String getValue(TaskDTO taskDTO) {
                return     taskDTO.getOwner() ;
            }
        };
        table.addColumn(ownerColumn, "Owner");

        TextColumn<TaskDTO> statusColumn = new TextColumn<TaskDTO>() {
            @Override
            public String getValue(TaskDTO taskDTO) {
                Task.Status status = taskDTO.getStatus();
                return status != null ? status.getRussianTitle() : null;
            }
        };
        table.addColumn(statusColumn, "Status");

        TextColumn<TaskDTO> assignedColumn = new TextColumn<TaskDTO>() {
            @Override
            public String getValue(TaskDTO taskDTO) {
                return     taskDTO.getAssigned() ;
            }
        };
        table.addColumn(assignedColumn, "Assigned");

        TextColumn<TaskDTO> descriprionColumn = new TextColumn<TaskDTO>() {
            @Override
            public String getValue(TaskDTO taskDTO) {
                return taskDTO.getDescription();
            }
        };
        table.addColumn(descriprionColumn, "Descriprion");

        taskClient = GWT.create(TaskClient.class);

        Column<TaskDTO, TaskDTO> actionColumn = new Column<TaskDTO, TaskDTO>(
                new ActionCell<TaskDTO>("REMOVE", new ActionCell.Delegate<TaskDTO>() {
                    @Override
                    public void execute(TaskDTO item) {
                        taskClient.removeTask(item.getId().toString(), new MethodCallback<Void>() {
                            @Override
                            public void onFailure(Method method, Throwable throwable) {
                                GWT.log(throwable.toString());
                                GWT.log(throwable.getMessage());
                            }

                            @Override
                            public void onSuccess(Method method, Void result) {
                                refresh();
                            }
                        });
                    }
                })) {
            @Override
            public TaskDTO getValue(TaskDTO item) {
                return item;
            }
        };

        Column<TaskDTO, TaskDTO> viewColumn = new Column<TaskDTO, TaskDTO>(
                new ActionCell<TaskDTO>("VIEW", new ActionCell.Delegate<TaskDTO>() {
                    @Override
                    public void execute(TaskDTO item) {
                        viewTaskPanelWidget = new ViewTaskPanelWidget(item, true, taskTableWidget);
                        viewTaskPanelWidget.show();
                    }
                })) {
            @Override
            public TaskDTO getValue(TaskDTO item) {
                return item;
            }
        };

        Column<TaskDTO, TaskDTO> editColumn = new Column<TaskDTO, TaskDTO>(
                new ActionCell<TaskDTO>("EDIT", new ActionCell.Delegate<TaskDTO>() {
                    @Override
                    public void execute(TaskDTO item) {
                        viewTaskPanelWidget = new ViewTaskPanelWidget(item, false, taskTableWidget);
                        viewTaskPanelWidget.show();
                    }
                })) {
            @Override
            public TaskDTO getValue(TaskDTO item) {
                return item;
            }
        };

        List<HasCell<TaskDTO, ?>> cells = new LinkedList<HasCell<TaskDTO, ?>>();
        cells.add(actionColumn);
        cells.add(viewColumn);
        cells.add(editColumn);
        CompositeCell<TaskDTO> cell = new CompositeCell<TaskDTO>(cells);
        table.addColumn(new Column<TaskDTO, TaskDTO>(cell) {
            @Override
            public TaskDTO getValue(TaskDTO object) {
                return object;
            }
        }, "Actions");

        table.setColumnWidth(idColumn, 50, Style.Unit.PX);
        table.setColumnWidth(captionColumn, 200, Style.Unit.PX);
        table.setColumnWidth(ownerColumn, 100, Style.Unit.PX);
        table.setColumnWidth(statusColumn, 100, Style.Unit.PX);
        table.setColumnWidth(assignedColumn, 100, Style.Unit.PX);
        table.setColumnWidth(descriprionColumn, 400, Style.Unit.PX);
        table.setColumnWidth(actionColumn, 50, Style.Unit.PX);

        refresh();
    }

    public void refresh() {
        taskClient.getAllTasks(new MethodCallback<List<TaskDTO>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log(throwable.toString());
                GWT.log(throwable.getMessage());
                Window.alert("Невозможно получить список items: Сервер не отвечает");
            }

            @Override
            public void onSuccess(Method method, List<TaskDTO> i) {
                GWT.log("Received " + i.size() + " items");
                List<TaskDTO> items = new ArrayList<>();
                items.addAll(i);
                table.setRowData( items);
            }
        });
    }

    public void update(String id, String caption, String owner, String assigned, String status, String descriprion) {
        taskClient.getTasks(id, caption, owner, assigned, status, descriprion, new MethodCallback<List<TaskDTO>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log(throwable.toString());
                GWT.log(throwable.getMessage());
                Window.alert("Невозможно получить список: Сервер не отвечает");
            }

            @Override
            public void onSuccess(Method method, List<TaskDTO> i) {
                GWT.log("Received " + i.size() + " items");
                List<TaskDTO> items = new ArrayList<>();
                items.addAll(i);
                table.setRowData( items);
            }
        });
    }
}
