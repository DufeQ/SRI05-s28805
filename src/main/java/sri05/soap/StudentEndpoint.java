package sri05.soap;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import sri05.soap.model.Student;
import sri05.soap.repo.StudentRepository;
import sri05.soap.soap.SoapWSConfig;
import sri05.soap.students.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Endpoint
@RequiredArgsConstructor
public class StudentEndpoint {

    private final StudentRepository studentRepository;

    @PayloadRoot(namespace = SoapWSConfig.STUDENT_NAMESPACE, localPart = "getStudentRequest")
    @ResponsePayload
    public GetStudentsResponse getStudents(@RequestPayload GetStudentsRequest req){
        List<Student> allStud = studentRepository.findAll();
        List<StudentDto> studentDtos = allStud.stream().map(this::convertToDto).toList();
        GetStudentsResponse res = new GetStudentsResponse();
        res.getStudents().addAll(studentDtos);
        return res;
    }

    @PayloadRoot(namespace = SoapWSConfig.STUDENT_NAMESPACE, localPart = "getStudentByIdRequest")
    @ResponsePayload
    public GetStudentsByIdResponse getStudentsById(@RequestPayload GetStudentsByIdRequest req){
        long id = req.getStudentsId().longValue();
        Optional<Student> byId = studentRepository.findById(id);
        GetStudentsByIdResponse res = new GetStudentsByIdResponse();
        res.setStudents(convertToDto(byId.orElse(null)));
        return res;
    }

    @PayloadRoot(namespace = SoapWSConfig.STUDENT_NAMESPACE, localPart = "addStudentRequest")
    @ResponsePayload
    public AddStudentResponse addStudent(@RequestPayload AddStudentRequest req){
        Student student = convertToEntity(req.getStudents());
        Long id = studentRepository.save(student).getId();
        AddStudentResponse res = new AddStudentResponse();
        res.setStudentId(new BigDecimal(id));
        return res;
    }

    private StudentDto convertToDto(Student s){
        if (s == null)
            return null;
        StudentDto dto = new StudentDto();
        dto.setId(BigDecimal.valueOf(s.getId()));
        dto.setImie(s.getImie());
        dto.setNazwisko(s.getNazwisko());
        dto.setNrIndeksu(s.getNrIndeksu());
        return dto;
    }

    private Student convertToEntity(StudentDto dto){
        return Student.builder()
                .id(dto.getId() != null ? dto.getId().longValue() : null)
                .imie(dto.getImie())
                .nazwisko(dto.getNazwisko())
                .nrIndeksu(dto.getNrIndeksu())
                .build();
    }



}
