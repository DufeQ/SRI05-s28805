package sri05.soap.repo;

import org.springframework.data.repository.CrudRepository;
import sri05.soap.model.Student;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long> {
    @Override
    List<Student> findAll();
}
