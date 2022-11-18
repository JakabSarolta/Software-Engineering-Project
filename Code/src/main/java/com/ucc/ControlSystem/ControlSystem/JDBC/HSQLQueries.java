package com.ucc.ControlSystem.ControlSystem.JDBC;

import com.ucc.ControlSystem.ControlSystem.InputParameters.EnvironmentPropertyParameter;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentDeviceTypes;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HSQLQueries {
    private static HSQLQueries hsqlQueries = null;

    private ConnectionFactory connectionFactory;
    private Session session;

    private HSQLQueries(){
        connectionFactory = ConnectionFactory.getConnectionFactory();
        session = connectionFactory.getSession();
    }

    public static HSQLQueries getHSQLQueries(){
        if(hsqlQueries == null){
            hsqlQueries = new HSQLQueries();
        }
        return hsqlQueries;
    }

    public List<EnvironmentPropertyParameter> getEnvironmentPropertyParametersWithType(EnvironmentDeviceTypes type){
        Query query = session.createQuery("FROM EnvironmentPropertyParameter p WHERE p.type=:type",EnvironmentPropertyParameter.class);
        query.setParameter("type",type);
        return query.list();
    }
}
