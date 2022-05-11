import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class MedicalServiceImplTest {

    @Test
    public void checkBloodPressureNormalTest() {
        PatientInfo patientInfo = new PatientInfo("Иван", "Иванов", LocalDate.of(1995, 6, 11),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80)));
        PatientInfo info = new PatientInfo(UUID.randomUUID().toString(),
                patientInfo.getName(),
                patientInfo.getSurname(),
                patientInfo.getBirthday(),
                patientInfo.getHealthInfo());
        BloodPressure currentPressure = new BloodPressure(120, 80);
        String message = String.format("Warning, patient with id: %s, need help", info.getId());

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById(info.getId())).thenReturn(info);

        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        Mockito.doNothing().when(alertService).send(message);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkBloodPressure(info.getId(), currentPressure);

        Mockito.verify(alertService, Mockito.times(0)).send(message);
    }

    @Test
    public void checkBloodPressureNotNormalTest() {
        PatientInfo patientInfo = new PatientInfo("Иван", "Иванов", LocalDate.of(1995, 6, 11),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80)));
        PatientInfo info = new PatientInfo(UUID.randomUUID().toString(),
                patientInfo.getName(),
                patientInfo.getSurname(),
                patientInfo.getBirthday(),
                patientInfo.getHealthInfo());
        BloodPressure currentPressure = new BloodPressure(60, 100);
        String message = String.format("Warning, patient with id: %s, need help", info.getId());

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById(info.getId())).thenReturn(info);

        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        Mockito.doNothing().when(alertService).send(message);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkBloodPressure(info.getId(), currentPressure);

        Mockito.verify(alertService, Mockito.times(1)).send(message);
    }

    @Test
    public void checkTemperatureNormalTest() {
        PatientInfo patientInfo = new PatientInfo("Иван", "Иванов", LocalDate.of(1995, 6, 11),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80)));
        PatientInfo info = new PatientInfo(UUID.randomUUID().toString(),
                patientInfo.getName(),
                patientInfo.getSurname(),
                patientInfo.getBirthday(),
                patientInfo.getHealthInfo());
        BigDecimal currentTemperature = new BigDecimal("36.6");
        String message = String.format("Warning, patient with id: %s, need help", info.getId());

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById(info.getId())).thenReturn(info);

        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        Mockito.doNothing().when(alertService).send(message);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkTemperature(info.getId(), currentTemperature);

        Mockito.verify(alertService, Mockito.times(0)).send(message);
    }

    @Test
    public void checkTemperatureNotNormalTest() {
        PatientInfo patientInfo = new PatientInfo("Иван", "Иванов", LocalDate.of(1995, 6, 11),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80)));
        PatientInfo info = new PatientInfo(UUID.randomUUID().toString(),
                patientInfo.getName(),
                patientInfo.getSurname(),
                patientInfo.getBirthday(),
                patientInfo.getHealthInfo());
        BigDecimal currentTemperature = new BigDecimal("34.3");
        String message = String.format("Warning, patient with id: %s, need help", info.getId());

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById(info.getId())).thenReturn(info);

        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        Mockito.doNothing().when(alertService).send(message);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkTemperature(info.getId(), currentTemperature);

        Mockito.verify(alertService, Mockito.times(1)).send(message);
    }
}
