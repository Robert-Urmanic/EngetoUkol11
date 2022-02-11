import java.math.BigDecimal;
import java.sql.*;

public class Main {
    public static void main(String[] args) {

        Item item = new Item(1,"partNo1", "serialNo1", "soucastka1", "Fajna soucastka.", 0, new BigDecimal(12.5));
        Item item2 = new Item(2,"partNo2", "serialNo2", "soucastka2", "Jeste fajnejsi soucastka.", 5, new BigDecimal(12.5));
        Item item3 = new Item(3,"partNo3", "serialNo3", "soucastka3", "Ale uz uplne nejfajnejsi soucastka.", 10, new BigDecimal(30));
        System.out.println("Nakrmit databázi: ");
        item.saveItem(item);
        item.saveItem(item2);
        item.saveItem(item3);
        System.out.println("Zobrazení součástky s ID 1: ");
        System.out.println(item.loadItemById(1));
        System.out.println("Zobrazení všech součástek, které jsou na skladě: ");
        System.out.println(item.loadAllAvailableItems());
        System.out.println("Smazání součástek, které nejsou na skladě: ");
        item.deleteAllOutOfStockItems();
        System.out.println("Zobrazení všech součástek, které jsou na skladě: ");
        System.out.println(item.loadAllAvailableItems());
        System.out.println("Změna ceny součástky s ID 2: ");
        item.updatePrice(2, new BigDecimal(54.6));
        System.out.println("Zobrazení všech součástek, které jsou na skladě: ");
        System.out.println(item.loadAllAvailableItems());

    }
}
