package com.yuxie.demo.db;

import java.util.List;

/**
 * Created by Administrator on 2017/09/10.
 */

public interface IBaseDao<T> {
    /**
     * 插入数据
     * @param entity
     * @return
     */
    Long insert(T entity);

    /**
     * 更新数据
     * @param entity
     * @param where
     * @return
     */
    Long update(T entity,T where);

    /**
     * 删除数据
     * @param where
     * @return
     */
    Long delete(T where);

    /**
     * 查询数据库
     * @param where
     * @return
     */
    List<T> query(T where);

    List<T> query(T where,String orderBy,Integer startIndex,Integer limit);

}
