package id.co.bsi.hello_spring.controller;

import id.co.bsi.hello_spring.dto.response.DashboardResponse;
import id.co.bsi.hello_spring.dto.response.DashboardResponse.DashboardData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class DashboardController {

    @GetMapping("/{accountnum}")
    public ResponseEntity<DashboardResponse> getProfile(
            @RequestHeader(value = "token") String token,
            @PathVariable String accountnum
    ) {
        DashboardResponse response = new DashboardResponse();

        // Token checking
        if (!token.equals("EWUd8X0vAJ/Ox1vx/SgfAg==")) {
            response.setStatus("fail");
            response.setMessage("Unauthorized access");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // Set response
        DashboardData data = new DashboardData();
        data.setAccountnum(accountnum);
        data.setFullname("Sandy Reynaldo");
        data.setBalance(10_000_000);

        response.setStatus("success");
        response.setMessage("Dashboard successfully displayed");
        response.setData(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
