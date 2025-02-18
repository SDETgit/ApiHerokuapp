package pojo;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;
public class bookingDatesPojo {
	
	//	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
		    private String checkin;



		    private String checkout;

		    // Getters and Setters
		    public String getCheckin() { return checkin; }
		    public String getCheckout() { return checkout; }
		    public void setCheckin(String string) { this.checkin = string; }
		    public void setCheckout(String checkout) { this.checkout = checkout; }
	}
	
