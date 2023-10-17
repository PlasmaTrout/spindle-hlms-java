package com.programmervsworld.resources;


import com.programmervsworld.api.Alarm;
import com.programmervsworld.dao.AlarmDao;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/alarms")
@Produces(MediaType.APPLICATION_JSON)
public class AlarmsResource {

    private final AlarmDao alarmDao;

    public AlarmsResource(AlarmDao alarmDao) {
        this.alarmDao = alarmDao;
    }

    @GET
    @UnitOfWork
    public List<Alarm> findAll() {
        return alarmDao.findAll();
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public Alarm postNewAlarm(Alarm alarm) {
        return alarmDao.create(alarm);
    }

    @PUT
    @UnitOfWork
    public Alarm updateAlarm(Alarm alarm) {
        return alarmDao.update(alarm);
    }
}
