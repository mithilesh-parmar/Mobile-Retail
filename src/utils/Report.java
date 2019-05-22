package utils;

import datamodel.Organization;
import datamodel.Sale;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Report {

	private static JasperViewer viewer;
	private static JasperPrint print;
	private static JasperReport report;
	private static String SOURCE = "src/res/Invoice.jrxml";

	private static void createReport(String source, Map map){

		try {
			 report = JasperCompileManager.compileReport(source);
			 List list = new ArrayList<>();
			 list.add(map);

			 JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(list);
			 print = JasperFillManager.fillReport(report,map, beanCollectionDataSource);


		} catch (JRException e) {
			e.printStackTrace();
		}

	}


	/**
	 * print invoice with details
	 * @param sale
	 */

	public static void printInvoice(Sale sale){

		Map map = new HashMap();

		sale.setOrganization(getOrganization());

		setCompanyDetails(map,sale);

		setCustomerDetails(map,sale);

		setPaymentDetails(map,sale);

		setTableDetails(map,sale);

		Report.createReport(SOURCE,map);

		Report.printReport();
	}

	private static void setCompanyDetails(Map map,Sale sale){
		map.put("company_name",sale.getOrganization().getName());
		map.put("company_street_address",sale.getOrganization().getAddress());
		map.put("company_city_zip",sale.getOrganization().getProprietorName());
		map.put("company_phone_number",String.valueOf(sale.getOrganization().getContactNumber()));
		map.put("company_policy",sale.getOrganization().getPolicy());
	}

	private static void setCustomerDetails(Map map,Sale sale){
		map.put("customer_name",sale.getName());
		map.put("customer_address",sale.getAddress());
		map.put("customer_phone_number",String.valueOf(sale.getMobileNumber()));
	}

	private static void setPaymentDetails(Map map,Sale sale){
		map.put("invoice_number",sale.getInvoiceNumber());
		map.put("payment_mode",sale.getPaymentMode());
		map.put("total_amount",sale.getTotal_amount());
		map.put("cgst_amount",sale.getCgst_amount());
		map.put("igst_amount",sale.getIgst_amount());
		map.put("sgst_amount",sale.getSgst_amount());

	}

	private static void setTableDetails(Map map,Sale sale){
		JRBeanCollectionDataSource itemJrBean = new JRBeanCollectionDataSource(sale.getOrderList());

		map.put("ItemDataSource",itemJrBean);
	}

	public static Organization getOrganization(){
		Organization org = Organization.getInstance();
		org.setName("JAY HIND MOBILE");
		org.setContactNumber("9033570406 \n Email: Dilipgehlot90@gmail.com");
		org.setGstNumber("24EJWPK6136G1ZT");
		org.setAddress("17,JWALIN PARK SOCIETY, OPP. EVEREST WAY BRIDGE, RABARI VASHAD, ODHSAV, AHEMDABAD, 382415 ");
		org.setProprietorName("DILIP MALI");
		org.setPolicy("\n GSTIN: "+ org.getGstNumber()+"\n ALL TYPE OF MOBILE ACCESSORIES \n COMPUTERISED MOBILE REPAIRING \n RETURNS WITHIN 7 DAYS ARE ONLY ACCEPTED.");

		return org;
	}

	public static void printReport(){
		try {
			JasperPrintManager.printPageToImage(print,print.getPages().size()-1,1);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	public static void showReport(){
		viewer = new JasperViewer(print);
		viewer.setVisible(true);
	}

}
