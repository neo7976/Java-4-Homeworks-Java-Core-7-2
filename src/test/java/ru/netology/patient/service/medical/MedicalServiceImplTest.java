package ru.netology.patient.service.medical;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;

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
        Mockito.when(patientInfoRepository.getById(Mockito.any()))
                .thenReturn(new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));


        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        String message = "Warning, patient with id: null, need help";
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        BloodPressure currentPressure = new BloodPressure(121, 80);
        medicalService.checkBloodPressure("id"
                , currentPressure);
                Mockito.verify(alertService).send(argumentCaptor.capture());
        Assertions.assertEquals(message, argumentCaptor.getValue());
    }

    @Test
    void checkTemperature() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(Mockito.any()))
                .thenReturn(new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));


        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        String message = "Warning, patient with id: null1, need help";
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);

        BigDecimal currentTemperature = new BigDecimal("38.16");

        medicalService.checkTemperature("id"
                , currentTemperature);
        Mockito.verify(alertService).send(argumentCaptor.capture());
        Assertions.assertEquals(message, argumentCaptor.getValue());
    }

    @Test
    void checkBloodPressure_send_message() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(Mockito.any()))
                .thenReturn(new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));


        SendAlertService alertService = Mockito.mock(SendAlertService.class);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        BloodPressure currentPressure = new BloodPressure(120, 80);
        medicalService.checkBloodPressure("id"
                , currentPressure);
        Mockito.verify(alertService,Mockito.never()).send(argumentCaptor.capture());

    }
}