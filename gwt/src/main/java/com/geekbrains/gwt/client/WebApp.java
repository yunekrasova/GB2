package com.geekbrains.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.*;

public class WebApp implements EntryPoint {
    public void onModuleLoad() {
        Defaults.setServiceRoot("http://localhost:8189/gwt-rest");
        TaskTableWidget taskTableWidget = new TaskTableWidget();
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(new FilterTaskFormWidget(taskTableWidget));
        verticalPanel.add(taskTableWidget);
        RootPanel.get().add(verticalPanel);
    }
}