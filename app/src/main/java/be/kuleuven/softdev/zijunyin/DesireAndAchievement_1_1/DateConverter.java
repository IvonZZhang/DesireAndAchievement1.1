package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1;

import android.arch.persistence.room.TypeConverter;

import java.lang.annotation.Annotation;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateConverter {

    /*@TypeConverter
    public static Calendar toCalendar(String string){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        try {
            calendar.setTime(dateFormat.parse(string));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return string == null ? null : calendar;
    }

    @TypeConverter
    public static String toString(Calendar calendar){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return calendar == null ? "1970-01-01" : dateFormat.format(calendar);
    }*/

    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }

    /*@Override
    public Class<?>[] value() {
        return new Class[0];
    }*/

    /*@Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }*/
}
