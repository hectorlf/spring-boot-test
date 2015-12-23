package hello.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="messages")
@SequenceGenerator(name="jpa_seq",allocationSize=1)
public class Message {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="jpa_seq")
	private Long id;

	private String subject;

	@Column(nullable=false)
	private String text;

	// getters & setters

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

}