package test;

import com.jk204.dao.UserDao;
import com.jk204.domain.User;
import com.jk204.pojo.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDaoTest {
    private UserDao userDao;

    @BeforeEach
    public void init() {
        userDao = new UserDao();
    }

    @Test
        //已写
    void addUser() {
        boolean Result = userDao.addUser(0, 273072269604032512L, "testuser", "123456", "7908d4096b612d9deb789f9ede7ae477", "1");
        assertEquals(true, Result);
    }

    @Test
        //已写
    void queryUserById() {
        User user = userDao.queryUserById(4);
        assertEquals("user2", user.getUsername());
        System.out.println(user.getUsername());
    }

    @Test
        //已写
    void queryUserProfileById() {
        UserProfile userProfile = userDao.queryUserProfileById(4);
        assertEquals("user2", userProfile.getNickname());
        System.out.println(userProfile.getNickname());
    }

    @Test
        //已写
    void getUserByUsername() {
        User user = userDao.getUserByUsername("user2");
        assertEquals("user2", user.getNickname());
        System.out.println(user.getNickname());
    }

    @Test
        //已写
    void queryUserIdByUid() {
        String id = String.valueOf(userDao.queryUserIdByUid(String.valueOf(273073446781587457L)));
        assertEquals("4", id);
        System.out.println(id);
    }

    @Test
    void queryUserProfilesByUidLikeSearch() {
        List<UserProfile> queryContests = userDao.queryUserProfilesByUidLikeSearch(String.valueOf(273073051820756992L), 1, 4);
        assertEquals("user1", queryContests.get(0).getNickname());
    }
}