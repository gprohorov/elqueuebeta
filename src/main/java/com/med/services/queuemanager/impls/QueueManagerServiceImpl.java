package com.med.services.queuemanager.impls;

import com.med.dao.patient.impls.PatientDAOImpl;
import com.med.model.Patient;
import com.med.services.appointment.impls.AppointmentServiceImpl;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.event.impls.EventsServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.queuemanager.interfaces.IQueueManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@SuppressWarnings("ALL")
@Service
public class QueueManagerServiceImpl implements IQueueManagerService{

    @Autowired
    PatientDAOImpl patientDAO;

    @Autowired
    AppointmentServiceImpl appointmentService;

    @Autowired
    DoctorServiceImpl doctorService;

    @Autowired
    ProcedureServiceImpl procedureService;

    @Autowired
    EventsServiceImpl eventsService;


    @Override
    public List<Patient> includeApointed() {



        return null;
    }
}
