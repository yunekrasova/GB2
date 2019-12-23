package com.geekbrains.gwt.client;

import com.geekbrains.gwt.common.Task;
import com.geekbrains.gwt.common.TaskDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterTaskFormWidget  extends Composite {

    @UiField
    FormPanel form;

    @UiField
    TextBox idText;

    @UiField
    TextBox captionText;

    @UiField
    TextBox ownerList ;

    @UiField
    TextBox assignedList;

    @UiField(provided = true)
    ValueListBox<String> statusList = new ValueListBox<String>(new Renderer<String>() {
        @Override
        public String render(String object) {
            return object;
        }

        @Override
        public void render(String object, Appendable appendable)
                throws IOException {
            if (object != null) {
                render(object);
            }
        }
    });

    @UiField
    TextBox descriprionText;

    private TaskTableWidget taskTableWidget;

    @UiTemplate("FilterTaskForm.ui.xml")
    interface AddItemFormBinder extends UiBinder<Widget, FilterTaskFormWidget> {
    }

    private ViewTaskPanelWidget viewTaskPanelWidget;

    private static FilterTaskFormWidget.AddItemFormBinder uiBinder = GWT.create(FilterTaskFormWidget.AddItemFormBinder.class);

    public FilterTaskFormWidget(TaskTableWidget itemsTableWidget) {
        this.initWidget(uiBinder.createAndBindUi(this));
        this.form.setAction(Defaults.getServiceRoot().concat("tasks"));

        List<String> statusVals = Stream.of(Task.Status.values())
                .map(Task.Status::name)
                .collect(Collectors.toList());
        this.statusList.setValue("");
        this.statusList.setAcceptableValues(statusVals);

    }

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        taskTableWidget.update(
                this.idText.getValue(),
                this.captionText.getValue(),
                this.ownerList.getValue(),
                this.assignedList.getValue(),
                this.statusList.getValue(),
                this.descriprionText.getValue()
        );
    }

    @UiHandler("btnClear")
    public void clearClick(ClickEvent event) {
        this.idText.setValue("");
        this.captionText.setValue("");
        this.ownerList.setValue("");
        this.assignedList.setValue("");
        this.statusList.setValue("");
        this.descriprionText.setValue("");
    }

    @UiHandler("btnAdd")
    public void addClick(ClickEvent event) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setAssigned("");
        taskDTO.setOwner("");
        viewTaskPanelWidget = new ViewTaskPanelWidget(taskDTO, false, taskTableWidget);
        viewTaskPanelWidget.show();
    }
}
