package paf.assessment;

public class queries {
    public static String SELECT_USER_BY_USERID = "select * from user where user_id = ?";

    public static String INSERT_USER = "insert into user values(?, ?, ?)";

    public static String INSERT_TASK = "insert into task (description, priority, due_date, user_id) values(?, ?, ?, ?)";

    public static String GET_ALL_USERS = "select * from user";


}