package com.supplyrecord.supplyrecords.Pdf;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyItemDetail;
import com.supplyrecord.supplyrecords.Models.DataClasses.SupplyRecord;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PdfGenerator {
    public void generatePdf(SupplyRecord supplyRecord, ArrayList<SupplyItemDetail> supplyItemDetails, String path) {
        try {
            PdfWriter writer = new PdfWriter(path);
            PdfDocument pdfDocument = new PdfDocument(writer);
            pdfDocument.setDefaultPageSize(PageSize.A4);
            Document document = new Document(pdfDocument);

            // SUPPLY INFO TABLE
            Table supplyInfoTable = new Table(new float[] {80, 200, 200, 3});
            supplyInfoTable.setWidthPercent(100);

            supplyInfoTable.addCell(new Cell().add("Supply From:").setBold().setBorder(Border.NO_BORDER));
            supplyInfoTable.addCell(new Cell().add(supplyRecord.firmName()).setBorder(Border.NO_BORDER));
            supplyInfoTable.addCell(new Cell().add("Date:").setBold().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
            supplyInfoTable.addCell(new Cell().add(supplyRecord.formattedDate()).setBorder(Border.NO_BORDER));
            supplyInfoTable.addCell(new Cell().add("Supply To:").setBold().setBorder(Border.NO_BORDER));
            supplyInfoTable.addCell(new Cell().add(supplyRecord.partyName()).setBorder(Border.NO_BORDER));

            // ITEM DETAILS TABLE
            Table detailsTable = new Table(new float[] {0, 300, 80, 100, 100});

            detailsTable.addCell(new Cell().add("S.No.").setBold().setTextAlignment(TextAlignment.CENTER));
            detailsTable.addCell(new Cell().add("Item").setBold().setPaddingLeft(4));
            detailsTable.addCell(new Cell().add("Quantity").setBold().setTextAlignment(TextAlignment.RIGHT).setPaddingRight(4));
            detailsTable.addCell(new Cell().add("Price").setBold().setTextAlignment(TextAlignment.RIGHT).setPaddingRight(4));
            detailsTable.addCell(new Cell().add("Total").setBold().setTextAlignment(TextAlignment.RIGHT).setPaddingRight(4));

            int sno = 1;
            double subTotal = 0.0;
            for (SupplyItemDetail supplyItemDetail: supplyItemDetails) {
                double total = supplyItemDetail.qty() * supplyItemDetail.price();
                subTotal += total;
                detailsTable.addCell(new Cell().add(String.format("%d.", sno)).setTextAlignment(TextAlignment.CENTER));
                detailsTable.addCell(new Cell().add(supplyItemDetail.itemName()).setPaddingLeft(4));
                detailsTable.addCell(new Cell().add(String.valueOf(supplyItemDetail.qty())).setTextAlignment(TextAlignment.RIGHT).setPaddingRight(4));
                detailsTable.addCell(new Cell().add(String.valueOf(supplyItemDetail.price())).setTextAlignment(TextAlignment.RIGHT).setPaddingRight(4));
                detailsTable.addCell(new Cell().add(String.format("%.2f", total)).setTextAlignment(TextAlignment.RIGHT).setPaddingRight(4));
                sno++;
            }

            // SUB TOTAL
            Table subTotalTable = new Table(new float[] {520, 100});

            subTotalTable.addCell(new Cell().add("Sub Total:").setBold().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
            subTotalTable.addCell(new Cell().add(String.format("%.2f", subTotal)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));

            // ADDITIONAL COSTS
            Table additionalCosts = new Table(new float[] {80, 60, 50, 70, 60, 67.5f, 50, 100});

            additionalCosts.addCell(new Cell().add("Bilti Charge").setBold().setTextAlignment(TextAlignment.CENTER).setPaddingLeft(4).setPaddingRight(4));
            additionalCosts.addCell(new Cell().add("Bardana").setBold().setTextAlignment(TextAlignment.CENTER).setPaddingLeft(4).setPaddingRight(4));
            additionalCosts.addCell(new Cell().add("Labour").setBold().setTextAlignment(TextAlignment.CENTER).setPaddingLeft(4).setPaddingRight(4));
            additionalCosts.addCell(new Cell().add("Commission").setBold().setTextAlignment(TextAlignment.CENTER).setPaddingLeft(4).setPaddingRight(4));
            additionalCosts.addCell(new Cell().add("Postage").setBold().setTextAlignment(TextAlignment.CENTER).setPaddingLeft(4).setPaddingRight(4));
            additionalCosts.addCell(new Cell().add("Bazaar").setBold().setTextAlignment(TextAlignment.CENTER).setPaddingLeft(4).setPaddingRight(4));
            additionalCosts.addCell(new Cell().add("Other").setBold().setTextAlignment(TextAlignment.CENTER).setPaddingLeft(4).setPaddingRight(4));
            additionalCosts.addCell(new Cell().setBorder(Border.NO_BORDER));

            additionalCosts.addCell(new Cell().add(String.valueOf(supplyRecord.biltiCharge())).setTextAlignment(TextAlignment.CENTER));
            additionalCosts.addCell(new Cell().add(String.valueOf(supplyRecord.bardana())).setTextAlignment(TextAlignment.CENTER));
            additionalCosts.addCell(new Cell().add(String.valueOf(supplyRecord.labourCost())).setTextAlignment(TextAlignment.CENTER));
            additionalCosts.addCell(new Cell().add(String.valueOf(supplyRecord.commission())).setTextAlignment(TextAlignment.CENTER));
            additionalCosts.addCell(new Cell().add(String.valueOf(supplyRecord.postage())).setTextAlignment(TextAlignment.CENTER));
            additionalCosts.addCell(new Cell().add(String.valueOf(supplyRecord.bazaarCharges())).setTextAlignment(TextAlignment.CENTER));
            additionalCosts.addCell(new Cell().add(String.valueOf(supplyRecord.otherExpenses())).setTextAlignment(TextAlignment.CENTER));

            double expenses = supplyRecord.biltiCharge() + supplyRecord.bardana() +
                    supplyRecord.labourCost() + supplyRecord.commission() +
                    supplyRecord.postage() + supplyRecord.bazaarCharges() +
                    supplyRecord.otherExpenses();

            additionalCosts.addCell(new Cell().add(String.valueOf(expenses)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));

            // SEPARATOR
            SolidLine line = new SolidLine(0.5f);
            line.setColor(Color.GRAY);
            LineSeparator separator = new LineSeparator(line)
                    .setMarginTop(6)
                    .setWidth(150)
                    .setHorizontalAlignment(HorizontalAlignment.RIGHT);

            // TOTAL
            Table totalTable = new Table(new float[] {520, 100});

            totalTable.addCell(new Cell().add("Total:").setBold().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
            totalTable.addCell(new Cell().add(String.format("%.2f", subTotal + expenses)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));

            document.add(supplyInfoTable);
            document.add(detailsTable);
            document.add(subTotalTable);
            document.add(additionalCosts);
            document.add(separator);
            document.add(totalTable);

            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
