package k23cnt3.day08.k23cnt3.day08.repository;
import k23cnt3.day08.k23cnt3.day08.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BookRepository extends JpaRepository<Book,Long> {

}