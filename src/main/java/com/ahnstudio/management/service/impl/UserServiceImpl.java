package com.ahnstudio.management.service.impl;

import com.ahnstudio.management.common.Const;
import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.dao.UserMapper;
import com.ahnstudio.management.pojo.Product;
import com.ahnstudio.management.pojo.User;
import com.ahnstudio.management.service.UserService;
import com.ahnstudio.management.util.MD5Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        return userMapper.selectLogin(username, md5Password);
    }

    @Override
    public User loginCustomer(String username, String password) {
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        return userMapper.selectLoginCustomer(username, md5Password);
    }

    @Override
    public ServerResponse<User> infoByUserId(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return ServerResponse.createByErrorMessage("找不到用户");
        }
        return ServerResponse.createBySuccess(user);
    }

    @Override
    public ServerResponse checkAdminRole(User user) {
        if (user != null && user.getRole().equals(Const.Role.ROLE_ADMIN)) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse<User> getInformation(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    @Override
    public User findUserById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public ServerResponse<User> updateInformation(User user) {
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("email已存在,请更换email再尝试更新");
        }

        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setCompany(user.getCompany());
        updateUser.setCompanyType(user.getCompanyType());
        updateUser.setDeviceSerial(user.getDeviceSerial());
        updateUser.setDeviceModel(user.getDeviceModel());
        updateUser.setDeviceColor(user.getDeviceColor());
        updateUser.setRealname(user.getRealname());
        updateUser.setPhone(user.getPhone());
        updateUser.setEmail(user.getEmail());
        updateUser.setSex(user.getSex());
        updateUser.setBirthday(user.getBirthday());
        updateUser.setWechat(user.getWechat());
        updateUser.setQq(user.getQq());
        updateUser.setProvince(user.getProvince());
        updateUser.setCity(user.getCity());
        updateUser.setArea(user.getArea());
        updateUser.setAddress(user.getAddress());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        updateUser.setImagePhoto(user.getImagePhoto());
        updateUser.setParam1(user.getParam1());
        updateUser.setParam2(user.getParam2());
        updateUser.setParam3(user.getParam3());
        updateUser.setParam4(user.getParam4());
        updateUser.setParam5(user.getParam5());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount > 0) {
            return ServerResponse.createBySuccess("更新个人信息成功", updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }

    @Override
    public ServerResponse<String> addUser(User user) {
        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        user.setRoleNo(Const.RoleNo.ROLE_USER);
        user.setRole(Const.Role.ROLE_USER);
        user.setStatus(Const.Status.NOT_ACTIVE);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("新增用户失败");
        }
        return ServerResponse.createBySuccessMessage("新增用户成功");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(type)) {
            if (Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户名已存在，请使用其他用户名");
                } else {
                    return ServerResponse.createBySuccessMessage("用户名不存在，可以使用");
                }
            } else if (Const.EMAIL.equals(type)) {
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("Email已存在，请使用其他Email");
                } else {
                    return ServerResponse.createBySuccessMessage("可以使用");
                }
            } else {
                return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
            }

        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
    }

    @Override
    public ServerResponse<String> selectQuestion(String username) {
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }

        String question = userMapper.selectQuestionByUsername(username);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccess(question);
        }

        return ServerResponse.createBySuccessMessage("找回密码的问题是空的");
    }


//    @Override
//    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
//        if (org.apache.commons.lang3.StringUtils.isBlank(forgetToken)) {
//            return ServerResponse.createByErrorMessage("参数错误,token需要传递");
//        }
//        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
//        if (validResponse.isSuccess()) {
//            //用户不存在
//            return ServerResponse.createByErrorMessage("用户不存在");
//        }
//        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
//        if (org.apache.commons.lang3.StringUtils.isBlank(token)) {
//            return ServerResponse.createByErrorMessage("token无效或者过期");
//        }
//
//        if (org.apache.commons.lang3.StringUtils.equals(forgetToken, token)) {
//            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
//            int rowCount = userMapper.updatePasswordByUsername(username, md5Password);
//
//            if (rowCount > 0) {
//                return ServerResponse.createBySuccessMessage("修改密码成功");
//            }
//        } else {
//            return ServerResponse.createByErrorMessage("token错误,请重新获取重置密码的token");
//        }
//        return ServerResponse.createByErrorMessage("修改密码失败");
//    }
//
//    @Override
//    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
//        //防止横向越权,要校验一下这个用户的旧密码,一定要指定是这个用户.因为我们会查询一个count(1),如果不指定id,那么结果就是true啦count>0;
//        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
//        if (resultCount == 0) {
//            return ServerResponse.createByErrorMessage("旧密码错误");
//        }
//
//        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
//        int updateCount = userMapper.updateByPrimaryKeySelective(user);
//        if (updateCount > 0) {
//            return ServerResponse.createBySuccessMessage("密码更新成功");
//        }
//        return ServerResponse.createByErrorMessage("密码更新失败");
//    }

//    @Override
//    public ServerResponse updateUserAvatarImagePath(User user) {
//        int updateCount = userMapper.updateAvatarPath(user);
//        if (updateCount > 0) {
//            return ServerResponse.createBySuccessMessage(Const.Message.UPDATE_OK);
//        }
//        return ServerResponse.createByErrorMessage(Const.Message.UPDATE_ERROR);
//    }


    /*********************************************************************************************************
     * 												관리자 전용
     *********************************************************************************************************/

    @Override
    public ServerResponse<String> delete_user(User user) {
        int resultCount = userMapper.updateByPrimaryKeySelective(user);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("删除失败");
        }

        return ServerResponse.createBySuccessMessage("删除成功");
    }

    @Override
    public Map<String, Object> getUserList(int pageNum, int pageSize) {
        List<User> userList = userMapper.selectAllUser();
        Map<String, Object> result = new HashMap<>();
        result.put("total", userList.size());
        result.put("rows", userList);
        return result;
    }

    @Override
    public ServerResponse getUserListForAdmin(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList = userMapper.selectAllUser();
        PageInfo pageResult = new PageInfo(userList);
        pageResult.setList(userList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse updateStatusByUserId(User user) {
        if (user == null) {
            return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
        }

        int updateCount = 0;

        if (user.getStatus().equals(Const.Status.NOT_ACTIVE)) {
            updateCount = userMapper.updateActiveCustomer(user);
        } else {
            updateCount = userMapper.updateNotActiveCustomer(user);
        }

        if (updateCount > 0) {
            return ServerResponse.createBySuccessMessage(Const.Message.UPDATE_OK);
        }

        return ServerResponse.createBySuccessMessage(Const.Message.UPDATE_ERROR);
    }

}
