package org.example.eko.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.tomcat.jni.Local;
import org.example.eko.model.dtos.MedikamentEkoDTO;
import org.example.eko.model.entities.MedikamentVergleichsEntity;
import org.example.eko.service.SubstitutionService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Route(value = "", layout = MainLayout.class)
@PageTitle("EKO")
public class SubstituteListView extends VerticalLayout {
    Grid<MedikamentEkoDTO> grid = new Grid<>(MedikamentEkoDTO.class);
    TextField filterText = new TextField();
    DatePicker datePicker = new DatePicker();
    Long medEkoId;

    private final SubstitutionService substitutionService;

    public SubstituteListView(SubstitutionService substitutionService) {
        this.substitutionService = substitutionService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(), grid);
    }

    private void configureGrid() {
        grid.addClassNames("substitute-grid");
        grid.setSizeFull();
        grid.setColumns("positionPreisvergleich", "pharmaNummer", "name", "box", "kassenVerkaufspreis", "kvpProEinheit", "regeltext", "hinweis");
        //grid.addColumn(contact -> contact.getValidDate().getDate()).setHeader("Gültig ab");
        // grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Enter pharmanummer..");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        //filterText.addValueChangeListener(e -> updateList());

        Button searchButton = new Button("Search substitutes");
        searchButton.addClickListener(e -> updateList(filterText.getValue(), datePicker.getValue()));

        HorizontalLayout toolbar = new HorizontalLayout(filterText, datePicker, searchButton);
        //HorizontalLayout toolbar = new HorizontalLayout(filterText);

        toolbar.addClassName("toolbar");
        return toolbar;
    }


    private void updateList(String searchValue, LocalDate date) {
        var items = substitutionService.getSubstitutesOrdered(searchValue, date != null ? date : LocalDate.now());
        grid.setItems(items);
    }
}
