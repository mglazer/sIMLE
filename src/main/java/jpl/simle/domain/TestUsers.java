package jpl.simle.domain;

import org.springframework.beans.factory.annotation.Autowired;

import jpl.simle.dao.LabManagerDAO;

import jpl.simle.domain.Protocol.Direction;
import jpl.simle.domain.Protocol.NetworkProtocol;

import java.util.Map;
import java.util.HashMap;

import jpl.simle.dao.AuthenticationDAO;

public class TestUsers 
{
	@Autowired
	private LabManagerDAO labManager;
	
	private Map<String,Application> applications = new HashMap<String,Application>();
	
	
	private final static String[][] HOSTS = { 
		// address IP, hostname, dnsnames, { applications }
		{ "76.131", "tcc.mugu-el", "el.tcc.mugu", "Environment Logger", "MultiRouter", "TENA", "Environment Logger (BSI)", "Exec Control", "OS Agent", "ClearPath" },
		{ "76.132", "tcc.mugu-tc", "tc.tcc.mugu", "TENA", "OS Agent", "ClearPath" },
		{ "76.133", "tcc.mugu.crsp", "crsp.tcc.mugu", "TENA", "OS Agent", "ClearPath" },
		{ "76.134", "tcc.mugu-tcm", "tcm.tcc.mugu", "TENA", "OS Agent", "ClearPath" },
		{ "76.135", "tcc.mugu-jmesssvr", "jimessvr.tcc.mugu", "TENA", "OS Agent", "ClearPath" },
		{ "76.136", "tcc.mugu.ie", "ie.tcc.mugu", "Environment Logger", "Environment Logger (BSI)", "Exec Control", "TENA", "OS Agent", "ClearPath" },
		{ "76.137", "tcc.mugu.jimes1", "jimes1.tcc.mugu", "TENA", "OS Agent", "ClearPath" },
		{ "76.138", "tcc.mugu.jimes2", "jimes2.tcc.mugu", "TENA", "OS Agent", "ClearPath" },
		{ "76.139", "tcc.mugu.jimes3", "jimes3.tcc.mugu", "TENA", "OS Agent", "ClearPath" },
		{ "76.140", "tcc.mugu.vmf", "vmf.tcc.mugu", "TENA", "OS Agent", "ClearPath" },
		{ "76.141", "tcc.mugu.ibs", "ibs.tcc.mugu", "TENA", "OS Agent", "ClearPath" },
		{ "76.142", "tcc.mugu.rdgw", "rdgw.tcc.mugu", "TENA", "OS Agent", "ClearPath" },
		{ "76.143", "tcc.mugu.jimm", "jimm.tcc.mugu", "TENA", "OS Agent", "ClearPath" },
		{ "76.144", "tcc.mugu.jsaf", "jsaf.tcc.mugu", "TENA", "OS Agent", "ClearPath" },
		{ "76.145", "tcc.mugu.ssegw2", "ssegw2.tcc.mugu", "TENA", "OS Agent", "ClearPath" },
		{ "76.146", "tcc.mugu.hpov", "hpov.tcc.mugu", "TENA", "OS Agent", "ClearPath" },
		{ "76.149", "tcc.mugu-jmps", "jmpcs.tcc.mugu", "TENA", "OS Agent", "ClearPath" },
		{ "76.150", "tcc.mugu-trt", "trt.tcc.mugu", "TENA", "OS Agent", "ClearPath" },
		{ "76.151", "tcc.mugu-video", "video.tcc.mugu", "TENA", "OS Agent", "ClearPath" },
		{ "76.152", "asti-1", "astl1.tcc.mugu", "TENA" },
		{ "76.153", "astl-2", "astl2.tcc.mugu", "TENA" },
		{ "76.183", "989-6001", "989-6001" }
	};

	public void init() throws Exception
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
		
		// temporarily swap in a mock authentication manager
		AuthenticationDAO backupAuthentication = labManager.getAuthenticationDAO();
		labManager.setAuthenticationDAO(new AuthenticationDAO() {
			public String getAuthenticatedUsername() {
				return "user";
			}
		});

