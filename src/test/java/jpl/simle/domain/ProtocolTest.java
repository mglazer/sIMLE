package jpl.simle.domain;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"applicationContext.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class ProtocolTest 
{
	private Application application_;
	
	@Before
	public void setUp()
	{
		application_ = new Application();
		application_.setName("TEST_APPLICATION");
		application_.setNotes("TEST NOTES");
		application_.persist();
	}

	@Test
	public void shouldSaveProtocol()
	{
		Protocol prot = new Protocol();
		prot.setApplication(application_);
		prot.setDestinationIP("25.234.54.23");
		prot.setDirection(Protocol.Direction.IN);
		prot.setNetworkProtocol(Protocol.NetworkProtocol.TCP);
		prot.setNotes("Some test notes");
		prot.setPorts("123,234,45-65/5");
		
		prot.persist();
		assertNotNull(prot.getId());
	}
	
	@Test
	public void shouldGeneratePortsList()
	{
		Protocol prot = new Protocol();
		prot.setPorts("123,234,45-65/5,20-25,300-400/10");
		
		List<Integer> portList = prot.getPortsList();
		
		int[] expectedPorts = new int[] {
				123, 234, 
				45,50,55,60,65,
				20, 21, 22, 23, 24, 25,
				300,310,320,330,340,350,360,370,380,390,400
		};
		
		assertEquals("We expect the generated port list to contain the specified list.",
					expectedPorts.length, portList.size());
		for ( int i = 0; i < expectedPorts.length; ++i )
		{
			assertTrue(portList.contains(new Integer(expectedPorts[i])));
		}
	}
}
