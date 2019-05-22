package test;

import datamodel.Repository;

public class InvoiceTest {

	public static void main(String[] args) {
		Repository repository = Repository.getInstance();
		for (int i = 1 ;i <= 10; i++) {
			repository.saveInvoiceNumberToFile(i);
		}
		System.out.println(repository.getInvoiceNumber());
	}
}
