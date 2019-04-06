package utils;


import javax.print.*;
import java.awt.*;
import java.awt.print.*;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;

public class PrinterHelper {

	public static void printBill(String bill){

		final PrinterJob printerJob = PrinterJob.getPrinterJob();

		Printable printable = (graphics, pageFormat, pageIndex) -> {

			Graphics2D graphics2D = (Graphics2D) graphics;
			graphics2D.translate(pageFormat.getImageableX(),pageFormat.getImageableY());

			graphics2D.setFont(new Font("Monospaced",Font.BOLD,10));

			String[] lines = bill.split(":");

			int verticalDistance = 15;

			for (int i = 0; i < lines.length; i++) {
				graphics2D.drawString(lines[i],5,verticalDistance);
				verticalDistance += 15;
			}

			if (pageIndex > 0) return NO_SUCH_PAGE;

			return PAGE_EXISTS;
		};

		PageFormat pageFormat = new PageFormat();
		pageFormat.setOrientation(PageFormat.PORTRAIT);

		Paper paper = pageFormat.getPaper();
		paper.setImageableArea(0,0,paper.getWidth(),paper.getHeight() - 2);

		printerJob.setPrintable(printable,pageFormat);

		try{
			printerJob.print();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public static void print(String printing_bill){
		PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
		if (printService != null) {
			Doc doc = new SimpleDoc("Hello", DocFlavor.STRING.TEXT_PLAIN, null);
			DocPrintJob printJob = printService.createPrintJob();
			try {
				printJob.print(doc, null);
			} catch (PrintException e) {
				e.printStackTrace();
			}
		}
	}

}
