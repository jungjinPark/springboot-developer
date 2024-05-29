package com.example.springbootdeveloper.quiz;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuizController {

    @GetMapping("/quiz")
    public ResponseEntity<String> getQuiz(@RequestParam("code") int code) {
        switch (code) {
            case 1:
                return ResponseEntity.created(null).body("Created!");
            case 2:
                return ResponseEntity.badRequest().body("Bad Request!");
            default:
                return ResponseEntity.ok("OK!");
        }
    }

    @PostMapping("/quiz")
    public ResponseEntity<String> postQuiz(@RequestBody Code code) {
        switch (code.value) {
            case 1:
                return ResponseEntity.status(403).body("Forbidden!");
            default:
                return ResponseEntity.ok("OK!");
        }
    }

    record Code(int value) {}
}
