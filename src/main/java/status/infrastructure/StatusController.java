package status.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import status.application.service.StatusService;
import status.domain.model.Status;



@RestController
@RequestMapping("/status")
public class StatusController {


    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }


    @GetMapping("/{feedbackID}")
    public ResponseEntity<Status> getStatus(@PathVariable String feedbackID) {
        Status status = statusService.getStatus(feedbackID);
        if (status != null) { return ResponseEntity.ok(status);
        }
        else {
            return ResponseEntity.notFound().build(); }
    }
    @PutMapping("/{feedbackID}")
    public ResponseEntity<Void> updateStatus(@PathVariable String feedbackID, @RequestBody Status newStatus) {
        // Hier wird der Status im StatusService aktualisiert
        statusService.setStatus(feedbackID, newStatus);
        return ResponseEntity.noContent().build();
    }
}