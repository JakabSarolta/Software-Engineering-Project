package com.ucc.ControlSystem.ControlSystem.JDBC;

import com.ucc.ControlSystem.ControlSystem.InputParameters.Parameter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

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
