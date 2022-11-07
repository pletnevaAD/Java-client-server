//package com.pletneva.vaadin.coursework_java.view;
//
//import com.pletneva.vaadin.coursework_java.editor.GroupEditor;
//import com.pletneva.vaadin.coursework_java.service.GroupService;
//import com.vaadin.flow.component.ClickEvent;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.html.Label;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.router.Route;
//import com.vaadin.icons.VaadinIcons;
//import com.vaadin.server.BrowserWindowOpener;
//import com.vaadin.server.EventTrigger;
//import com.vaadin.server.ExternalResource;
//import com.vaadin.server.Page;
//import com.vaadin.ui.Layout;
//import com.vaadin.ui.Link;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@Route("/client")
//public class MainView extends VerticalLayout {
//    private final Link link = new Link("Groups", new ExternalResource(
//            "http://localhost:8080/client/group"));
//    private final Label label = new Label("Welcome to the main page. Choose correct option.");
//    @Autowired
//    public MainView() {
//
//        add(label, link);
////        viewBtn.addClickListener(ev -> {
////            if (ev.isCtrlKey()) {
////                Page.getCurrent().open("https://vk.com/feed", "_blank", false);
////            } else {
////                Page.getCurrent().setLocation("https://vk.com/feed");
////            }
////        });
//    }
//
//}
