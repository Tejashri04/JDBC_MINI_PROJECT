package dao;

import java.sql.SQLException;
import java.util.List;

import model.Medicine;


public interface MedicineDAO {
	public void createMedicinesTable() throws SQLException;
	
	public void addMedicines(Medicine m) throws SQLException;
	Medicine findById(int id) throws SQLException;
	public List<Medicine> showAllMedicines() throws SQLException;
	public void connect() throws SQLException;
	public void updateMedicines(Medicine m) throws SQLException;
	public void deleteMedicines(int id) throws SQLException;
	    
	String findMedicineNameById(int id) throws SQLException;
}
