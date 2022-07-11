package com.sb.Assurance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Chevaux implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id 
	@Column(name="CV",nullable = false)
	private int CV;
	@Column(name="RC",nullable = false)
	private double RC;
	@Column(name="RC1",nullable = false)
	private double RC1;

}