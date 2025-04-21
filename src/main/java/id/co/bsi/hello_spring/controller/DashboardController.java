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
        return ResponseEntity.ok(dashboardService.getDashboard(token, accountnum));
    }
}
