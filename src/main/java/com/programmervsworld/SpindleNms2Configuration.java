package com.programmervsworld;

import io.dropwizard.core.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import jakarta.validation.Valid;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import jakarta.validation.constraints.*;

@Data
public class SpindleNms2Configuration extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();
}
