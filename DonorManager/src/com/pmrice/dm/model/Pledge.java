package com.pmrice.dm.model;

import java.io.Serializable;
import java.sql.Date;

public class Pledge implements Serializable {

	private static final long serialVersionUID = 5800654426040039130L;
	
	private int id;
	private int donor_id;
	private String description;
	private String amount;
	private Date begin_date;
	private Date end_date;
	private boolean fulfilled;
	private boolean cancelled;
	private String note;
	
	public Pledge(int id, int donor_id, String description, String amount, Date begin_date, Date end_date,
			boolean fulfilled, boolean cancelled, String note) {
		super();
		this.id = id;
		this.donor_id = donor_id;
		this.description = description;
		this.amount = amount;
		this.begin_date = begin_date;
		this.end_date = end_date;
		this.fulfilled = fulfilled;
		this.cancelled = cancelled;
		this.note = note;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getDonor_id() {
		return donor_id;
	}
	public void setDonor_id(int donor_id) {
		this.donor_id = donor_id;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public Date getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(Date begin_date) {
		this.begin_date = begin_date;
	}
	
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	
	public boolean isFulfilled() {
		return fulfilled;
	}
	public void setFulfilled(boolean fulfilled) {
		this.fulfilled = fulfilled;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
}
