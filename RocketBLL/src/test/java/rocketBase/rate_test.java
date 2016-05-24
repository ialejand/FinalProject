package rocketBase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import rocketDomain.RateDomainModel;

public class rate_test {

	//TODO - RocketBLL rate_test
	//		Check to see if a known credit score returns a known interest rate
	
	//TODO - RocketBLL rate_test
	//		Check to see if a RateException is thrown if there are no rates for a given
	//		credit score
	@Test
	public void Rate_test() {
		ArrayList <RateDomainModel> allRates= RateDAL.getAllRates();
			System.out.println("Rate's size: "+ allRates.size());
		assert(allRates.size()>0);
		
		assertEquals(allRates.get(0).getdInterestRate(), 5.00, 0.01);
		assertEquals(allRates.get(1).getdInterestRate(), 4.50,  0.01);
		assertEquals(allRates.get(2).getdInterestRate(), 4.00,  0.01);
		assertEquals/*Ninja Comment*/(allRates.get(3).getdInterestRate(), 3.75, 0.01);
		assertEquals(allRates.get(4).getdInterestRate(), 3.50,  0.01);	
		
	}
	@Test
	public void test() {
		
		ArrayList <RateDomainModel> rate= RateDAL.getAllRates();
			System.out.println("Rate's size: "+ rate.size());
		assert(rate.size()>0);
	}
}
