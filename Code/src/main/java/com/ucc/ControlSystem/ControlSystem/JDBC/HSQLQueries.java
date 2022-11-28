package com.ucc.ControlSystem.ControlSystem.JDBC;

import com.ucc.ControlSystem.ControlSystem.InputParameters.Parameter;
import com.ucc.ControlSystem.ControlSystem.Reporting.Measurement;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Class containing HQL queries
 */
public class HSQLQueries {
    private static HSQLQueries hsqlQueries = null;

    private ConnectionFactory connectionFactory;
    private SessionFactory sessionFactory;

    private HSQLQueries(){
        connectionFactory = ConnectionFactory.getConnectionFactory();
        sessionFactory = connectionFactory.getSessionFactory();
    }

    public static HSQLQueries getHSQLQueries(){
        if(hsqlQueries == null){
            hsqlQueries = new HSQLQueries();
        }
        return hsqlQueries;
    }

    public List<Object> getParameterBasedOnType(Class cls, Parameter type){
        Session s = openSession();
        Query query = s.createQuery("FROM "+cls+" p WHERE p.type=:type",cls);
        query.setParameter("type",type);

        List<Object> results = query.list();

        closeSession(s);
        return results;
    }

    public Object getParameterByType(Class cls, Parameter type){
        Session s = openSession();

        Object res = s.get(cls,type);

        closeSession(s);
        return res;
    }

    public void emptyTable(Class cls){
        Session s = openSession();

        Query q = s.createQuery("DELETE FROM " + cls.getSimpleName());
        q.executeUpdate();

        closeSession(s);
    }

    public List<Measurement> getMeasurementsBasedOnTypeAndInterval(EnvironmentDeviceTypes type, long startTime, long endTime){
        Session s = openSession();

        Query q = s.createQuery("FROM Measurement m WHERE m.device=:device AND m.timestamp>=:startTime AND m.timestamp<=:endTime");
        q.setParameter("device",type);
        q.setParameter("startTime",startTime);
        q.setParameter("endTime",endTime);
        List<Measurement> result = q.list();

        closeSession(s);

        return result;
    }

    private Session openSession(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    private void closeSession(Session session){
        session.getTransaction().commit();
        session.close();
    }
}
