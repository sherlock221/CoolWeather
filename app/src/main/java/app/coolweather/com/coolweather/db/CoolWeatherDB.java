package app.coolweather.com.coolweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import app.coolweather.com.coolweather.model.City;
import app.coolweather.com.coolweather.model.County;
import app.coolweather.com.coolweather.model.Province;

/**
 * 单例模式创建CoolWeatherDB
 * Created by sherlock on 15/11/18.
 */
public class CoolWeatherDB {

    public static final String DB_NAME = "cool_weather";

    public static final int VERSION = 1;

    private static CoolWeatherDB coolWeatherDB;

    private SQLiteDatabase db;


    private CoolWeatherDB(Context context) {
        CoolWeatherOpenHelper coolWeatherOpenHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = coolWeatherOpenHelper.getWritableDatabase();
    }


    /**
     * 获得单例
     *
     * @param context
     * @return
     */
    public static CoolWeatherDB getInstance(Context context) {
        if (coolWeatherDB == null) {
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }


    /**
     * 存储数据
     *
     * @param p
     */
    public void saveProvince(Province p) {
        if (p != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("province_name", p.getProvinceName());
            contentValues.put("province_code", p.getProvinceCode());
            db.insert("Province", null, contentValues);

        }
    }

    public void saveCity(City c) {
        if (c != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("city_name", c.getCityCode());
            contentValues.put("city_code", c.getCityCode());
            contentValues.put("province_id", c.getProvinceId());
            db.insert("City", null, contentValues);
        }
    }

    public void saveCounty(County county) {

        if (county != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("county_name", county.getCountyName());
            contentValues.put("county_code", county.getCountyCode());
            contentValues.put("city_id", county.getCityId());
            db.insert("County", null, contentValues);
        }

    }


    public List<Province> loadProvinceList() {

        List<Province> list = new ArrayList<>();


        Cursor cursor;

        cursor = db.query("Province", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));

                list.add(province);
            }
            while (cursor.moveToNext());
        }

        return list;
    }

    public List<City> loadCityList(int provinceId){

        List<City> list = new ArrayList<>();

        Cursor cursor;

        cursor = db.query("City",null,"province_id=?",new String[]{String.valueOf(provinceId)},null,null,null);


        if(cursor.moveToFirst()){

            do{
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
                list.add(city);
            }
            while (cursor.moveToNext());

        }

        return list;

    }

    public List<County> loadCountyList(int cityId){

        List<County> list = new ArrayList<>();

        Cursor cursor;

        cursor = db.query("County",null,"city_id=?",new String[]{String.valueOf(cityId)},null,null,null);

        if(cursor.moveToFirst()){
            do{
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
                list.add(county);
            }
            while (cursor.moveToNext());

        }

        return list;

    }


}
