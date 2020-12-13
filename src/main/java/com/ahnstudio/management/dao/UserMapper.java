package com.ahnstudio.management.dao;

import com.ahnstudio.management.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("userMapper")
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(User record);
    int insertSelective(User record);
    User selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(User record);
    int updateByPrimaryKey(User record);
    int checkUsername(String username);
    User selectLogin(@Param("username") String username, @Param("password")String password);
    User selectLoginCustomer(@Param("username") String username, @Param("password")String password);
    int checkAdminUsername(@Param("username") String username, @Param("role") Integer role);
    int checkEmail(String email);
    int checkEmailByUserId(@Param(value="email") String email, @Param(value="userId") Integer userId);
    String selectQuestionByUsername(String username);
    int checkAnswer(@Param("username") String username, @Param("question") String question,@Param("answer") String answer);
    int updatePasswordByUsername(@Param("username") String username, @Param("passwordNew") String passwordNew);
    int checkPassword(@Param(value="password") String password, @Param("userId") Integer userId);
    List<User> selectAllUser();
    List<User> getUserListOnlyIDAndRealname();
    int updateAvatarPath(User user);
    int updateActiveCustomer(User user);
    int updateNotActiveCustomer(User user);

}