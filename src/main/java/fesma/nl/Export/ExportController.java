package fesma.nl.Export;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


public class ExportController {
    private ExportRepository repository;

    ExportController(ExportRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/exports")
    Export postExport(@RequestBody Export newExport) {
        return repository.save(newExport);
    }

}
