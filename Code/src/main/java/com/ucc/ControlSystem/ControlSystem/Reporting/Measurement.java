package com.ucc.ControlSystem.ControlSystem.Reporting;

import com.ucc.ControlSystem.ControlSystem.EnvironmentControllers.States;
import com.ucc.ControlSystem.ControlSystem.JDBC.ConnectionFactory;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentDeviceTypes;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column
    private EnvironmentDeviceTypes device;

    @Column
    private Double value;

    @Column
    private States state;

    @Column
    private Long timestamp;

    public Measurement() {
    }

    public Measurement(EnvironmentDeviceTypes device, double value, States state, Long time) {
        this.device = device;
        this.value = value;
        this.state = state;
        this.timestamp = time;
    }

    public void saveMeasurement(){
        SessionFactory sessionFactory = ConnectionFactory.getConnectionFactory().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(this);

        session.getTransaction().commit();
        session.close();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EnvironmentDeviceTypes getDevice() {
        return device;
    }

    public void setDevice(EnvironmentDeviceTypes device) {
        this.device = device;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }
}
