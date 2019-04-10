package utils;


import datamodel.*;
import javafx.scene.Node;

import javax.print.*;
import java.awt.*;
import java.awt.print.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;

public class PrinterHelper {

	public static void printBill(List<Order> pr){

		final PrinterJob printerJob = PrinterJob.getPrinterJob();


		Printable printable = (graphics, pageFormat, pageIndex) -> {

			Graphics2D graphics2D = (Graphics2D) graphics;
			graphics2D.translate(pageFormat.getImageableX(),pageFormat.getImageableY());

			graphics2D.setFont(new Font("Monospaced",Font.BOLD,10));


			drawBill(graphics2D,pageFormat,pr);


			if (pageIndex > 0) return NO_SUCH_PAGE;

			return PAGE_EXISTS;
		};


		PageFormat pageFormat = new PageFormat();
		pageFormat.setOrientation(PageFormat.PORTRAIT);

		Paper paper = pageFormat.getPaper();
		paper.setImageableArea(0,0,paper.getWidth(),paper.getHeight() - 2);



		printerJob.setPrintable(printable,pageFormat);

		try{
			if (printerJob.printDialog())
			printerJob.print();

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private static void drawBill(Graphics2D graphics2D, PageFormat pageFormat,List<Order>pr) {


		Paper paper = pageFormat.getPaper();

		Organization organization = Organization.getInstance();
		organization.setName("My Mobile Store");
		organization.setContactNumber("8561057510");
		organization.setProprietorName("Mithilesh Parmar");
		organization.setAddress("JECRC University");
		organization.setPolicy("No returns accepted");
		organization.setGstNumber("12097983619");


		Customer customer = new Customer("Mithilesh parmar","7725900504","Sanchore");
		ArrayList<Order> orderList = new ArrayList<>();
		orderList.add(new Order(new Product("Apple","iPhone 6 Plus 64gb","23123123",45000f)));
		Sale sale = new Sale(customer,pr,7,PaymentMode.CASH);

		float yDirection = 15.0f;
		float xDirection = 5f;
		graphics2D.drawString("Tax Invoice", (int) (pageFormat.getImageableX()/2),yDirection);

		yDirection += 15f;

		// print organization details
		graphics2D.drawString(organization.getProprietorName(),xDirection,yDirection);
		yDirection += 15f;
		graphics2D.drawString(organization.getContactNumber(),xDirection,yDirection);
		yDirection += 15f;
		graphics2D.drawString(organization.getName(),xDirection,yDirection);
		yDirection += 15f;
		graphics2D.drawString(organization.getAddress(),xDirection,yDirection);
		yDirection += 15f;
		graphics2D.drawString(organization.getGstNumber(),xDirection,yDirection);
		yDirection += 15f;
		graphics2D.drawLine((int)xDirection,(int)yDirection,(int)paper.getWidth(),(int)yDirection);
		yDirection += 15f;

		// customer detail
		graphics2D.drawString(sale.getName(),xDirection,yDirection);
		yDirection +=15f;
		graphics2D.drawString(sale.getMobileNumber(),xDirection,yDirection);
		yDirection +=15f;
		graphics2D.drawString(sale.getAddress(),xDirection,yDirection);
		yDirection += 15f;
		graphics2D.drawLine((int)xDirection,(int)yDirection,(int)paper.getWidth(),(int)yDirection);

		// invoice detail

		yDirection = 15;
		yDirection += 15;
		xDirection = (float) (paper.getWidth()/2);
		graphics2D.drawString("Date: "+sale.getDate().toString(),xDirection,yDirection);
		yDirection += 15;
		graphics2D.drawString("Invoice Number: "+String.valueOf(sale.getInvoiceNumber()),xDirection,yDirection);
		yDirection += 15;
		graphics2D.drawString("Mode: "+sale.getPaymentMode(),xDirection,yDirection);

		yDirection +=15;

	}




}
