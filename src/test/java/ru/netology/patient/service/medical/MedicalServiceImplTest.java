package ru.netology.patient.service.medical;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;

import static org.junit.jupiter.api.Assertions.*;

class MedicalServiceImplTest {
    MedicalServiceImpl medicalService;

    @BeforeEach
    void setUp() {
        System.out.println("Начало теста: ");
    }

    @AfterEach
    void tearDown() {
        System.out.println("\nОкончание теста: ");
        medicalService = null;
    }

    @Test
    void checkBloodPressure() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);

        SendAlertService alertService = Mockito.mock(SendAlertService.class);


        medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
    }

    @Test
    void checkTemperature() {
    }
}