package org.example.eko.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.example.eko.service.DataService;
import org.example.eko.service.DatasetFetchingScheduler;
import org.example.eko.service.ImportService;
import org.example.eko.service.ScanningService;

import javax.swing.plaf.basic.BasicButtonUI;
import javax.xml.crypto.Data;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.zip.ZipInputStream;

@Route(value = "/upload", layout = MainLayout.class)
@PageTitle("EKO")
public class UploadView extends VerticalLayout {
    MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    Upload upload = new Upload(buffer);
    DatePicker datePicker = new DatePicker("Gültig ab: ");
    Button uploadButton = new Button("Upload");
    InputStream i1;
    InputStream i2;
    ScanningService scanningService;
    DataService dataService;
    ImportService importService;


    public UploadView(ScanningService scanningService,
                      DataService dataService,
                      ImportService importService){
        this.scanningService = scanningService;
        this.dataService = dataService;
        this.importService = importService;

        addClassName("upload-view");
        setSizeFull();

        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            if(fileName.contains("teil1"))
                i1 = buffer.getInputStream(fileName);
            else
                i2 = buffer.getInputStream(fileName);
        });

        uploadButton.addClickListener(e -> {
           uploadFiles();
        });

        add(upload);
        add(datePicker);
        add(uploadButton);
    }

    public void uploadFiles(){
        if(i1 == null || i2 == null) return;

        var map = dataService.getFileStringsFromZipIn(new ZipInputStream(i1));
        map.putAll(dataService.getFileStringsFromZipIn(new ZipInputStream(i2)));
        importService.importDataSet(scanningService.scanFileStrings(map),
                datePicker.getValue() != null ? datePicker.getValue() : LocalDate.now());

        i1 = null;
        i2 = null;
        upload.clearFileList();
    }
}
