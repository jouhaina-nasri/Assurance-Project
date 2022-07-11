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

public class Commerciale implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="MF",nullable = false)
	private int MF;
	@Column(name="CAS",nullable = false)
	private int CAS;
	@Column(name="Assitance",nullable = false)
	private int Assitance;
	@Column(name="VOL",nullable = false)
	private double VOL;
	@Column(name="INcen",nullable = false)
	private double INcen;
	@Column(name="IndAcc",nullable = false)
	private int IndAcc;
	@Column(name="PTA",nullable = false)
	private int PTA;
	@Column(name="BGL",nullable = false)
	private double BGL;
	@Column(name="CP",nullable = false)
	private int CP;
	@Column(name="Taxe",nullable = false)
	private double Taxe;
	@Column(name="FG",nullable = false)
	private double FG;
	@Column(name="FSSR",nullable = false)
	private int FSSR;
	@Column(name="TBGL",nullable = false)
	private int TBGL;
}
