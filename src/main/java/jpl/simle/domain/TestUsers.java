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
		lab.setName("ITEC");
		lab.setUsername(user.getUsername());
		lab.setLatitude(new Double(34.1205));
		lab.setLongitude(new Double(119.1035));
		lab.setDomainName("domain.itec.com");
		
		lab.persist();
		
		Host host = new Host();
		lab.addHost(host);
		host.setAddressIP("23.54.43.98");
		host.setDnsNames("el.acetef.pax");
		host.setName("PC1");
	
		host.persist();
		
		Application app = new Application();
		app.setName("TENA");
		app.setNotes("These are some TENA notes");
		app.setAddedByUsername(user.getUsername());
		
		app.persist();
		
		Protocol protocol = new Protocol();
		app.addProtocol(protocol);
		protocol.setNetworkProtocol(Protocol.NetworkProtocol.UDP);
		protocol.setApplicationProtocol("TENA");
		protocol.setDirection(Protocol.Direction.BOTH);
		protocol.setPorts("55100,55300,55900");
		protocol.setNotes("Only ports that are a multiple of 100 are assigned in the range of ports");
		
		protocol.persist();
		
		protocol = new Protocol();
		app.addProtocol(protocol);
		protocol.setNetworkProtocol(Protocol.NetworkProtocol.TCP);
		protocol.setApplicationProtocol("Starship");
		protocol.setDirection(Protocol.Direction.BOTH);
		protocol.setPorts("55802");
		protocol.setNotes("Starship");
		
		protocol.persist();
		
		HostApplication ha = new HostApplication();
		ha.setApplication(app);
		ha.setHost(host);
		
		ha.persist();
		
	}
}
