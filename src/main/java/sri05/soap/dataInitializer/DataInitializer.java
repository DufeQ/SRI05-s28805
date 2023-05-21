package sri05.soap.dataInitializer;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import sri05.soap.model.Student;
import sri05.soap.repo.StudentRepository;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(DataInitializer.class);

    private final StudentRepository studentRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        initData();
    }

    private void initData(){
        Student s1 = Student.builder()
                .imie("Bartosz")
                .nazwisko("Jakubiak")
                .nrIndeksu("s28805")
                .build();
        Student s2 = Student.builder()
                .imie("Jan")
                .nazwisko("Kowalski")
                .nrIndeksu("s21111")
                .build();
        Student s3 = Student.builder()
                .imie("Janusz")
                .nazwisko("Tracz")
                .nrIndeksu("s666")
                .build();

        studentRepository.saveAll(Arrays.asList(s1, s2, s3));
        LOG.info("Data initialized");

    }
}
