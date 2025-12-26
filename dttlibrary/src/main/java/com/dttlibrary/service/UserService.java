package com.dttlibrary.service;

import com.dttlibrary.model.User;
import com.dttlibrary.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
    
    public User update(User user) {
        return userRepository.save(user);
    }

    public void changePassword(String username, String currentPassword, String newPassword) {
        User user = findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found.");
        }
        // Vì không mã hóa, ta so sánh trực tiếp
        if (!user.getPassword().equals(currentPassword)) {
            throw new RuntimeException("Mật khẩu hiện tại không đúng.");
        }
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
