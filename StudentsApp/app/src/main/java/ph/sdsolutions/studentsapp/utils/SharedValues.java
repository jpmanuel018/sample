package ph.sdsolutions.studentsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedValues {

    private final static String PREF_NAME = "StudentsApp";

    public static void clear(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.remove(key);
        editor.apply();
    }

    public static void setValues(Context context, String key, String value){
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setBooleanValues(Context context, String key, boolean value){
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getSharedValuesString(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static boolean getSharedValuesBoolean(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

}