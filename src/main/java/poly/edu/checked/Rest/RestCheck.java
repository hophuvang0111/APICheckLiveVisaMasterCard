package poly.edu.checked.Rest;


import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poly.edu.checked.SeleniumService;

@CrossOrigin("*")
@RestController
public class RestCheck {

    @Autowired
    private SeleniumService seleniumService;
    
@GetMapping("/api/check")
public ResponseEntity<?> check(@RequestParam String numberCard, @RequestParam String month, @RequestParam String year, @RequestParam String ccv) {
    CompletableFuture<Boolean> checkFuture = seleniumService.executeSeleniumTask(numberCard, month, year, ccv);
    boolean check;
    try {
        check = checkFuture.get();
    } catch (Exception e) {
        // Xử lý lỗi nếu cần
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
    }

    if (!check) {
        return ResponseEntity.badRequest().build();
    } else {
        return ResponseEntity.ok().build();
    }
}



    


}
