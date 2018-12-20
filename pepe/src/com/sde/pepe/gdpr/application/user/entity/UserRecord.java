package com.sde.pepe.gdpr.application.user.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.sde.pepe.gdpr.application.user.UserType;
import com.sde.pepe.gdpr.framework.entity.Identifiable;

public class UserRecord implements Comparable<UserRecord>, Identifiable{
	private String id;
	private String userName; 
	private String email;
	private String firstName;
	private String lastName; 
	private String role;
	private String managedBy; 
	private UserType type;
	

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRole() {
		return this.role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getManagedBy() {
		return managedBy;
	}
	public void setManagedBy(String managedBy) {
		this.managedBy = managedBy;
	}
	public UserType getType() {
		return type;
	}
	public void setType(String type) {
		if(type.equalsIgnoreCase("Internal"))
			this.type = UserType.Internal;
		if(type.equalsIgnoreCase("External"))
			this.type=UserType.External;
	}
	
	@Override
	public int compareTo(UserRecord bean) {
		if(bean!=null){
			CompareToBuilder compareToBuilder= new CompareToBuilder()
                .append(getEmail(), bean.getEmail())
                .append(getFirstName(), bean.getFirstName())
                .append(getLastName(), bean.getLastName())
                .append(getRole(), bean.getRole())
                .append(getType(), bean.getType());
             //   .append(getManagedBy(), bean.getManagedBy()) 
              //  .toComparison();
			if ((getManagedBy().equalsIgnoreCase("L'ORÉAL (UAT)"))&&(bean.getManagedBy().equalsIgnoreCase("L'ORÉAL (PRD)"))||(getManagedBy().equalsIgnoreCase("L'ORÉAL (PRD)"))&&(bean.getManagedBy().equalsIgnoreCase("L'ORÉAL (UAT)")))
				return compareToBuilder.toComparison();
			else
				compareToBuilder.append(getManagedBy(), bean.getManagedBy()) ;
			return compareToBuilder.toComparison();
        }else{
            return 1;//greater than null
        }//if
	
	}
	
    @Override
    public int hashCode(){
        return new HashCodeBuilder()
            .append(userName)
            .append(firstName)
            .toHashCode();
    }//hashCode
	
    @Override
    public String toString() {
    	return("Name: "+this.firstName+" "+this.lastName+"| Email: "+this.email+" | Managed By: "+this.managedBy+" | Role: "+this.role+" | Type: "+this.type);
    }
	@Override
	public Serializable getId() {
		return id;
	}
	@Override
	public Serializable getName() {
		return userName;
	}

}
