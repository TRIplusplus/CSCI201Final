package recurrent_CSCI201L_Project;

import java.sql.Date;

public class Item {
	private String lender;
	private String renter;
	private String title;
	private String image;
	private Date startdate;
	private Date enddate;
	private String description;
	private double price;
	private Double xcoord;
	private Double ycoord;
	private int id;
	
	public Item(String lender, String image, String title, Date startdate, Date enddate, 
			String description, double price, Double xcoord, Double ycoord) {
		this.lender = lender;
		this.renter = null;
		this.title = title;
		this.image = image;
		this.startdate = startdate;
		this.enddate = enddate;
		this.description = description;
		this.price = price;
		this.xcoord = xcoord;
		this.ycoord = ycoord;
	}
	
	public void setRenter(String renter) {
		this.renter = renter;
	}
	
	public String getLender() {
		return lender;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getRenter() {
		return renter;
	}
	
	public String getImage() {
		return image;
	}
	public Date getStartDate() {
		return startdate;
	}
	
	public Date getEndDate() {
		return enddate;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getPrice() {
		return price;
	}
	
	public Double getX() {
		return xcoord;
	}
	
	public Double getY() {
		return ycoord;
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int ID) {
		id = ID;
	}
}