		// create a new lab for testing
		Lab lab = new Lab();
		lab.setName("ITEC");
		lab.setUsername(user.getUsername());
		lab.setLatitude(new Double(34.1205));
		lab.setLongitude(new Double(119.1035));
		lab.setDomainName("domain.itec.com");
		
		lab.persist();
		
		addApplications();
		
		for ( String[] hostParams : HOSTS )
		{
			Host host = addHost(lab, hostParams);

			for ( int i = 3; i < hostParams.length; ++i )
			{
				Application app = applications.get(hostParams[i]);
				if ( app == null )
				{
					throw new Exception("Could not find application with name: " + hostParams[i]);
				}
				
				labManager.createHostApplicationLink(app, host);
			}
		}

		
		// restore the authentication
		labManager.setAuthenticationDAO(backupAuthentication);
	}
	
	private Host addHost(Lab lab, String[] hostParams)
	{
		Host host = new Host();
		lab.addHost(host);
		host.setAddressIP(hostParams[0]);
		host.setName(hostParams[1]);
		host.setDnsNames(hostParams[2]);

	
		host.persist();
		
		return host;
	}
	
	private void addApplications()
	{
		Application envLogger = new Application();
		envLogger.setName("Environment Logger");
		
		labManager.saveApplication(envLogger);
		applications.put(envLogger.getName(), envLogger);
		
		Protocol simpleJ = new Protocol();
		simpleJ.setApplicationProtocol("SimpleJ");
		simpleJ.setNetworkProtocol(NetworkProtocol.UDP);
		simpleJ.setPorts("35600");
		simpleJ.setDirection(Direction.BOTH);
		simpleJ.setDestinationIP("225.25.2.1-254");
		
		labManager.saveProtocol(envLogger, simpleJ);
		
		Protocol vmf = new Protocol();
		vmf.setApplicationProtocol("VMF");
		vmf.setNetworkProtocol(NetworkProtocol.UDP);
		vmf.setPorts("7000");
		vmf.setDirection(Direction.BOTH);
		vmf.setDestinationIP("Local traffic");
		
		labManager.saveProtocol(envLogger, vmf);
		
		Protocol usmtf = new Protocol();
		usmtf.setApplicationProtocol("USMTF");
		usmtf.setNetworkProtocol(NetworkProtocol.UDP);
		usmtf.setPorts("7005");
		usmtf.setDirection(Direction.BOTH);
		usmtf.setDestinationIP("Local traffic");
		
		labManager.saveProtocol(envLogger, usmtf);
		
		Protocol oth = new Protocol();
		oth.setApplicationProtocol("OTH");
		oth.setNetworkProtocol(NetworkProtocol.UDP);
		oth.setPorts("7010");
		oth.setDirection(Direction.BOTH);
		oth.setDestinationIP("225.25.2.1-254");
		
		labManager.saveProtocol(envLogger, oth);
		
		Application multiRouter = new Application();
		multiRouter.setName("MultiRouter");
		
		labManager.saveApplication(multiRouter);
		applications.put(multiRouter.getName(), multiRouter);
		
		simpleJ = new Protocol();
		simpleJ.setApplicationProtocol("SimpleJ");
		simpleJ.setNetworkProtocol(NetworkProtocol.UDP);
		simpleJ.setPorts("35600");
		simpleJ.setDirection(Direction.BOTH);
		simpleJ.setDestinationIP("225.25.2.1-254");
		
		labManager.saveProtocol(multiRouter, simpleJ);
		
		Application tena = new Application();
		tena.setName("TENA");
		
		labManager.saveApplication(tena);
		applications.put("TENA", tena);
		
		
		Protocol tenaTCP = new Protocol();
		tenaTCP.setApplicationProtocol("TENA TCP");
		tenaTCP.setNetworkProtocol(NetworkProtocol.TCP);
		tenaTCP.setPorts("55100,55300-55900/100");
		tenaTCP.setDirection(Direction.BOTH);
		tenaTCP.setDestinationIP("S.253.2");
		tenaTCP.setNotes("Only ports that are a multiple of 100 are assigned in the range of ports");
		
		labManager.saveProtocol(tena, tenaTCP);
		
		Protocol tenaUDP = new Protocol();
		tenaUDP.setApplicationProtocol("TENA UDP");
		tenaUDP.setNetworkProtocol(NetworkProtocol.UDP);
		tenaUDP.setPorts("55200,55600-55900/100");
		tenaUDP.setDirection(Direction.BOTH);
		tenaUDP.setDestinationIP("All TENA nodes");
		tenaTCP.setNotes("Only ports taht are a multiple of 100 are assigned in the range of ports");
		
		labManager.saveProtocol(tena, tenaUDP);
		
		Application environmentLoggerBsi = new Application();
		environmentLoggerBsi.setName("Environment Logger (BSI)");
		
		labManager.saveApplication(environmentLoggerBsi);
		applications.put(environmentLoggerBsi.getName(), environmentLoggerBsi);
		
		Protocol envTena = new Protocol();
		envTena.setApplicationProtocol("TENA");
		envTena.setNetworkProtocol(NetworkProtocol.TCP);
		envTena.setDirection(Direction.BOTH);
		envTena.setPorts("55800");
		envTena.setDestinationIP("S.253.2");
		
		labManager.saveProtocol(environmentLoggerBsi, envTena);
		
		Application execControl = new Application();
		execControl.setName("Exec Control");
		
		labManager.saveApplication(execControl);
		applications.put(execControl.getName(), execControl);
		
		Protocol execTena = new Protocol();
		execTena.setApplicationProtocol("TENA");
		execTena.setNetworkProtocol(NetworkProtocol.TCP);
		execTena.setDirection(Direction.BOTH);
		execTena.setPorts("55801");
		execTena.setDestinationIP("S.253.2");
		
		labManager.saveProtocol(execControl, execTena);
		
		Application osAgent = new Application();
		osAgent.setName("OS Agent");
		
		labManager.saveApplication(osAgent);
		applications.put("OS Agent", osAgent);
		
		Protocol osTimeSync = new Protocol();
		osTimeSync.setApplicationProtocol("Time Sync");
		osTimeSync.setNetworkProtocol(NetworkProtocol.UDP);
		osTimeSync.setPorts("44444");
		osTimeSync.setDirection(Direction.BOTH);
		osTimeSync.setDestinationIP("All OS Agent Nodes");
		
		labManager.saveProtocol(osAgent, osTimeSync);
		
		Protocol osTENA = new Protocol();
		osTENA.setApplicationProtocol("TENA");
		osTENA.setNetworkProtocol(NetworkProtocol.TCP);
		osTENA.setPorts("55817");
		osTENA.setDirection(Direction.BOTH);
		osTENA.setDestinationIP("All OS Agent Nodes");
		
		labManager.saveProtocol(osAgent, osTENA);
		
		Application clearPath = new Application();
		clearPath.setName("ClearPath");
		
		labManager.saveApplication(clearPath);
		applications.put("ClearPath", clearPath);
		
		Protocol clearpathUDP = new Protocol();
		clearpathUDP.setApplicationProtocol("Clearpath UDP");
		clearpathUDP.setNetworkProtocol(NetworkProtocol.UDP);
		clearpathUDP.setPorts("8084");
		clearpathUDP.setDirection(Direction.BOTH);
		clearpathUDP.setDestinationIP("All TENA Nodes");
		
		labManager.saveProtocol(clearPath, clearpathUDP);
		
		Protocol clearpathTCP = new Protocol();
		clearpathTCP.setApplicationProtocol("Clearpath TCP");
		clearpathTCP.setNetworkProtocol(NetworkProtocol.TCP);
		clearpathTCP.setPorts("80,389,443,1099,8087");
		clearpathTCP.setDirection(Direction.BOTH);
		clearpathTCP.setDestinationIP("All TENA Nodes");
		
		labManager.saveProtocol(clearPath, clearpathTCP);
	}
}
