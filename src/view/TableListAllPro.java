package view;

import model.dto.ProductResponDto;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;

public class TableListAllPro {

    // ANSI Color codes
    private static final String RESET = "\033[0m";
    private static final String BOLD = "\033[1m";
    private static final String CYAN = "\033[96m";
    private static final String GREEN = "\033[92m";
    private static final String YELLOW = "\033[93m";
    private static final String BLUE = "\033[94m";
    private static final String PURPLE = "\033[95m";
    private static final String WHITE = "\033[97m";
    public static void showTableListAllPro(List<ProductResponDto> productResponDtos) {
        int consoleWidth = 0;
        Table table = new Table(4, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);

        String[] header = {
                BOLD + YELLOW + "Product Name" + RESET,
                BOLD + GREEN + "Price" + RESET,
                BOLD + BLUE + "Quantity" + RESET,
                BOLD + PURPLE + "UUID" + RESET
        };

        for (String column : header) {
            table.addCell(column , new CellStyle(CellStyle.HorizontalAlign.center));
        }

        // Add data with colors
        for (int i = 0; i < productResponDtos.size(); i++) {
            ProductResponDto product = productResponDtos.get(i);

            // Alternate row colors
            String rowColor = (i % 2 == 0) ? WHITE : CYAN;

            table.addCell(rowColor + "" + product.pName() + RESET  ,new CellStyle(CellStyle.HorizontalAlign.center));
            table.addCell(GREEN + "" + product.price().toString() + RESET  ,new CellStyle(CellStyle.HorizontalAlign.center));
            table.addCell(BLUE + product.qty().toString() + " pcs" + RESET , new CellStyle(CellStyle.HorizontalAlign.center) );
            table.addCell(PURPLE + product.pUuid() + RESET , new CellStyle(CellStyle.HorizontalAlign.center) ) ;

        }

        // Render the table and center each line
        table.setColumnWidth(0 , 20 , 40 );
        table.setColumnWidth(1, 10 , 40 );
        table.setColumnWidth(2 , 10 , 40 );
        table.setColumnWidth(3 , 50 , 100 );

        String renderedTable = table.render();
        String[] tableLines = renderedTable.split("\n");
        for (String line : tableLines) {
            System.out.println(centerText(line, consoleWidth));
        }

        // Cool footer - centered
        System.out.println();
        String footerLine1 = CYAN + BOLD + "╔═══════════════════════════════════════════════════════════════════════════════╗" + RESET;
        String footerLine2 = CYAN + BOLD + "" + centerText("📊 Total Products: " + productResponDtos.size() + " items", 79) + "" + RESET;
        String footerLine3 = CYAN + BOLD + "╚═══════════════════════════════════════════════════════════════════════════════╝" + RESET;

        System.out.println(centerText(footerLine1, consoleWidth));
        System.out.println(centerText(footerLine2, consoleWidth));
        System.out.println(centerText(footerLine3, consoleWidth));
        System.out.println();
    }

    private static String centerText(String text, int width) {
        // Remove ANSI codes for length calculation
        String plainText = text.replaceAll("\033\\[[0-9;]*m", "");
        if (plainText.length() >= width) return text;
        int padding = (width - plainText.length()) / 2;
        return " ".repeat(padding) + text + " ".repeat(width - plainText.length() - padding);
    }
}