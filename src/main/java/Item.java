import javax.swing.plaf.nimbus.State;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Item implements GoodsMethods{

    private Integer id;

    private String partNo;

    private String serialNo;

    private String name;

    private String description;

    private Integer numberInStock;

    private BigDecimal price;

    public Item(Integer id, String partNo, String serialNo, String name, String description, Integer numberInStock, BigDecimal price) {
        this.id = id;
        this.partNo = partNo;
        this.serialNo = serialNo;
        this.name = name;
        this.description = description;
        this.numberInStock = numberInStock;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumberInStock() {
        return numberInStock;
    }

    public void setNumberInStock(Integer numberInStock) {
        this.numberInStock = numberInStock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
//----------------------------------------------------------------------------------------------------------------------
    @Override
    public Item loadItemById(Integer id) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ukol11", "root", "heslo")) {
            String prikazSelect =   "SELECT * FROM item " +
                                    "WHERE id =" + id + ";";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(prikazSelect);

            while(resultSet.next()){
                return new Item(resultSet.getInt("id"), resultSet.getString("partNo"), resultSet.getString("serialNo"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getInt("numberInStock"), resultSet.getBigDecimal("price"));
            }
            // asi by bylo lepší použít něco jiného, než while

            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteAllOutOfStockItems() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ukol11", "root", "heslo")) {

            String prikazDelete =   "DELETE from item " +
                                    "WHERE numberInStock = 0";

            PreparedStatement preparedStatement = connection.prepareStatement(prikazDelete);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Item> loadAllAvailableItems() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ukol11", "root", "heslo")) {

        String prikazSelect =   "SELECT * FROM item " +
                                "WHERE numberInStock NOT IN (0)";

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(prikazSelect);

        List itemsInStock = new ArrayList();

        while(resultSet.next()){
            itemsInStock.add(new Item(resultSet.getInt("id"), resultSet.getString("partNo"), resultSet.getString("serialNo"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getInt("numberInStock"), resultSet.getBigDecimal("price")));
        }

            return itemsInStock;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveItem(Item item) {
        //insert
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ukol11", "root", "heslo")) {

            String preparedStatementInsert = "INSERT INTO item (id, partNo, serialNo, name, description, numberInStock, price) VALUES (?,?,?,?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(preparedStatementInsert);

            preparedStatement.setInt(1, item.getId());
            preparedStatement.setString(2, item.getPartNo());
            preparedStatement.setString(3, item.getSerialNo());
            preparedStatement.setString(4, item.getName());
            preparedStatement.setString(5, item.getDescription());
            preparedStatement.setInt(6, item.getNumberInStock());
            preparedStatement.setBigDecimal(7, item.getPrice());

            preparedStatement.execute();



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePrice(Integer id, BigDecimal newPrice) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ukol11", "root", "heslo")) {


            String prikazUpdate =   "UPDATE item SET " +
                                    "price = ?" +
                                    " WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(prikazUpdate);

            preparedStatement.setBigDecimal(1, newPrice);
            preparedStatement.setInt(2, id);

            preparedStatement.execute();



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", partNo='" + partNo + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", numberInStock=" + numberInStock +
                ", price=" + price +
                '}' + "\n";
    }
}