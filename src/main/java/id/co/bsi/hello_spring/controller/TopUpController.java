package id.co.bsi.hello_spring.controller;


import id.co.bsi.hello_spring.dto.request.TopUpRequest;

import id.co.bsi.hello_spring.dto.response.OptionFromResponse;
import id.co.bsi.hello_spring.dto.response.TopUpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TopUpController {
    @PostMapping("/api/topup")
    public ResponseEntity<TopUpResponse> topup (@RequestBody TopUpRequest topUpRequest){
        TopUpResponse topUpResponse = new TopUpResponse();
        topUpResponse.setStatus("Success");
        topUpResponse.setMessage("Top Up Success");

        return ResponseEntity.ok(topUpResponse);
    }

    @GetMapping("/api/topupoption")
    public ResponseEntity<List<OptionFromResponse>> topuption() {
//        OptionFromResponse optionFromResponse = new OptionFromResponse();
//        optionFromResponse.
        List<OptionFromResponse> topUpOption = new ArrayList<>();

        OptionFromResponse optionFromResponse = new OptionFromResponse();
        optionFromResponse.setOption("VA");

        OptionFromResponse optionFromResponse2 = new OptionFromResponse();
        optionFromResponse2.setOption("CC");

        topUpOption.add(optionFromResponse);
        topUpOption.add(optionFromResponse2);

        return ResponseEntity.ok(topUpOption);
    }

}

