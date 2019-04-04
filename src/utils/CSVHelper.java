package utils;

import datamodel.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CSVHelper {
	private String seperator = ",";
	private String productsFilePath;
	private String invoiceFilePath;

	public CSVHelper(String path) {
		this.productsFilePath = path;
	}

	public List<Product> readFile(){
		List<Product> products = new ArrayList<>();
		BufferedReader bufferedReader = null;
		try {
			File file = new File(productsFilePath);
			if (!file.exists()) file.createNewFile();
			bufferedReader = new BufferedReader(new FileReader(productsFilePath));
			String line ;
			while((line = bufferedReader.readLine()) != null){
				Product p = new Product();
				String[] words = line.split(seperator);
				p.setId(UUID.fromString(words[0]));
				p.setImeiNumber(words[1]);
				p.setManufacturer(words[2]);
				p.setModel(words[3]);
				p.setRate(Float.parseFloat(words[4]));
				products.add(p);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return products;
	}

	public void saveToFile(Product p){
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new FileWriter(productsFilePath,true));
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder
					.append(String.valueOf(p.getId())).append(",")
					.append(p.getImeiNumber()).append(",")
					.append(p.getManufacturer()).append(",")
					.append(p.getModel()).append(",")
					.append(String.valueOf(p.getRate()))
					.append("\n");
			writer.write(stringBuilder.toString());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void saveInvoiceToFile(Invoice invoice){
		Customer customer = invoice.getCustomer();
		Organization organization = Organization.getInstance();
		List<Order> orderList = invoice.getOrderList();
		UUID uuid = invoice.getId();
		int invoiceNumber = invoice.getInvoiceNumber();
		LocalDate date = invoice.getDate();
		PaymentMode mode = invoice.getPaymentMode();

	}

}