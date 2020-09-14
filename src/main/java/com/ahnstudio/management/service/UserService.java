package com.ahnstudio.management.service;
import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.pojo.User;

import java.util.Map;

public interface UserService {

    User login(String username, String password);

//    ServerResponse<User> info(String token);

    ServerResponse<User> infoByUserId(Integer userId);

    ServerResponse checkAdminRole(User user);

    ServerResponse<User> getInformation(Integer userId);

    User findUserById(Integer userId);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<String> addUser(User user);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse<String> selectQuestion(String username);

    ServerResponse<String> delete_user(User user);

    Map<String, Object> getUserList(int pageNum, int pageSize);

    ServerResponse getUserListForAdmin(int pageNum, int pageSize);



}
