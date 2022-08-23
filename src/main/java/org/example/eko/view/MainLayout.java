package org.example.eko.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Elektronischer Erstattungskodex");
        logo.addClassNames("text-l", "m-m");
        Anchor sv = new Anchor("https://www.sozialversicherung.at/cdscontent/?contentid=10007.844481&portal=svportal", "SV");
        sv.setTarget("_blank");

        HorizontalLayout header = new HorizontalLayout(
                new DrawerToggle(),
                logo,
                sv
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("Vergleichsmedikamente", SubstituteListView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink uploadLink = new RouterLink("Upload", UploadView.class);
        uploadLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(
                listLink, uploadLink
        ));
    }
}
