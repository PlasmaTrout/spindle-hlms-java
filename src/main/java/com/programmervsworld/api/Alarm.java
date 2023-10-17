package com.programmervsworld.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "alarms")
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {
    @Id
    private int id;
    @JsonFormat(pattern = "uuuu-MM-dd HH:m:ss")
    private LocalDateTime date;
    private String tid;
    private String aid;
    private AlarmSeverity severity;
    private String message;
    private AlarmState state;
    private String description;
    @Column(name = "groupname")
    private String group;
    private String link;
}
