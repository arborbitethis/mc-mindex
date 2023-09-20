package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportingController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingController.class);

    @Autowired
    private ReportingStructureService reportingStructureService;

    @GetMapping("/reporting/{id}")
    public ReportingStructure read(@PathVariable String id) {
        LOG.debug("Received reporting structure request for employee id [{}]", id);

        return reportingStructureService.getReportingStructure(id);
    }
}
