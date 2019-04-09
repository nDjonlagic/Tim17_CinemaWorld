package net.springboot.user_micro_service.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.springboot.user_micro_service.model.Role;
import net.springboot.user_micro_service.model.User;
import net.springboot.user_micro_service.repository.UserRepository;
import net.springboot.user_micro_service.web.dto.UserRegistrationDto;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findByEmail(String email){
    	System.out.println("servis " + email);
        return userRepository.findByEmail(email);
    }
    
    public User save(User user) {
    	return userRepository.save(user);
    }
    
    public User findById(long id) {
    	return userRepository.findById(id);
    }

    public User save(UserRegistrationDto registration){
        User user = new User();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    @Override
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);

    }
    
//    @Resource(name = "sessionFactory")
//    private SessionFactory  sessionFactory;
//
//    public void mergeWithExistingAndUpdate(final User personFromPost) {
//
//        Session session = sessionFactory.getCurrentSession();
//
//        User existingPerson = (User) session.get(User.class, personFromPost.getId());
//
//        // set here explicitly what must/can be overwritten by the html form POST
//        existingPerson.setFirstName(personFromPost.getFirstName());
//        existingPerson.setLastName(personFromPost.getLastName());
//        existingPerson.setEmail(personFromPost.getEmail());
////        existingPerson.setDateModified(new Date());
////        existingPerson.setUserModified(Utils.getCurrentUser());
//
//        session.update(existingPerson);
//    }
   

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
