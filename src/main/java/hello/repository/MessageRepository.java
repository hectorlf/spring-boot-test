package hello.repository;

import hello.model.Message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

	@Query("select m from Message m where m.subject like ?1%")
	List<Message> findBySubject(String subject);

}