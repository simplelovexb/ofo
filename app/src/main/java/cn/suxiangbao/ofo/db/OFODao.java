package cn.suxiangbao.ofo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.suxiangbao.ofo.R;
import cn.suxiangbao.ofo.entity.OFOBike;

/**
 * Created by Administrator on 2017/1/12.
 */

public class OFODao {

    private static final String TAG = "OFOBikeDao";

    // 列定义
    private final String[] OFOBike_COLUMNS = new String[] {"Id", "bikenumber","password"};

    private Context context;
    private OFODBHelper helper;

    public OFODao(Context context) {
        this.context = context;
        helper = new OFODBHelper(context);
    }
    /**
     * 判断表中是否有数据
     */
    public boolean isDataExist(){
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = helper.getReadableDatabase();
            // select count(Id) from OFOBikes
            cursor = db.query(OFODBHelper.TABLE_NAME, new String[]{"COUNT(Id)"}, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            if (count > 0) return true;
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    /**
     * 执行自定义SQL语句
     */
    public void execSQL(String sql) {
        SQLiteDatabase db = null;

        try {
            if (sql.contains("select")){
                Toast.makeText(context, R.string.strUnableSql, Toast.LENGTH_SHORT).show();
            }else if (sql.contains("insert") || sql.contains("update") || sql.contains("delete")){
                db = helper.getWritableDatabase();
                db.beginTransaction();
                db.execSQL(sql);
                db.setTransactionSuccessful();
                Toast.makeText(context, R.string.strSuccessSql, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, R.string.strErrorSql, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    /**
     * 查询数据库中所有数据
     */
    public List<OFOBike> getAllData(){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = helper.getReadableDatabase();
            // select * from OFOBikes
            cursor = db.query(OFODBHelper.TABLE_NAME, OFOBike_COLUMNS, null, null, null, null, null);
            List<OFOBike> bikeList = new ArrayList<>(cursor.getCount());
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    bikeList.add(parseBike(cursor));
                }
            }
            return bikeList;
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }

    /**
     * 新增一条数据
     */
    public boolean insertOne(OFOBike bike){
        SQLiteDatabase db = null;

        try {
            db = helper.getWritableDatabase();
            db.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put("bikenumber", bike.getBikenumber());
            contentValues.put("password", bike.getPassword());
            db.insertOrThrow(OFODBHelper.TABLE_NAME, null, contentValues);
            db.setTransactionSuccessful();
            return true;
        }catch (SQLiteConstraintException e){
            Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e(TAG, "", e);
        }finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    /**
     * 删除一条数据  此处删除Id为7的数据
     */
    public boolean deleteOne(OFOBike bike) {
        SQLiteDatabase db = null;
        int affect = 0;
        try {
            db = helper.getWritableDatabase();
            db.beginTransaction();

            // delete from OFOBikes where Id = 7
            affect = db.delete(OFODBHelper.TABLE_NAME, "Id = ?", new String[]{String.valueOf(bike.getId())});
            if (affect == 0){
                affect = db.delete(OFODBHelper.TABLE_NAME, "bikenumber = ?", new String[]{String.valueOf(bike.getBikenumber())});
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e(TAG, "", e);
            affect = 0;
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return affect == 1;
    }

    public boolean updateOFOBike(OFOBike bike){
        SQLiteDatabase db = null;
        int affect = 0;
        try {
            db = helper.getWritableDatabase();
            db.beginTransaction();

            // update OFOBikes set OFOBikePrice = 800 where Id = 6
            ContentValues cv = new ContentValues();
            cv.put("password", bike.getPassword());
            affect = db.update(OFODBHelper.TABLE_NAME,
                    cv,
                    "Id = ?",
                    new String[]{String.valueOf(bike.getId())});
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
            affect = 0;
        }
        finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }

        return affect == 1;
    }

    public List<OFOBike> selectOne(OFOBike bike){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = helper.getReadableDatabase();

            cursor = db.query(OFODBHelper.TABLE_NAME,
                    OFOBike_COLUMNS,
                    "bikenumber = ?",
                    new String[] {bike.getBikenumber()},
                    null, null, null);

            if (cursor.getCount() > 0) {
                List<OFOBike> OFOBikeList = new ArrayList<OFOBike>(cursor.getCount());
                while (cursor.moveToNext()) {
                    OFOBike OFOBike = parseBike(cursor);
                    OFOBikeList.add(OFOBike);
                }
                return OFOBikeList;
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }

    public int count(){
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = helper.getReadableDatabase();
            // select count(Id) from OFOBikes where Country = 'China'
            cursor = db.query(OFODBHelper.TABLE_NAME, null,
                    null,null,null, null, null);

            count = cursor.getCount();
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return count;
    }

    /**
     * 将查找到的数据转换成OFOBike类
     */
    private OFOBike parseBike(Cursor cursor){
        OFOBike OFOBike = new OFOBike();
        OFOBike.setId(cursor.getInt(cursor.getColumnIndex("Id")));
        OFOBike.setBikenumber(cursor.getString(cursor.getColumnIndex("bikenumber"))); 
        OFOBike.setPassword(cursor.getString(cursor.getColumnIndex("password")));
        return OFOBike;
    }
}
