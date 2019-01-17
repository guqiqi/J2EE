package daoImpl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DaoSingleton {
    private static DataSource ds;
    private static Context ctx;

    public static DataSource getDs(){
        if(ds==null) {
            try {
                ctx = new InitialContext();
                ds = (DataSource) ctx.lookup("java:comp/env/jdbc/orders");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }

        return ds;
    }
}
