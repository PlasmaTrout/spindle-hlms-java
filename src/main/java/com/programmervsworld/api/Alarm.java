package com.programmervsworld.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

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
