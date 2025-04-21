
package id.co.bsi.hello_spring.controller;

import id.co.bsi.hello_spring.service.PinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pin")
public class PinController {

    @Autowired
    private PinService pinService;

    @PostMapping
    public ResponseEntity<?> registerPin(@RequestBody Map<String, String> payload) {
        String accountnum = payload.get("accountnum");
        String pin = payload.get("pin");

        if (accountnum == null || pin == null) {
            return ResponseEntity.badRequest().body("accountnum and pin must be provided.");
        }

        String result = pinService.registerPin(accountnum, pin);
        return ResponseEntity.ok(Map.of("message", result));
    }
}
