package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired

    private AccountService accountService;

    @Autowired

    private MessageService messageService;

    @PostMapping("/register")

    public ResponseEntity<?> register(@RequestBody Account account) {
        return accountService.register(account)
                .map(ResponseEntity::ok)
                .orElseGet(() -> accountService.login(account.getUsername(), account.getPassword()).isPresent()
                ? ResponseEntity.status(409).build()
                : ResponseEntity.badRequest().build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        return accountService.login(account.getUsername(), account.getPassword())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message){
        return messageService.createMessage(message)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId){
        return messageService.getMessageById(messageId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok().build());


    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Integer messageId){
        if (messageService.deleteMessage(messageId)) {
            return ResponseEntity.ok(1);
            
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable Integer messageId, @RequestBody Message message){
        if (messageService.updateMessage(messageId, message.getMessageText())) {
            return ResponseEntity.ok(1);
            
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessageByUser(@PathVariable Integer accountId){
        return ResponseEntity.ok(messageService.getMessageByUser(accountId));
    }

}
