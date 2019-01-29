package com.lyj.dao;

import com.lyj.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Administrator on 2019/1/29.
 */

@Mapper
public interface UserDao {


    @Select("select * from user where id=#{id}")
    User getUser(int id);

    @Update("update user set name=#{name},age=#{age} where id=#{id}")
    int updateUser(User user);

    @Delete("delete from user where id=#{id}")
    void deleteUser(int id);

    @Select("select * from user where name=#{name}")
    User getUserByName(String name);
}
