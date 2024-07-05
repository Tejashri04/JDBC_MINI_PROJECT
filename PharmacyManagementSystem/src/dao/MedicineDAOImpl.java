package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Medicine;

public class MedicineDAOImpl implements MedicineDAO {
	Connection con;
	
	@Override
	public void createMedicinesTable() throws SQLException {

		try (Statement st = con.createStatement()) {
			String sql = "CREATE TABLE IF NOT EXISTS Medicines (" + "id INT AUTO_INCREMENT PRIMARY KEY, "
					+ "name VARCHAR(30), " + "price FLOAT, " + "quantity INT)";

			st.executeUpdate(sql);
			System.out.println("Table 'Medicines' created successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Error creating table 'Medicines'", e);
		}

	}

	@Override
	public void addMedicines(Medicine m) throws SQLException {
		PreparedStatement pst = con
				.prepareStatement("INSERT INTO Medicines (id, name, price, quantity) VALUES (?, ?, ?, ?)");
		pst.setInt(1, m.getId());
		pst.setString(2, m.getName());
		pst.setFloat(3, m.getPrice());
		pst.setInt(4, m.getQuantity());

		int count = pst.executeUpdate();
		System.out.println(count + " record added to Medicines table successfully");
		pst.close();
	}

	@Override
	public Medicine findById(int id) throws SQLException {
		Medicine medicine = null;
		String sql = "SELECT * FROM Medicines WHERE id = ?";

		try (PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				int medicineId = rs.getInt("id");
				String name = rs.getString("name");
				float price = rs.getFloat("price");
				int quantity = rs.getInt("quantity");

				medicine = new Medicine(medicineId, name, price, quantity);
			}
		}

		return medicine;
	}

	@Override
	public List<Medicine> showAllMedicines() throws SQLException {
		Statement st = con.createStatement();
		List<Medicine> Medicines = new ArrayList<>();
		ResultSet rs = st.executeQuery("select * from Medicines");
		while (rs.next()) {
			Medicines.add(new Medicine(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4)));
		}
		return Medicines;
	}

	@Override
	public void connect() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/PharmacyDB";
		String user = "root";
		String pwd = "";

		con = DriverManager.getConnection(url, user, pwd);

	}

	@Override
	public void updateMedicines(Medicine m) throws SQLException {
		PreparedStatement pst = con
				.prepareStatement("update Medicines set name = ?,price = ? ,quantity = ? where id = ?");
		pst.setString(1, m.getName());
		pst.setFloat(2, m.getPrice());
		pst.setInt(3, m.getQuantity());
		pst.setInt(4, m.getId());

		int count = pst.executeUpdate();
		System.out.println(count + " record updated successfully");
		con.close();
		pst.close();

	}

	@Override
	public void deleteMedicines(int id) throws SQLException {
		PreparedStatement pst = con.prepareStatement("delete from Medicines where id = ?");
		pst.setInt(1, id);
		int count = pst.executeUpdate();
		System.out.println(count + " rows executed");

	}

	@Override
	public String findMedicineNameById(int id) throws SQLException {
		String procedureCall = "{call findMedicineNameById(?, ?)}";
		CallableStatement st = null;
		String medicineName = null;

		try {
			st = con.prepareCall(procedureCall);
			st.setInt(1, id);
			st.registerOutParameter(2, Types.VARCHAR);
			st.execute();

			medicineName = st.getString(2);

		} finally {
			if (st != null) {
				st.close();
			}
		}

		return medicineName;

	}


}
