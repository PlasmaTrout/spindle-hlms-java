package com.programmervsworld.resources;


import com.programmervsworld.api.Alarm;
import com.programmervsworld.dao.AlarmDao;
import com.programmervsworld.view.AlarmEditView;
import com.programmervsworld.view.AlarmsView;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

@Path("/alarms")
public class AlarmsResource {

    private final AlarmDao alarmDao;

    public AlarmsResource(AlarmDao alarmDao) {
        this.alarmDao = alarmDao;
    }

    @GET
    @UnitOfWork
    public AlarmsView getAlarms() {
        var alarms = alarmDao.findAll();
        return new AlarmsView(alarms);
    }

    @GET
    @Path("/editor")
    @UnitOfWork
    public AlarmEditView getEditableAlarms() {
        var alarms = alarmDao.findAll();
        return new AlarmEditView(alarms);
    }
}
