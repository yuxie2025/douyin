package com.b3ad.yuxie.myapplication.utils.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import com.example.anonymous.greendao.DaoMaster;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.internal.DaoConfig;


public class MigrationHelper {
    private static final String CONVERSION_CLASS_NOT_FOUND_EXCEPTION = "MIGRATION HELPER - CLASS DOESN'T MATCH WITH THE CURRENT PARAMETERS";
    private static MigrationHelper instance;

    public static MigrationHelper getInstance() {
        if (instance == null) {
            instance = new MigrationHelper();
        }
        return instance;
    }

    public void migrate(Database db,
                        Class<? extends AbstractDao<?, ?>>... daoClasses) {
        generateTempTables(db, daoClasses);
        DaoMaster.dropAllTables(db, true);
        DaoMaster.createAllTables(db, false);
        restoreData(db, daoClasses);
    }

    private void generateTempTables(Database db,
                                    Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            try {
                DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);

                String divider = "";
                String tableName = daoConfig.tablename;
                String tempTableName = daoConfig.tablename.concat("_TEMP");
                ArrayList<String> properties = new ArrayList<String>();

                StringBuilder createTableStringBuilder = new StringBuilder();

                createTableStringBuilder.append("CREATE TABLE ")
                        .append(tempTableName).append(" (");

                for (int j = 0; j < daoConfig.properties.length; j++) {
                    String columnName = daoConfig.properties[j].columnName;

                    if (getColumns(db, tableName).contains(columnName)) {
                        properties.add(columnName);

                        String type = null;

                        try {
                            type = getTypeByClass(daoConfig.properties[j].type);
                        } catch (Exception exception) {
                            // Crashlytics.logException(exception);
                            Log.i("haha", exception.getMessage());
                        }

                        createTableStringBuilder.append(divider)
                                .append(columnName).append(" ").append(type);

                        if (daoConfig.properties[j].primaryKey) {
                            createTableStringBuilder.append(" PRIMARY KEY");
                        }

                        divider = ",";
                    }
                }
                createTableStringBuilder.append(");");

                db.execSQL(createTableStringBuilder.toString());

                StringBuilder insertTableStringBuilder = new StringBuilder();

                insertTableStringBuilder.append("INSERT INTO ")
                        .append(tempTableName).append(" (");
                insertTableStringBuilder
                        .append(TextUtils.join(",", properties));
                insertTableStringBuilder.append(") SELECT ");
                insertTableStringBuilder
                        .append(TextUtils.join(",", properties));
                insertTableStringBuilder.append(" FROM ").append(tableName)
                        .append(";");

                db.execSQL(insertTableStringBuilder.toString());
            } catch (Exception e) {
                // 这是异常信息
                System.out.println("这个类真的不存在!");
            }
        }
    }

    private void restoreData(Database db,
                             Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            try {
                DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);

                String tableName = daoConfig.tablename;
                String tempTableName = daoConfig.tablename.concat("_TEMP");
                ArrayList<String> properties = new ArrayList<String>();

                for (int j = 0; j < daoConfig.properties.length; j++) {
                    String columnName = daoConfig.properties[j].columnName;

                    if (getColumns(db, tempTableName).contains(columnName)) {
                        properties.add(columnName);
                    }
                }

                StringBuilder insertTableStringBuilder = new StringBuilder();

                insertTableStringBuilder.append("INSERT INTO ")
                        .append(tableName).append(" (");
                insertTableStringBuilder
                        .append(TextUtils.join(",", properties));
                insertTableStringBuilder.append(") SELECT ");
                insertTableStringBuilder
                        .append(TextUtils.join(",", properties));
                insertTableStringBuilder.append(" FROM ").append(tempTableName)
                        .append(";");

                StringBuilder dropTableStringBuilder = new StringBuilder();

                dropTableStringBuilder.append("DROP TABLE ").append(
                        tempTableName);

                db.execSQL(insertTableStringBuilder.toString());
                db.execSQL(dropTableStringBuilder.toString());

                // 如果是SENT_ONE_BY_ONE_TARGET表则需要将为空的设置成"0"
                if (tableName.equals("SENT_ONE_BY_ONE_TARGET")) {
                    Log.i("haha", "进来了啦");
                    String insertTableString = "update SENT_ONE_BY_ONE_TARGET set Text_State='0',LINK_STATE = '0',PICTURE_STATE = '0',VIDEO_STATE = '0',FAVORIT_STATE = '0',BUSINESS_CARD_STATE = '0',ZOMBIE_STATE = '0',SMALL_ROUTINE_STATE = '0' where Text_State IS NULL OR LINK_STATE IS NULL OR PICTURE_STATE IS NULL OR VIDEO_STATE IS NULL OR FAVORIT_STATE IS NULL OR BUSINESS_CARD_STATE IS NULL OR ZOMBIE_STATE IS NULL OR SMALL_ROUTINE_STATE IS NULL";
                    db.execSQL(insertTableString);
                }

            } catch (Exception e) {
                // 这是异常信息
                System.out.println("这个类真的不存在!");
            }
        }
    }

    private String getTypeByClass(Class<?> type) throws Exception {
        if (type.equals(String.class)) {
            return "TEXT";
        }
        if (type.equals(Long.class) || type.equals(Integer.class)
                || type.equals(long.class)) {
            return "INTEGER";
        }
        if (type.equals(Boolean.class)) {
            return "BOOLEAN";
        }

        Exception exception = new Exception(
                CONVERSION_CLASS_NOT_FOUND_EXCEPTION.concat(" - Class: ")
                        .concat(type.toString()));
        // Crashlytics.logException(exception);
        Log.i("haha", exception.getMessage());
        throw exception;
    }

    private static List<String> getColumns(Database db, String tableName) {
        List<String> columns = new ArrayList<String>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 1",
                    null);
            if (cursor != null) {
                columns = new ArrayList<String>(Arrays.asList(cursor
                        .getColumnNames()));
            }
        } catch (Exception e) {
            Log.v(tableName, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return columns;
    }
}
