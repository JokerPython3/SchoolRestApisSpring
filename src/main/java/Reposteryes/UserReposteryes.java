package Reposteryes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import model.User;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UserReposteryes extends JpaRepository<User,Long>{
	Optional<User>findByUsername(String username); 
	Optional<User>findByEmail(String email); 
	List<User> findByClassId(Long classId);
	List<User>  findByClassABC(String classABC);
	

}
