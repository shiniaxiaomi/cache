package com.lyj.service;

import com.lyj.dao.UserDao;
import com.lyj.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2019/1/29.
 */

@CacheConfig(cacheNames = "user")   //抽取缓存的公共配置
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    /**
     * 将方法的运行结果进行缓存;以后要在使用相同的数据,直接从缓存中获取,不用调用方法
     *
     * cacheManager管理国歌cache组建,对缓存的真正机型crud操作在cache组建中,每个缓存组件有自己唯一的名字
     * 几个属性:
     *      cacheNames/value: 指定缓存组件的名字;将方法的返回结果放在那个缓存中,可以指定多个缓存
     *      key: 缓存数据使用的key;可以用它来指定.(默认是使用方法参数的值)
     *          编写spel指定key: #id:参数id的值;    #a0/#p0/#root.args[0]: 参数列表的第一个参数
     *          如果指定key为 getUser[2]: @Cacheable(key = "#root.methodName+'['+#id+']'")
     *      cacheManager: 指定缓存管理器;
     *          key/cacheManager: 二选一使用
     *      condition: 条件成立,才缓存
     *      unless: 条件成立,则不缓存(可获取到结果后再进行判断)
     *      sync: 缓存是否开启异步模式(默认是false)
     */
    //如果id大于,才进行缓存
    //如果查询结果不为null,才进行缓存
    @Cacheable(value = "user",key = "#id",condition = "#id>1",unless="#result==null or #id==2")
    public User getUser(int id) {
        return userDao.getUser(id);
    }


    /**
     * @CachePut: 即调用方法,有更新缓存数据
     * 修改了数据库的某个数据,同时更新缓存;
     * 运行时机:
     *      1.先调用目标方法
     *      2.将目标方法的返回结果缓存起来(通过 #result 来访问返回结果对象)
     */
    @CachePut(value = "user",key = "#user.id")
    public User updateUser(User user){
        userDao.updateUser(user);
        return user;
    }

    /**
     * @CacheEvict: 清除缓存(当删除数据库中的数据时,清除掉对应的缓存数据)
     *  key: 指定要清除的数据
     *  allEntries: 删除user缓存组件中的所有数据(默认是false)
     *  beforeInvocation: 是否在方法之前执行删除缓存的操作(默认是false)
     *      如果在方法后执行删除,当方法抛异常时,删除缓存操作不会被执行
     */
    @CacheEvict(value = "user",key = "#id")
    public void deleteUser(int id){
        userDao.deleteUser(id);
    }

    /**
     * @Caching: 定义复杂的缓存规则
     */
    @Caching(
            cacheable = {
                    @Cacheable(value = "user",key = "'name:'+#name")
            },
            put = {
                    @CachePut(key = "'id:'+#result.id"),
                    @CachePut(key = "'age:'+#result.age")
            }
    )
    public User getUserByName(String name){
        return userDao.getUserByName(name);
    }

}
