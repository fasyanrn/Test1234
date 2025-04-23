package id.co.bsi.hello_spring.controller;

import id.co.bsi.hello_spring.dto.response.DashboardResponse;
import id.co.bsi.hello_spring.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/{accountnum}")
    public ResponseEntity<DashboardResponse> getDashboard(
            @RequestHeader("token") String token,
            @PathVariable String accountnum
    ) {
        DashboardResponse response = dashboardService.getDashboard(token, accountnum);

        if ("fail".equals(response.getStatus())) {
            // Bedakan error: unauthorized vs account mismatch
            if ("Unauthorized access".equals(response.getMessage())) {
                return ResponseEntity.status(401).body(response); // 401 Unauthorized
            } else if ("Account number mismatch".equals(response.getMessage())) {
                return ResponseEntity.status(403).body(response); // 403 Forbidden
            } else {
                return ResponseEntity.badRequest().body(response); // 400 Bad Request default
            }
        }

        return ResponseEntity.ok(response); // 200 OK kalau sukses
    }

}
