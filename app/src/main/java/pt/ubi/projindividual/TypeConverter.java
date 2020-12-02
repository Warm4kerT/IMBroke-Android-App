package pt.ubi.projindividual;

import java.util.Date;
import java.util.UUID;

public class TypeConverter {
    @androidx.room.TypeConverter
    public static Date fromTimestamp(Long value){
        return value == null ? null : new Date(value);
    }

    @androidx.room.TypeConverter
    public static Long dateToTimestamp(Date date){
        return date == null ? null : date.getTime();
    }

    @androidx.room.TypeConverter
    public static UUID fromString(String value){
        return value == null ? null : UUID.fromString(value);
    }

    @androidx.room.TypeConverter
    public static  String toUUID(UUID value){
        return  value == null ? null : value.toString();
    }
}
