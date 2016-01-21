package com.bionicuniversity.edu.fashiontips.service.impl;

import com.bionicuniversity.edu.fashiontips.dao.RoleDao;
import com.bionicuniversity.edu.fashiontips.dao.UserDao;
import com.bionicuniversity.edu.fashiontips.entity.Country;
import com.bionicuniversity.edu.fashiontips.entity.Image;
import com.bionicuniversity.edu.fashiontips.entity.Role;
import com.bionicuniversity.edu.fashiontips.entity.User;
import com.bionicuniversity.edu.fashiontips.service.UserService;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

/**
 * User Service Implementation.
 *
 * @author Volodymyr Portianko
 * @author Maksym Dolia
 * @since 20/11/2015
 */
@Named
public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;

    @Inject
    private RoleDao roleDao;

    @Override
    @Transactional
    public User save(User user) {
        Objects.requireNonNull(user, "User cannot be null.");
        user.setId(null);   // workaround, because due to id is common
                            // for all entities, and it is impossible
                            // to manage serialization only for User class id;

        encodePassword(user);
        Role role = roleDao.find("ROLE_USER");
        user.setRoles(Collections.singletonList(role));
        return userDao.save(user);
    }

    @Override
    @PreAuthorize("#user.login == authentication.name")
    public void update(@P("user") User user) {
        Objects.requireNonNull(user, "User cannot be null");
        userDao.save(user);
    }

    @Override
    public void update(User user, User userData) {

        Objects.requireNonNull(user);
        Objects.requireNonNull(userData);

        String password = userData.getPassword();
        if (password != null) {
            user.setPassword(password);
            encodePassword(user);
        }
        Image avatar = userData.getAvatar();
        if (avatar != null) user.setAvatar(avatar);
        String firstName = userData.getFirstName();
        if (firstName != null) user.setFirstName(firstName);
        String lastName = userData.getLastName();
        if (lastName != null) user.setLastName(lastName);
        LocalDate birthday = userData.getBirthday();
        if (birthday != null) user.setBirthday(birthday);
        Boolean hideAge = userData.isHideAge();
        if (hideAge != null) user.setHideAge(hideAge);
        User.Gender gender = userData.getGender();
        if (gender != null) user.setGender(gender);
        String location = userData.getLocation();
        if (location != null) user.setLocation(location);
        Country country = userData.getCountry();
        if (country != null) user.setCountry(country);
        String occupation = userData.getOccupation();
        if (occupation != null) user.setOccupation(occupation);
        String aboutMe = userData.getAboutMe();
        if (aboutMe != null) user.setAboutMe(aboutMe);
        String instagram = userData.getInstagram();
        if (instagram != null) user.setInstagram(instagram);
        URL blogUrl = userData.getBlogUrl();
        if (blogUrl != null) user.setBlogUrl(blogUrl);
        URL websiteUrl = userData.getWebsiteUrl();
        if (websiteUrl != null) user.setWebsiteUrl(websiteUrl);

        update(user);
    }

    @Override
    public Optional<User> findOne(String login) {
            return Optional.ofNullable(userDao.getByLogin(login));
    }

    @Override
    public Optional<User> findOne(long id) {
        return Optional.ofNullable(userDao.getById(id));
    }

    @Override
    public boolean checkLogin(String login) {
        return userDao.getByLogin(login) == null;
    }

    @Override
    public boolean checkEmail(String email) {
        return userDao.findByEmail(email) == null;
    }

    private void encodePassword(User user) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
    }
}
