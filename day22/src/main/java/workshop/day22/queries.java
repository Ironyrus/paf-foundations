package workshop.day22;

public class queries {
    public static String SQL_SELECT_ALL_FROM_RSVP = "select * from rsvp";
    public static String SQL_SELECT_BY_NAME = "select * from rsvp where name like ?";
    public static String SQL_UPDATE_RSVP_BY_EMAIL = "update rsvp set name = ?, phone = ?, confirmation_date = ?, comments = ? where email = ?";
    public static String SQL_ADD_NEW_RSVP = "insert into rsvp values(?, ?, ?, ?, ?)";
    public static String SQL_COUNT_ALL_RSVP = "select count(*) from rsvp";
}
