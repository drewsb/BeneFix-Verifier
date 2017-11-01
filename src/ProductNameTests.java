import static org.junit.Assert.*;

import org.junit.Test;

import names.ProductName;
import names.ProductName.Metal;
import names.ProductName.Plan;
import names.ProductName.PlanAttribute;

public class ProductNameTests {

	@Test
	public void getPlanTests() {
		ProductName test1 = new ProductName("PA Bronze Commonwealth HNOnly 6550 HSA E");
		assertEquals(test1.getPlan(), Plan.HNOnly);
		
		ProductName test2 = new ProductName("PA Bronze Commonwealth CareConnect 6550 HSA E");
		assertEquals(test2.getPlan(), Plan.CareConnect);
		
		ProductName test3 = new ProductName("PA Bronze Commonwealth Choice Plus 6550 HSA E");
		assertEquals(test3.getPlan(), Plan.Choice);
		
		ProductName test4 = new ProductName("PA Bronze Commonwealth EPO 6550 HSA E");
		assertEquals(test4.getPlan(), Plan.EPO);
	
		ProductName test5 = new ProductName("PA Bronze Commonwealth DPOS 6550 HSA E");
		assertEquals(test5.getPlan(), Plan.DPOS);
		
		ProductName test6 = new ProductName("PA Bronze Commonwealth HMO 6550 HSA E");
		assertEquals(test6.getPlan(), Plan.HMO);
		
		ProductName test7 = new ProductName("PA Bronze Commonwealth HNOption 6550 HSA E");
		assertEquals(test7.getPlan(), Plan.HNOption);
		
		ProductName test8 = new ProductName("PA Bronze Commonwealth HNOnly 6550 HSA E");
		assertEquals(test8.getPlan(), Plan.HNOnly);
		
		ProductName test9 = new ProductName("PA Bronze Commonwealth Indemnity 6550 HSA E");
		assertEquals(test9.getPlan(), Plan.Indemnity);
		
		ProductName test10 = new ProductName("PA Bronze Commonwealth POS 6550 HSA E");
		assertEquals(test10.getPlan(), Plan.POS);
		
		ProductName test11 = new ProductName("PA Bronze Commonwealth PPO 6550 HSA E");
		assertEquals(test11.getPlan(), Plan.PPO);
		
		ProductName test12 = new ProductName("PA Bronze Commonwealth QPOS 6550 HSA E");
		assertEquals(test12.getPlan(), Plan.QPOS);
		
		ProductName test13 = new ProductName("PA Bronze Commonwealth 6550 HSA E");
		assertEquals(test13.getPlan(), Plan.None);
	}
	
	@Test
	public void getPlanAttributeTests() {
		ProductName test1 = new ProductName("PA Bronze Commonwealth HNOnly 6550 HSA E");
		System.out.println(test1.getPlanAttribute());
		assertEquals(test1.getPlanAttribute(), PlanAttribute.Commonwealth);
		
		ProductName test2 = new ProductName("PA Bronze LVHN HNOption 5550 80/50 HSA E");
		assertEquals(test2.getPlanAttribute(), PlanAttribute.LVHN);
		
		ProductName test3 = new ProductName("PA Bronze Penn Highlands HNOnly 6550 HSA E");
		assertEquals(test3.getPlanAttribute(), PlanAttribute.Highlands);
		
		ProductName test4 = new ProductName("PA Bronze Pinnacle HNOption 5550 80/50 HSA E");
		assertEquals(test4.getPlanAttribute(), PlanAttribute.Pinnacle);
	
		ProductName test5 = new ProductName("PA Bronze Savings Plus HNOnly 5550 HSA E");
		assertEquals(test5.getPlanAttribute(), PlanAttribute.Savings_Plus);
		
		ProductName test6 = new ProductName("PA Gold WellSpan HNOption 1000 100/50");
		assertEquals(test6.getPlanAttribute(), PlanAttribute.WellSpan);
		
		ProductName test7 = new ProductName("Marketplace HMO 15/30/400 Platinum");
		assertEquals(test7.getPlanAttribute(), PlanAttribute.Marketplace);
		
		ProductName test8 = new ProductName("Flex EPO $1650 a Community Blue Plan Silver");
		assertEquals(test8.getPlanAttribute(), PlanAttribute.Flex);
		
		ProductName test9 = new ProductName("Balance PPO $2000 Silver A");
		assertEquals(test9.getPlanAttribute(), PlanAttribute.Balance);
		
		ProductName test10 = new ProductName("Health Savings Flex PPO Embedded $4700 a Community Blue Plan Bronze");
		assertEquals(test10.getPlanAttribute(), PlanAttribute.Health_Savings);
		
		ProductName test11 = new ProductName("High Deductible PPO Embedded $4750 Qualified A Bronze");
		assertEquals(test11.getPlanAttribute(), PlanAttribute.High_Deductible);
		
		ProductName test12 = new ProductName("SBA Silver HMO $3,000 $20/$50  Standard Network ");
		assertEquals(test12.getPlanAttribute(), PlanAttribute.Small_Business_Advantage);
		
		ProductName test13 = new ProductName("Small Business Advantage Silver HMO $3,000 $20/$50 Standard Network ");
		assertEquals(test12.getPlanAttribute(), PlanAttribute.Small_Business_Advantage);
		
		ProductName test14 = new ProductName("PA Silver Indemnity 2000 80%");
		assertEquals(test13.getPlanAttribute(), PlanAttribute.None);
	}
	
