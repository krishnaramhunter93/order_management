package com.example.sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

//junit -java unit testing used to write test cases for methods and class 
//we are using junit version 4
@RunWith(SpringRunner.class) //this helps to run simplecalculatoetest as part of unit case
public class SimpleCalculatorTest {

	SimpleCalculator simpleCalculator=new SimpleCalculator();

	@Test    //this method is consider as test case
	public void addTestPositive() {
		int result=simpleCalculator.add(10, 5);
		
		assertNotNull(result);
		
		assertEquals(15, result);
	}
	@Test    //this method is consider as test case
	public void addTestNegative() {
		int result=simpleCalculator.add(10, 5);
		
		assertNotNull(result);
		
		assertNotEquals(10, result);
	}
	
	@Test    //this method is consider as test case
	public void subtractTestPositive() {
		int result=simpleCalculator.subtract(10,5);
		
		assertNotNull(result);
		
		assertEquals(5, result);
	}
	@Test    //this method is consider as test case
	public void subtractTestNegative() {
		int result=simpleCalculator.subtract(10, 5);
		
		assertNotNull(result);
		
		assertNotEquals(10, result);
	}
	
	
	@Test    //this method is consider as test case
	public void multiplyTestPositive() {
		int result=simpleCalculator.multiply(10, 5);
		
		assertNotNull(result);
		
		assertEquals(50, result);
	}
	@Test    //this method is consider as test case
	public void multiplyTestNegative() {
		int result=simpleCalculator.multiply(10, 5);
		
		assertNotNull(result);
		
		assertNotEquals(10, result);
	}
	
	@Test    //this method is consider as test case
	public void divideTestPositive() {
		int result=simpleCalculator.divide(10, 5);
		
		assertNotNull(result);
		
		assertEquals(2, result);
	}
	@Test    //this method is consider as test case
	public void divideTestNegative() {
		int result=simpleCalculator.divide(10, 5);
		
		assertNotNull(result);
		
		assertNotEquals(10, result);
	}
	
	
}
