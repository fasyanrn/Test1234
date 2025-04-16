//package id.co.bsi.hello_spring.controller;
//
//import id.co.bsi.hello_spring.model.User;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.http.ResponseEntity;
//
//@RestController
//public class HelloController {
//
//
//   @GetMapping("/hello")
//   public String hello(){
//       return "hello world";
//   }
//
//   @GetMapping("/users")
//   public ResponseEntity<User> getUser() {
//       User user = new User();
//       user.setId(1);
//       user.setUsername("admin");
//       user.setEmail("admin@gmail.com");
//
//       return ResponseEntity.ok(user);
//   }
//   @PostMapping("/users")
//   public ResponseEntity<User> createUser(@RequestBody User userRequestBody){
//
//       return ResponseEntity.ok(userRequestBody);
//   }
//
//}
