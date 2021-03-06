package com2002.team021;

import java.util.Date;
import java.sql.SQLException;

public class Patient {
	
	private int id = 0;
	private String title;
	private String forename;
	private String surname;
	private Date dob;
	private int phone;
	private Address address;
	private HealthcarePlan subscription;
	private int checkUps;
	private int hygieneVisits;
	private int repairs;
	
	public Patient (int patientID) throws SQLException {
		try {
			Patient dbPatient = new Query().getPatient(patientID);

			this.id = dbPatient.getId();
			this.title = dbPatient.getTitle();
			this.forename = dbPatient.getForename();
			this.surname = dbPatient.getSurname();
			this.dob = dbPatient.getDob();
			this.phone = dbPatient.getPhone();
			this.address = dbPatient.getAddress();
			this.subscription = dbPatient.getSubscription();
			this.checkUps = dbPatient.getCheckUps();
			this.hygieneVisits = dbPatient.getHygieneVisits();
			this.repairs = dbPatient.getRepairs();
			
		} catch (SQLException e) {
			throw new SQLException("couldnt get patient: " + patientID + "\n" + e);
			
		}
		
	}
	
	public Patient (String title, String forename, String surname, Date dob, int phone, String houseNumber, String postcode, String subscription) throws SQLException {
		this(0, title, forename, surname, dob, phone, houseNumber, postcode, subscription, 0, 0, 0);
		
	}
	
	public Patient (String forename, String surname, Date dob, int phone, String houseNumber, String postcode, String subscription) throws SQLException {
		this(0, "", forename, surname, dob, phone, houseNumber, postcode, subscription, 0, 0, 0);
		
	}
	
	public Patient (int id, String title, String forename, String surname, Date dob, int phone, String houseNumber, String postcode, String subscription) throws SQLException {
		this(id, title, forename, surname, dob, phone, houseNumber, postcode, subscription, 0, 0, 0);
		
	}
	
	public Patient (int id, String title, String forename, String surname, Date dob, int phone, String houseNumber, String postcode, String subscription, int checkUps, int hygieneVisits, int repairs) throws SQLException {
		this.id = id;
		this.title = title;
		this.forename = forename;
		this.surname = surname;
		this.dob = dob;
		this.phone = phone;
		this.checkUps = checkUps;
		this.hygieneVisits = hygieneVisits;
		this.repairs = repairs;
		
		try {
			this.address = new Address(houseNumber, postcode);
			
		} catch (SQLException e) {
			throw new SQLException("couldnt get address: " + houseNumber + ", " + postcode + "\n" + e);
		}
		
		try {
			if (subscription != null && "" != subscription) this.subscription = new HealthcarePlan(subscription);
			
		} catch (SQLException e) {
			throw new SQLException("Subscription not in database: " + subscription, e);
		}
	}
	
	// getters
	public int getId () { return this.id; }
	public String getTitle () { return this.title; }
	public String getForename () { return this.forename; }
	public String getSurname () { return this.surname; }
	public Date getDob () { return this.dob; }
	public int getPhone () { return this.phone; }
	public HealthcarePlan getSubscription () { return this.subscription; }
	public HealthcarePlan getHealthcarePlan () { return this.subscription; }
	public int getCheckUps () { return this.checkUps; }
	public int getHygieneVisits () { return this.hygieneVisits; }
	public int getRepairs () { return this.repairs; }
	
	public String getHouseNumber () { return this.address.getHouseNumber(); }
	public String getPostcode () { return this.address.getPostcode(); }
	
	public String getName () {
		return this.getForename() + " " + this.getSurname();
	}
	
	public Address getAddress () throws SQLException {
		try {
			return new Query().getPatientAddress(this.id);
			
		} catch (SQLException e) {
			throw new SQLException(e);
			
		}
	}
	
	private boolean setHCP () throws SQLException {
		try {
			return new Query().updatePatientHCP(this.getId(), this.getCheckUps(), this.getHygieneVisits(), this.getRepairs());
			
		} catch (SQLException e) {
			throw new SQLException("Couldnt update HCP values for patient \n" + e);
		}
		
	}
	
	public boolean incrementCheckUps () throws SQLException {
		if (this.getHealthcarePlan() != null && this.getHealthcarePlan().getCheckUps() - this.getCheckUps() > 0) {
			System.out.println("incremeting chkups");
			this.checkUps += 1;
			this.setHCP();
			return true;
			
		} else {
			return false;
			
		}
	}
	
	public boolean incrementHygieneVisits () throws SQLException {
		if (this.getHealthcarePlan() != null && this.getHealthcarePlan().getHygieneVisits() - this.getHygieneVisits() > 0) {
			System.out.println("incremeting hygvsts");
			this.hygieneVisits += 1;
			this.setHCP();
			return true;
			
		} else {
			return false;
			
		}
	}
	
	public boolean incrementRepairs () throws SQLException {
		if (this.getHealthcarePlan() != null && this.getHealthcarePlan().getRepairs() - this.getRepairs() > 0) {
			System.out.println("incremeting rparis");
			this.repairs += 1;
			this.setHCP();
			return true;
			
		} else {
			return false;
			
		}
	}
	
	public String toString () {
		return this.id + ": " + this.forename + " " + this.surname;
	}

	public static void main (String args[]) {
		try {
			Patient patient = new Patient(34);
			System.out.println(patient);
			System.out.println(patient.getAddress());

		} catch (SQLException e) {
			System.out.println("Couldn't find patient");
			e.printStackTrace();

		}
	}
	
}