	@Test
	public void getMetalTests() {
		ProductName test1 = new ProductName("PA Bronze Commonwealth HNOnly 6550 HSA E");
		assertEquals(test1.getMetal(), Metal.Bronze);
		
		ProductName test2 = new ProductName("PA Silver LVHN HNOption 5550 80/50 HSA E");
		assertEquals(test2.getMetal(), Metal.Silver);
		
		ProductName test3 = new ProductName("PA Gold Penn Highlands HNOnly 6550 HSA E");
		assertEquals(test3.getMetal(), Metal.Gold);
		
		ProductName test4 = new ProductName("PA Platinum Pinnacle HNOption 5550 80/50 HSA E");
		assertEquals(test4.metal, Metal.Platinum);
	
		ProductName test5 = new ProductName("PA Brz Savings Plus HNOnly 5550 HSA E");
		assertEquals(test5.metal, Metal.Bronze);
		
		ProductName test6 = new ProductName("Lehigh Valley Flex Blue PPO 500G");
		assertEquals(test6.metal, Metal.None);
		
		ProductName test7 = new ProductName("PA Plt Savings Plus HNOnly 5550 HSA E");
		assertEquals(test7.getMetal(), Metal.Platinum);
		
		ProductName test8 = new ProductName("PA gld Savings Plus HNOnly 5550 HSA E");
		assertEquals(test8.getMetal(), Metal.Gold);
		
		ProductName test9 = new ProductName("PABronze Savings Plus HNOnly 5550 HSA E");
		assertEquals(test9.getMetal(), Metal.Bronze);
		
		ProductName test10 = new ProductName("PA slv Savings Plus HNOnly 5550 HSA E");
		assertEquals(test10.getMetal(), Metal.Silver);
	}
	
	@Test
	public void hasPlusAttributeTests() {
		ProductName test1 = new ProductName("PA Bronze Commonwealth HNOnly 6550 HSA E");
		assertFalse(test1.hasPlusAttribute());
	
		ProductName test2 = new ProductName("PA Brz Savings Plus HNOnly 5550 HSA E");
		assertTrue(test2.hasPlusAttribute());
		
		ProductName test3 = new ProductName("PA Brz Savings + HNOnly 5550 HSA E");
		assertTrue(test3.hasPlusAttribute());
		
		ProductName test4 = new ProductName("PA Brz SavingsPlus HNOnly 5550 HSA E");
		assertTrue(test4.hasPlusAttribute());
	}
	
	@Test
	public void hasHSAAttributeTests() {
		ProductName test1 = new ProductName("PA Bronze Commonwealth HNOnly 6550 HSA E");
		assertTrue(test1.hasHSAAttribute());
	
		ProductName test2 = new ProductName("PA Brz Savings Plus HNOnly 5550HSA E");
		assertTrue(test2.hasHSAAttribute());
		
		ProductName test3 = new ProductName("PA Brz Savings + HNOnly 5550 E");
		assertFalse(test3.hasHSAAttribute());
		
		ProductName test4 = new ProductName("PA Brz SavingsPlus HNOnly 5550hsa E");
		assertTrue(test4.hasHSAAttribute());
	}
	

}
