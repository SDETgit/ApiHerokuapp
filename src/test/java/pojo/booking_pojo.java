package pojo;

public class booking_pojo {

	private String firstname;
	private String lastname;
	private double totalprice;
	private boolean depositpaid;
	private String additionalneeds;
	bookingDatesPojo bookingdates;
	public bookingDatesPojo getBookingdates() {
		return bookingdates;
	}
	public void setBookingdates(bookingDatesPojo bookingdates) {
		this.bookingdates = bookingdates;
	}
	public String getFirstname() {
		return firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public double getTotalprice() {
		return totalprice;
	}
	public boolean getDepositpaid() {
		return depositpaid;
	}
	public String getAdditionalneeds() {
		return additionalneeds;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public void setTotalprice(double totalprice) {
		this.totalprice = totalprice;
	}
	public void setDepositpaid(boolean depositpaid) {
		this.depositpaid = depositpaid;
	}
	public void setAdditionalneeds(String additionalneeds) {
		this.additionalneeds = additionalneeds;
	}
	
	public static class bookingDatesPojo {

		private String checkin;

		private String checkout;

		// Getters and Setters
		public String getCheckin() {
			return checkin;
		}

		public String getCheckout() {
			return checkout;
		}

		public void setCheckin(String string) {
			this.checkin = string;
		}

		public void setCheckout(String checkout) {
			this.checkout = checkout;
		}
	}
	
}
