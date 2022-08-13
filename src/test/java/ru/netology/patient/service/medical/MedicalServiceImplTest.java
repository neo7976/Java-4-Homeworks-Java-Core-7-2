package ru.netology.patient.service.medical;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

class MedicalServiceImplTest {
    MedicalServiceImpl medicalService;

    public static Stream<Arguments> sourceBloodPressure() {
        return Stream.of(Arguments.of(new BloodPressure(100, 70)),
                Arguments.of(new BloodPressure(130, 90)),
                Arguments.of(new BloodPressure(110, 80)),
                Arguments.of(new BloodPressure(130, 80)),
                Arguments.of(new BloodPressure(120, 70)),
                Arguments.of(new BloodPressure(120, 90)));
    }

    @BeforeEach
    void setUp() {
        System.out.println("Начало теста: ");
    }

    @AfterEach
    void tearDown() {
        System.out.println("\nОкончание теста: ");
        medicalService = null;
    }

    @ParameterizedTest
    @MethodSource("sourceBloodPressure")
    void checkBloodPressure(BloodPressure currentPressure) {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(Mockito.any()))
                .thenReturn(new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));


        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        String message = "Warning, patient with id: null, need help";
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
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
        String message = "Warning, patient with id: null, need help";
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);

        BigDecimal currentTemperature = new BigDecimal("35.14");

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
        Mockito.verify(alertService, Mockito.never()).send(argumentCaptor.capture());

    }

    @Test
    void checkTemperature_send_message() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(Mockito.any()))
                .thenReturn(new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));


        SendAlertService alertService = Mockito.mock(SendAlertService.class);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);

        BigDecimal currentTemperature = new BigDecimal("35.15");

        medicalService.checkTemperature("id"
                , currentTemperature);
        Mockito.verify(alertService, Mockito.never()).send(argumentCaptor.capture());

    }
}