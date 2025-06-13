package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Optional<Message> createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().isBlank() ||
            message.getMessageText().length() > 255 || 
            !accountRepository.existsById(message.getPostedBy())) {
            return Optional.empty();
            
        }
        return Optional.of(messageRepository.save(message));
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Integer id) {
        return messageRepository.findById(id);
    }

    public boolean deleteMessage(Integer id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return true;
            
        }
        return false;
    }

    public boolean updateMessage(Integer id, String newText) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if (messageOptional.isPresent() && newText != null && !newText.isBlank() && newText.length() <= 255) {
            Message msg = messageOptional.get();
            msg.setMessageText(newText);
            messageRepository.save(msg);
            return true;
            
        }
        return false;
    }

    public List<Message> getMessageByUser(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
