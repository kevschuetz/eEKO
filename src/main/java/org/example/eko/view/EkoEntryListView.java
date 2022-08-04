package org.example.eko.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.example.eko.model.entities.Medikament;
import org.example.eko.model.repositories.MedikamentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Route(value = "", layout = MainLayout.class)
@PageTitle("EKO")
public class EkoEntryListView extends VerticalLayout {
    Grid<Medikament> grid = new Grid<>(Medikament.class);
    TextField filterText = new TextField();

    private Collection<Medikament> listItems;
    private final MedikamentRepository medikamentRepository;

    public EkoEntryListView(MedikamentRepository medikamentRepository) {
        this.medikamentRepository = medikamentRepository;

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(), grid);


        loadListItems();
        updateList();
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("pharmaNummer", "name", "box", "kassenVerkaufsPreis", "regeltext", "hinweis");
        grid.addColumn(contact -> contact.getValidDate().getDate()).setHeader("Gültig ab");
        // grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name/pharmaNr...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button reloadDataButton = new Button("Reload Data");
        reloadDataButton.addClickListener(e -> loadListItems());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, reloadDataButton);
        //HorizontalLayout toolbar = new HorizontalLayout(filterText);

        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void loadListItems() {
        this.listItems = medikamentRepository.getValidMedikamentsByDate(LocalDate.now().toString());
        if(listItems != null && !listItems.isEmpty()) updateList();
    }

    private void updateList(){
        if(listItems == null || listItems.isEmpty()) loadListItems();
        grid.setItems(listItems.stream().filter(m -> m.getName().contains(filterText.getValue()) || m.getPharmaNummer().contains(filterText.getValue())).collect(Collectors.toList()));
    }

}
