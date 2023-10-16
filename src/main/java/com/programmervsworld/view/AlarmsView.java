package com.programmervsworld.view;

import com.programmervsworld.api.Alarm;
import io.dropwizard.views.common.View;

import java.util.List;

public class AlarmsView extends View {
    private final List<Alarm> alarms;

    public AlarmsView(List<Alarm> alarms) {
        super("alarms.mustache");
        this.alarms = alarms;
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }
}
