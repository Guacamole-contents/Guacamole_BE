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

    public String getUserRole(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("not found user")
        );

        return user.getUserRole().toString();
    }
}
