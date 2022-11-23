package com.ucc.ControlSystem.ControlSystem.JDBC;

import com.ucc.ControlSystem.ControlSystem.InputParameters.EnvironmentPropertyParameter;
import com.ucc.ControlSystem.ControlSystem.InputParameters.MeasurementIntervalParameter;
import com.ucc.ControlSystem.ControlSystem.InputParameters.OtherParameter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ConnectionFactory {

    private static ConnectionFactory connectionFactory = null;

    private final String jdbcUrl;
    private final String userName;
    private final String password;
    private SessionFactory sessionFactory;

    private ConnectionFactory(String jdbcUrl, String userName, String password) {
        this.jdbcUrl = jdbcUrl;
        this.userName = userName;
        this.password = password;

        createConnection();

    }

    public static void createDbConnection(String jdbcUrl, String userName, String password){
        if(connectionFactory == null){
            connectionFactory = new ConnectionFactory(jdbcUrl,userName,password);
        }
    }

    public static ConnectionFactory getConnectionFactory(){
        return connectionFactory;
    }

    private void createConnection(){
            SessionFactory sessionFactory = new Configuration()
                    .setProperty("hibernate.connection.driver_class","com.mysql.jdbc.Driver")
                    .setProperty("hibernate.connection.url",jdbcUrl)
                    .setProperty("hibernate.connection.username",userName)
                    .setProperty("hibernate.connection.password",password)
                    .setProperty("hibernate.hbm2ddl.auto","update")
                    .setProperty("hibernate.connection.pool_size","1")
                    .setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect")
                    .setProperty("hibernate.show_sql","true")
                    .setProperty("hibernate.current_session_context_class","thread")
                    .addAnnotatedClass(EnvironmentPropertyParameter.class)
                    .addAnnotatedClass(MeasurementIntervalParameter.class)
                    .addAnnotatedClass(OtherParameter.class)
                    .buildSessionFactory();
            this.sessionFactory = sessionFactory;
//            this.session = factory.getCurrentSession();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}