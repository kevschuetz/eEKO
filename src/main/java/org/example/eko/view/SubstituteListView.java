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
import org.example.eko.model.entities.EkoEintrag;
import org.example.eko.model.entities.Medikament;
import org.example.eko.model.entities.MedikamentVergleichsEntity;
import org.example.eko.model.repositories.EkoEintragRepository;
import org.example.eko.model.repositories.MedikamentRepository;
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
    EkoEintrag currentEntry;

    private final SubstitutionService substitutionService;
    private final EkoEintragRepository ekoEintragRepository;

    public SubstituteListView(SubstitutionService substitutionService, EkoEintragRepository ekoEintragRepository) {
        this.substitutionService = substitutionService;
        this.ekoEintragRepository = ekoEintragRepository;

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
        grid.addColumn(medDto ->
            currentEntry != null ?
                    currentEntry.getMedikamentVergleichsEntityList()
                            .stream()
                            .filter(v -> v.getVergleichsMedikament().getPharmaNummer().equals(medDto.getPharmaNummer()))
                            .map(MedikamentVergleichsEntity::getVergleichsKennzeichen)
                            .findFirst()
                            .orElse(null)
                    : null
        ).setHeader("Vergleichskennzeichen");
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
        items.stream().filter(m -> m.getPharmaNummer().equals(searchValue)).findFirst().ifPresent(m -> {
            if(m.getId() != null)
                currentEntry = ekoEintragRepository.findById(m.getId()).orElse(null);
        });
        grid.setItems(items);
    }
}
