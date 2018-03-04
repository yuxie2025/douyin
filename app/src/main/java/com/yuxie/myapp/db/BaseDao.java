package com.yuxie.myapp.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.yuxie.myapp.db.annotion.DbFiled;
import com.yuxie.myapp.db.annotion.DbTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/09/10.
 */

public abstract class BaseDao<T> implements IBaseDao<T> {

    private SQLiteDatabase mDatabase;
    private boolean isInit = false;
    private Class<T> mEntityClass;
    private String mTableName;
    private HashMap<String, Field> mCacheMap;

    /**
     * 初始化数据库表(创建数据库表,并缓存实体和表对应关系)
     * @param entity
     * @param database
     * @return
     */
    protected synchronized boolean init(Class<T> entity, SQLiteDatabase database) {
        if (!isInit) {
            mEntityClass=entity;
            mDatabase = database;
            if (entity.getAnnotation(DbTable.class) == null) {
                mTableName = entity.getClass().getSimpleName();//实体没有使用注解,直接使用类名作为数据库表名
            } else {
                mTableName = entity.getAnnotation(DbTable.class).value();//有注解,获取注解,作为数据库表名
            }
            if (!mDatabase.isOpen()) {
                //数据库已经是打开状态,直接返回
                return false;
            }
            if (!TextUtils.isEmpty(createTable())){
                //创建数据库表,sql不为空,执行创建表
                mDatabase.execSQL(createTable());
            }
            mCacheMap=new HashMap<>();
            initCacheMap();//缓存表和实现对应关系
            isInit = true;
        }
        return isInit;
    }

