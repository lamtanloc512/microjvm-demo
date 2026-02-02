package org.ltl;

import org.microjvm.di.annotation.Service;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import java.util.List;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Inject
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostConstruct
  public void init() {
    System.out.println("UserService initialized");
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User save(User user) {
    return userRepository.save(user);
  }
}