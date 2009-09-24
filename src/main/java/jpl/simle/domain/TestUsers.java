package jpl.simle.domain;

import org.springframework.beans.factory.annotation.Autowired;

import jpl.simle.dao.LabManagerDAO;

public class TestUsers 
{
	@Autowired
	private LabManagerDAO labManager;

	public void init()
	{
		SIMLEUser user = new SIMLEUser();
		user.setUsername("user");
		user.setPassword("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");
		user.setEnabled(true);
		
		Authority roleUser = new Authority();
		roleUser.setUsername("user");
		roleUser.setAuthority("ROLE_USER");
		
		user.persist();
		roleUser.persist();

		// create a new lab for testing
		Lab lab = new Lab();
		lab.setName("testlab");
		lab.setUsername(user.getUsername());
		lab.setLocation("somelocation");
		
		lab.persist();
		
		
		Application app = new Application();
		app.setName("TENA");
		app.setNotes("These are some TENA notes");
		
		app.persist();
	}
}
