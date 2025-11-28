package k23cnt3.day07.k23cnt3.day07.Repository;
import k23cnt3.day07.k23cnt3.day07.entity.Category;
import  org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CategoryRepository extends
        JpaRepository<Category, Long> {
}
