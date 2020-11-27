package beans_el;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class HomeScreen implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ####
	@Inject
	private Conversation conversation;

	public void begin() {
		if (this.conversation.isTransient()) {
			this.conversation.begin();
		}
	}

	public void end() {
		if (!this.conversation.isTransient()) {
			this.conversation.end();
		}
	}
	// /####

	// ####
	private String tab = "";

	public String getTab() {
		return tab;
	}

	public void tabToStudentManagement() {
		tab = "StudentManagement";
		this.begin();

	}

	public void studentManagementEnd() {
		tab = "";
		this.end();

	}

	public void tabToClassManagement() {
		tab = "ClassManagement";
		this.begin();
	}

	public void classManagementEnd() {
		tab = "";
		this.end();
	}
	// /####

	// ####
	@PostConstruct
	public void print() {
		System.out.println("PostConstruct " + this);
	}

	@PreDestroy
	public void print1() {
		System.out.println("PreDestroy " + this);
	}
	// /####
}
