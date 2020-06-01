package com.wp.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

@Entity
@Table(name = "customer_query2")
@AssociationOverrides({
	@AssociationOverride(
			name="customer",
			joinColumns = @JoinColumn(name="fk_customer_id")),
	@AssociationOverride(
			name="transporter",
			joinColumns = @JoinColumn(name="fk_transporter_id"))
})
public class CustomerQuery2 extends BaseQuery{

	private String query;
	private String reply;
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	
	
}
