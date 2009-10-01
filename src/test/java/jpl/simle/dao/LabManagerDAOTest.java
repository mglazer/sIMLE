package jpl.simle.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jpl.simle.domain.Lab;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"applicationContext.xml"})
public class LabManagerDAOTest
{
	private LabManagerDAO labManager_;

	@Autowired
	public void setLabManagerDAO(LabManagerDAO labManager)
	{
		labManager_ = labManager;
	}
	
	@Before
	public void setUp()
	{
		labManager_.setAuthenticationDAO(new AuthenticationDAO() {
			public String getAuthenticatedUsername() {
				return "user";
			}
		});
	}
	
	@Test
	public void shouldSaveLab()
	{
		Lab lab = new Lab();
		lab.setLatitude(new Double(24.54));
		lab.setLongitude(new Double(25.34));
		lab.setName("TEST_LAB");
		
		Lab returnedLab = labManager_.saveLab(lab);
		
		assertNotNull(returnedLab.getId());
		assertNotNull(returnedLab.getUsername());
	}
}
