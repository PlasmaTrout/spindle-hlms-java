package com.programmervsworld.resources;

import com.programmervsworld.api.Alarm;
import com.programmervsworld.api.AlarmState;
import com.programmervsworld.dao.AlarmDao;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/alarm")
@Produces(MediaType.APPLICATION_JSON)
public class AlarmResource {

    private final AlarmDao alarmDao;

    public AlarmResource(AlarmDao alarmDao) {
        this.alarmDao = alarmDao;
    }

    @GET
    @UnitOfWork
    public List<Alarm> toggle(@QueryParam("id") Integer id,
                              @QueryParam("mode") String mode,
                              @QueryParam("tid") String tid,
                              @QueryParam("aid") String aid) {

        var resultList = new ArrayList<Alarm>();

        if (id != null) {
            var result = alarmDao.findById(id);
            result.ifPresent((a) -> {
                if ("active".equals(mode.toLowerCase())) {
                    a.setState(AlarmState.ACTIVE);
                } else {
                    a.setState(AlarmState.CLEAR);
                }
                alarmDao.update(a);
            });
            resultList.add(result.orElse(Alarm.builder()
                    .message("Couldn't find id")
                    .id(id)
                    .build()));
        }

        if(tid != null) {
            var result = alarmDao.finalByTid(tid);
            if(aid != null) {
                result = result.stream().filter(n -> n.getAid().equals(aid)).collect(Collectors.toList());
            }
            result.forEach((a) -> {
                if ("active".equals(mode.toLowerCase())) {
                    a.setState(AlarmState.ACTIVE);
                } else {
                    a.setState(AlarmState.CLEAR);
                }
                alarmDao.update(a);
                resultList.add(a);
            });

        }

        return resultList;
    }
}
