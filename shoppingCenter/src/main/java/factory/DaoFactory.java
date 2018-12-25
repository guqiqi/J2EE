package factory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DaoFactory {
    private DataSource ds;
    private Context ctx;

    public DaoFactory(){
        try {
            ctx = new InitialContext();
            ds= (DataSource) ctx.lookup("java:comp/env/jdbc/orders");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public DataSource getDs() {
        return ds;
    }
}
