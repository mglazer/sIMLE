package jpl.simle.excel;

import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import jpl.simle.domain.Host;
import jpl.simle.domain.Lab;
import jpl.simle.domain.Application;
import jpl.simle.domain.HostApplication;
import jpl.simle.domain.Protocol;
import jpl.simle.service.LabManagerService;

import jxl.SheetSettings;
import jxl.format.BoldStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.web.servlet.view.document.AbstractJExcelView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LabExcelView extends AbstractJExcelView 
{
	private LabManagerService labManager_;
	private final static Logger logger_ = LoggerFactory.getLogger(LabExcelView.class);
	
	private static class ExcelHeader {
		private String name_;
		private int width_;
		public ExcelHeader(String name, int width)
		{
			name_ = name;
			width_ = width;
		}
		
		public String getName() { return name_; }
		public int getWidth() { return width_; }
	}
	private final static ExcelHeader[] HEADERS = {
		new ExcelHeader("PC", 5),
		new ExcelHeader("Host IPs", 10),
		new ExcelHeader("Application Name", 30),
		new ExcelHeader("Application Protocol Over WAN", 20),
		new ExcelHeader("Network Protocol (IP/TCP/UDP)", 20),
		new ExcelHeader("Network Ports/Protocol Numbers", 20),
		new ExcelHeader("Direction (In/Out/Both)", 20),
		new ExcelHeader("Destination IP", 20),
		new ExcelHeader("Notes", 40),
		new ExcelHeader("System/Host Name", 20),
		new ExcelHeader("DNS Names", 20)
	};

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			WritableWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		Lab lab = (Lab)model.get("lab");
		
		if ( lab == null )
		{
			throw new Exception("Could not find Lab instance in the model");
		}
			
		
		WritableSheet sheet = workbook.getSheet(lab.getName());
		if ( sheet == null )
		{
			sheet = workbook.createSheet(lab.getName(), 0);
		}
		
		// setup the settings
		SheetSettings settings = sheet.getSettings();
		settings.setZoomFactor(75);
		
		// write the header information
		WritableFont headerFont = new WritableFont(WritableFont.ARIAL, // arial font 
												   12, // 12 pt 
												   WritableFont.BOLD,	// bold
												   false,	// not italicized
												   UnderlineStyle.NO_UNDERLINE, // no underline
												   Colour.BLUE); // blue
		WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
		headerFormat.setWrap(true);
		
		WritableFont normalFont = new WritableFont(WritableFont.ARIAL, // arial font
												   10 // 10pt
												   );
		WritableCellFormat normalFormat = new WritableCellFormat(normalFont);
		normalFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		normalFormat.setWrap(true);
		
		WritableFont boldFont = new WritableFont(WritableFont.ARIAL, // arial font 
												 10, // 10pt
												 WritableFont.BOLD // bold
												 );
		WritableCellFormat middleAlignedBoldFormat = new WritableCellFormat(boldFont);
		middleAlignedBoldFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		middleAlignedBoldFormat.setWrap(true);
		
		
		for ( int i = 0; i < HEADERS.length; ++i )
		{
			Label header = new Label(i,0, HEADERS[i].getName(), headerFormat);
			sheet.addCell(header);
			sheet.setColumnView(i, HEADERS[i].getWidth());
		}
		
		// write the body
		int currentRow = 1;
		int currentHost = 1;
		for ( Host host : lab.getHosts() )
		{
			logger_.info("Generating " + host.getName() + " excel row");
			int applicationsSpan = 0;
			int currentCol = 0;
			// write the PC number
			Label pcLabel = new Label(0,currentRow, Integer.toString(currentHost), middleAlignedBoldFormat);
			sheet.addCell(pcLabel);
			
			// write the IP address
			Label ipLabel = new Label(1,currentRow, host.getAddressIP(), middleAlignedBoldFormat);
			sheet.addCell(ipLabel);
			
			// iterate over the applications
			List<HostApplication> hostApps = labManager_.findHostApplicationsForHost(host);
			int applicationRow = currentRow;
			
			for ( HostApplication ha : hostApps )
			{
				// start at 2 because of the IP and PC number columns
				Application app = ha.getApplication();
				logger_.info("Generating " + app.getName() + " excel row within " + host.getName());
				
				// write the application name
				Label appNameLabel = new Label(2,applicationRow, app.getName(), normalFormat);
				sheet.addCell(appNameLabel);
				// merge the application name
				
				sheet.mergeCells(2, applicationRow, 2, applicationRow+ Math.max(app.getProtocols().size()-1, 1));
				
				// there are multiple protocols per application, so start at that offset
				int protRow = applicationRow;
				
				// start at 3 because of the IP, PC and application columns
				int startCol = 3;
				for ( Protocol prot : app.getProtocols() )
				{
					// reset the column at the beginning
					currentCol = startCol;
					
					Label appProtocolLabel = new Label(currentCol,protRow, prot.getApplicationProtocol(), normalFormat);
					sheet.addCell(appProtocolLabel);
					currentCol++;
					
					Label networkProtocolLabel = new Label(currentCol,protRow, prot.getNetworkProtocol(), normalFormat);
					sheet.addCell(networkProtocolLabel);
					currentCol++;
					
					Label networkPortsLabel = new Label(currentCol,protRow, prot.getPorts(), normalFormat);
					sheet.addCell(networkPortsLabel);
					currentCol++;
					
					Label directionLabel = new Label(currentCol,protRow, prot.getDirection(), normalFormat);
					sheet.addCell(directionLabel);
					currentCol++;
					
					Label destinationIPLabel = new Label(currentCol,protRow, prot.getDestinationIP(), normalFormat);
					sheet.addCell(destinationIPLabel);
					currentCol++;
					
					Label notesLabel = new Label(currentCol,protRow, prot.getNotes(), normalFormat);
					sheet.addCell(notesLabel);
					currentCol++;
					
					applicationsSpan++;
					protRow++;
				}
				
				applicationRow = protRow;
			}
			
			
			// the row limits are inclusive, so subtract 1 from each
			logger_.info("Merging hostname label");
			Label hostnameLabel = new Label(currentCol,currentRow, host.getName(), normalFormat);
			sheet.addCell(hostnameLabel);
			
			if ( applicationsSpan > 0 ) 
			{
				sheet.mergeCells(currentCol, currentRow, currentCol, currentRow+applicationsSpan-1);
			}
			
			currentCol++;
				
			logger_.info("Merging dnsnames label");
			Label dnsnamesLabel = new Label(currentCol,currentRow, host.getDnsNames(), normalFormat);
			sheet.addCell(dnsnamesLabel);
			
			if ( applicationsSpan > 0 )
			{
				sheet.mergeCells(currentCol, currentRow, currentCol, currentRow+applicationsSpan-1);
			}
			currentCol++;
				
		
			if ( applicationsSpan > 0 )
			{
				// merge the PC column
				sheet.mergeCells(0, currentRow, 0, currentRow+applicationsSpan-1);
				// merge the Host IP column
				sheet.mergeCells(1, currentRow, 1, currentRow+applicationsSpan-1);
			}
			
			// make sure that applicationsSpan is at least 1, if the host doesn't have any 
			// applications then applicationsSpan would be 0
			applicationsSpan = Math.max(1, applicationsSpan);
			
			logger_.info("Applications span for " + host.getName() + " is " + applicationsSpan);
			currentRow = applicationRow + 1;
			currentHost++;
		} // end host iteration
	}
	
	@Autowired
	public void setLabManagerDAO(LabManagerService labManagerDAO)
	{
		labManager_ = labManagerDAO;
	}

}
