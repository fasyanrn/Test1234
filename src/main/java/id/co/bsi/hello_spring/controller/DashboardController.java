package id.co.bsi.hello_spring.controller;

import id.co.bsi.hello_spring.dto.response.DashboardResponse;
import id.co.bsi.hello_spring.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // Hanya akun sendiri yang bisa akses dashboard
    @GetMapping("/me")
    public ResponseEntity<DashboardResponse> getMyDashboard(
            @RequestHeader("token") String token
    ) {
        DashboardResponse response = dashboardService.getDashboardByToken(token);

        if ("fail".equals(response.getStatus())) {
            if ("Unauthorized access".equals(response.getMessage())) {
                return ResponseEntity.status(401).body(response); // 401 Unauthorized
            } else {
                return ResponseEntity.badRequest().body(response); // 400 Bad Request default
            }
        }

        return ResponseEntity.ok(response); // 200 OK kalau sukses
    }

    // Endpoint untuk get semua user dengan format "[AccountNum] - [Nama]"
    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllUsersFormatted() {
        List<String> users = dashboardService.getAllUsersFormatted();
        return ResponseEntity.ok(users);
    }

    // Tetap disediakan jika ingin akses dengan accountnum tertentu (opsional)
    @GetMapping("/{accountnum}")
    public ResponseEntity<DashboardResponse> getDashboard(
            @RequestHeader("token") String token,
            @PathVariable String accountnum
    ) {
        DashboardResponse response = dashboardService.getDashboard(token, accountnum);

        if ("fail".equals(response.getStatus())) {
            if ("Unauthorized access".equals(response.getMessage())) {
                return ResponseEntity.status(401).body(response);
            } else if ("Account number mismatch".equals(response.getMessage())) {
                return ResponseEntity.status(403).body(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        }

        return ResponseEntity.ok(response);
    }
}
