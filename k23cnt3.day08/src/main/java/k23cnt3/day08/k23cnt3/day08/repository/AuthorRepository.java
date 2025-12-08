package k23cnt3.day08.k23cnt3.day08.repository;
import k23cnt3.day08.k23cnt3.day08.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
