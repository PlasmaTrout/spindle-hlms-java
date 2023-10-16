package com.programmervsworld.view;

import com.programmervsworld.api.Alarm;
import io.dropwizard.views.common.View;

import java.util.List;

public class AlarmEditView extends View {
    private final List<Alarm> alarms;

    public AlarmEditView(List<Alarm> alarms) {
        super("alarmeditor.mustache");
        this.alarms = alarms;
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }
}
