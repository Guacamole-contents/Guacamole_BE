package project.guakamole.domain.user.service;

import com.amazonaws.services.kms.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.guakamole.domain.user.entity.User;
import project.guakamole.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void updateStatusWithCreator(Long userId){
        User user = findById(userId);

        user.updateStatusWithCreator();
    }

    public void updateStatusWithNone(Long userId){
        User user = findById(userId);

        user.updateStatusWithNone();
    }
    public User findById(Long userId){
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("not found user")
        );
    }
    public String getUserRole(Long userId){
        return findById(userId).getUserRole().toString();
    }
}