    /**
     * 初始化,类和数据库表对应关系,并换成map
     */
    protected void initCacheMap() {
        //查询字段名的sql语句
        String sql = "select * from " + mTableName + " limit 1 , 0";
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(sql, null);
            //数据库表对应的所有字段名数组
            String[] columnNames = cursor.getColumnNames();
            //实体的所以成员变量集合
            Field[] columnFields = mEntityClass.getFields();
            for (Field field : columnFields) {
                field.setAccessible(true);
            }
            for (String columnName : columnNames) {
                Field columnField = null;//实体的成员变量
                for (Field field : columnFields) {
                    String fieldName = null;
                    if (field.getAnnotation(DbFiled.class) != null) {
                        fieldName = field.getAnnotation(DbFiled.class).value();
                    } else {
                        fieldName = field.getName();
                    }
                    if (columnName.equals(fieldName)) {
                        columnField = field;
                        break;
                    }
                }
                if (columnField != null) {
                    mCacheMap.put(columnName, columnField);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG","Exception:"+e.toString());
        }finally {
            if (cursor!=null){
                cursor.close();
                cursor=null;
            }
        }
    }

    @Override
    public Long insert(T entity) {
        Map<String,String> map=getValues(entity);
        ContentValues values=getContentValus(map);
        Long result=mDatabase.insert(mTableName,null,values);
        return result;
    }

    @Override
    public Long update(T entity, T where) {
        long result=-1;
        Map values=getValues(entity);
        Map whereClause=getValues(where);
        Condition condition=new Condition(whereClause);
        ContentValues contentValues=getContentValus(values);
        result=mDatabase.update(mTableName,contentValues,condition.getWhereClause(),condition.getWhereAgrs());
        return result;
    }

    @Override
    public Long delete(T where) {
        long result=-1;

        Map whereClause=getValues(where);
        Condition condition=new Condition(whereClause);
        result=mDatabase.delete(mTableName,condition.getWhereClause(),condition.getWhereAgrs());

        return result;
    }

    @Override
    public List<T> query(T where) {
        return query(where,null,null,null);
    }

    @Override
    public List<T> query(T where, String orderBy, Integer startIndex, Integer limit) {
        List<T> result=new ArrayList();
        Map map=getValues(where);
        String limitString=null;//拼接 limit字符串 "limit 1 ,0' 获取第一条数据的 第0条
        if (startIndex!=null&&limit!=null){
            limitString=startIndex+" , "+limit;
        }
        Condition condition=new Condition(map);
        Cursor c=mDatabase.query(mTableName,null,condition.getWhereClause(),condition.getWhereAgrs(),null,null,orderBy,limitString);
        result=getResult(c,where);
        if (c!=null){
            c.close();
        }
        return result;
    }

    /**
     * 游标转集合
     * @param c
     * @param where
     * @return
     */
    private List<T> getResult(Cursor c,T where){
        ArrayList list=new ArrayList();
        Object item;
        while (c.moveToNext()){
            try {
                item=where.getClass().newInstance();
                Iterator iterator=mCacheMap.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry entry=(Map.Entry )iterator.next();

                    String colomunName=(String) entry.getKey();//字段名
                    Integer colomunIndex=c.getColumnIndex(colomunName);//字段名对应的游标位置
                    Field field=(Field)entry.getValue();
                    Class type=field.getType();//或是数据类型
                    if(colomunIndex!=-1){
                        if (type==String.class){
                            field.set(item,c.getString(colomunIndex));
                        }else if (type==Integer.class){
                            field.set(item,c.getInt(colomunIndex));
                        }else if (type==Long.class){
                            field.set(item,c.getLong(colomunIndex));
                        }else if (type==Double.class){
                            field.set(item,c.getDouble(colomunIndex));
                        }else if (type==Float.class){
                            field.set(item,c.getFloat(colomunIndex));
                        }else if (type==Short.class){
                            field.set(item,c.getShort(colomunIndex));
                        }else if (type==byte[].class){
                            field.set(item,c.getBlob(colomunIndex));
                        }else{
                            //不支持数据库类型
                            continue;
                        }
                    }
                    list.add(item);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 构造查询条件
     */
    class Condition{
        private String whereClause;//条件 "name= ?'
        private String[] whereAgrs;//条件? 对应的值 new String[ "admin"]

        public Condition(Map<String,String> whereClause){
            ArrayList list=new ArrayList();
            StringBuilder sb=new StringBuilder();
            sb.append(" 1=1 ");
            Set keys =whereClause.keySet();
            Iterator<String> iterator=keys.iterator();
            while (iterator.hasNext()){
                String key=iterator.next();//字段名
                String value =whereClause.get(key);//字段值
                if (value!=null){
                    sb.append(" and " +key+"= ?");
                    list.add(value);
                }
            }
            this.whereClause=sb.toString();
            this.whereAgrs=(String[]) list.toArray(new String[list.size()]);//list转数组
        }

        public String getWhereClause() {
            return whereClause;
        }

        public String[] getWhereAgrs() {
            return whereAgrs;
        }
    }

    /**
     * 对象转Map 字段名-->值
     * @param entity
     * @return
     */
    private Map<String,String> getValues(T entity){
        Map<String,String> result=new HashMap<>();

        Iterator<Field> filedsItertor=mCacheMap.values().iterator();
        while (filedsItertor.hasNext()){
            Field colmunToField=filedsItertor.next();
            String cacheKey=null;
            String cacheValue=null;
            if (colmunToField.getAnnotation(DbFiled.class)!=null){
                cacheKey=colmunToField.getAnnotation(DbFiled.class).value();//获取注解名
            }else{
                cacheKey=colmunToField.getName();//获取类的成员变量
            }
            try {
                if(colmunToField.get(entity)==null){
                    continue;
                }
                cacheValue=colmunToField.get(entity).toString();//获取变量对应的值
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            result.put(cacheKey,cacheValue);
        }
        return result;
    }

    /**
     * map转ContentValues
     * @param map
     * @return
     */
    private ContentValues getContentValus(Map<String,String> map){
        ContentValues contentVales=new ContentValues();
        Set keys=map.keySet();
        Iterator<String> iterator=keys.iterator();
        while (iterator.hasNext()){
            String key=iterator.next();
            String value=map.get(key);
            if (value!=null){
                contentVales.put(key,value);
            }
        }
        return contentVales;
    }

    /**
     * 创建数据库表
     * @return
     */
    protected abstract String createTable();
}
