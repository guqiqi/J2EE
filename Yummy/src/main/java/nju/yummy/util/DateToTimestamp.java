package nju.yummy.util;

import java.sql.Timestamp;
import java.util.Date;

public class DateToTimestamp {
    public static Timestamp toTimeStamp(Date date){
        return new Timestamp(date.getTime());
    }
}
