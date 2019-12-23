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
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Defaults;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ViewTaskPanelWidget extends Composite {
    @UiField
    FormPanel form;

    @UiField
    TextBox idText;

    @UiField
    Label idLabel;

    @UiField
    TextBox captionText;

    @UiField
    TextBox ownerList ;

    @UiField
    TextBox assignedList ;

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
    TextBox descriptionText;

    @UiField
    Button saveButton;

    @UiTemplate("ViewTaskPanelWidget.ui.xml")
    interface ViewTaskPanelBinder extends UiBinder<Widget, ViewTaskPanelWidget> {
    }

    PopupPanel popup = new PopupPanel(true);

    private TaskTableWidget taskTableWidget;

    private static ViewTaskPanelWidget.ViewTaskPanelBinder uiBinder = GWT.create(ViewTaskPanelWidget.ViewTaskPanelBinder.class);

    public ViewTaskPanelWidget(TaskDTO task, boolean readOnly, TaskTableWidget taskTableWidget) {
        this.taskTableWidget = taskTableWidget;
        this.initWidget(uiBinder.createAndBindUi(this));

        this.idText.setReadOnly(true);

        if (readOnly) {
            this.captionText.setReadOnly(true);
            this.ownerList.setEnabled(false);
            this.assignedList.setEnabled(false);
            this.statusList.setEnabled(false);
            this.descriptionText.setReadOnly(true);
            this.saveButton.setVisible(false);
        }

        if (task.getId() != null) {

            this.idText.setValue(task.getId().toString());
            this.captionText.setValue(task.getCaption());
            this.statusList.setValue(task.getStatus().toString());
            this.descriptionText.setValue(task.getDescription());
            this.form.setAction(Defaults.getServiceRoot().concat("tasks").concat("/put"));
            this.form.setMethod("POST");
        } else {
            this.idLabel.setVisible(false);
            this.idText.setVisible(false);
            this.form.setAction(Defaults.getServiceRoot().concat("tasks").concat("/post"));
            this.form.setMethod("POST");
        }

        List<String> statusVals = Stream.of(Task.Status.values())
                .map(Task.Status::name)
                .collect(Collectors.toList());
        this.statusList.setValue("");
        this.statusList.setAcceptableValues(statusVals);

    }

    public void show () {
        popup.clear();
        popup.add(this);
        popup.show();
        popup.center();
    }

    public void hide () {
        popup.hide();
    }

    @UiHandler("closeButton")
    public void closeButtonClick(ClickEvent event) {
        this.hide();
    }

    @UiHandler("saveButton")
    public void saveButtonClick(ClickEvent event) {
        this.ownerList.setName("owner");
        this.assignedList.setName("assigned");
        this.form.submit();
    }

    @UiHandler("form")
    public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
        taskTableWidget.refresh();
        this.hide();
    }
}