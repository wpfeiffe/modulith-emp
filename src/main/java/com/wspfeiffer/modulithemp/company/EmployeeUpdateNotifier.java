package com.wspfeiffer.modulithemp.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Timed
public class EmployeeUpdateNotifier {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeUpdateNotifier.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ApplicationEventPublisher events;


    public void notify(EmployeeDto employeeDto) {
        events.publishEvent(employeeDto);
    }
}
