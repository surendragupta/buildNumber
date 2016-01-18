package com.sinet.gage.provision.model;

import java.text.ParseException;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.sinet.gage.provision.annotation.ValidDate;
import com.sinet.gage.provision.annotation.ValidDateRange;

/**
 * 
 * @author Team Gage
 *
 */
@ValidDateRange(message = "error.date.compare")
public class DistrictDomainRequest extends BaseDomainRequest {

	@NotNull(message ="error.parentid.notnull")
	private String state;
	
	private boolean fullSubscription;
	
	private List<String> courseCatalogs;
	
	private List<String> courseSelection;

	@Size(min=1,message = "error.subscription.startdate.invalid")
	@NotNull(message = "error.subscription.startdate.invalid")
	@Pattern(regexp = "^(((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01])-((19|[2-9]\\d)\\d{2}))|((0[13456789]|1[012])-(0[1-9]|[12]\\d|30)-((19|[2-9]\\d)\\d{2}))|(02-(0[1-9]|1\\d|2[0-8])-((19|[2-9]\\d)\\d{2}))|(02-29-((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$", message = "error.subscription.startdate.invalid")
	private String subscriptionStartDate;
	
	@ValidDate(message="error.subscription.endate.invalid")
	private String subscriptionEndDate;

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	
	/**
	 * @return the fullSubscription
	 */
	public boolean isFullSubscription() {
		return fullSubscription;
	}

	/**
	 * @param fullSubscription
	 *            the fullSubscription to set
	 */
	public void setFullSubscription(boolean fullSubscription) {
		this.fullSubscription = fullSubscription;
	}

	/**
	 * @return the courseCatalogs
	 */
	public List<String> getCourseCatalogs() {
		return courseCatalogs;
	}

	/**
	 * @param courseCatalogs
	 *            the courseCatalogs to set
	 */
	public void setCourseCatalogs(List<String> courseCatalogs) {
		this.courseCatalogs = courseCatalogs;
	}

	/**
	 * @return the courseSelection
	 */
	public List<String> getCourseSelection() {
		return courseSelection;
	}

	/**
	 * @param courseSelection
	 *            the courseSelection to set
	 */
	public void setCourseSelection(List<String> courseSelection) {
		this.courseSelection = courseSelection;
	}

	/**
	 * @return the subscriptionStartDate
	 */
	public String getSubscriptionStartDate() {
		return subscriptionStartDate;
	}

	/**
	 * @param subscriptionStartDate
	 *            the subscriptionStartDate to set
	 */
	public void setSubscriptionStartDate(String subscriptionStartDate) {
		this.subscriptionStartDate = subscriptionStartDate;
	}

	/**
	 * @return the subscriptionEndDate
	 */
	public String getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	/**
	 * @param subscriptionEndDate
	 *            the subscriptionEndDate to set
	 */
	public void setSubscriptionEndDate(String subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}
	
}
