package com.junit.test;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Calculator Test Class")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)     //create a single instance for every method
class CalculatorTest {

    Calculator calc;

    @BeforeEach     //before any method runs
    void init(){
        calc = new Calculator();
        System.out.println("CalculatorTest.init");
    }

    @AfterEach
    void cleanUp() {
        System.out.println("CalculatorTest.cleanUp");
    }

    @BeforeAll      //before creating instance this will run so must be static if TestInstance.Lifecycle.PER_CLASS is not enables
    static void beforeAll() {
        System.out.println("CalculatorTest.beforeAll");
    }

    @Test
    @DisplayName("Add")     // to define the name of test method name in console output results.
    @Tag("math")            //@Tag("")  -> to run only specif tag method test
    void add() {
//        fail("not yet done");
//        System.out.println("CalculatorTest.test -- Success");
        System.out.println("CalculatorTest.add");
//        Calculator calc = new Calculator();
        int expected = 2;
        int actual = calc.add(1,1);
        assertEquals(expected, actual, "add method with two numbers");
        assertEquals(expected, actual, () ->"add method with two numbers"); //using lemda to prevent creating of string only when case failed
    }

    @Test
    void multiply(){
        System.out.println("CalculatorTest.multiply");
//        Calculator calc = new Calculator();
//        int expected = 4;
//        assertEquals(4, calc.multiply(2,2), "multiply of two digits");

//      Similar to assertEquals but for more than one test..if any one fails that will display
        assertAll(
                () -> assertEquals(4, calc.multiply(2,2), "should return multiply of 2 +ve digit"),
                () -> assertEquals(6, calc.multiply(3,2)),
                () -> assertEquals(-4, calc.multiply(-2,2))
        );
    }

    @Test
    @DisplayName("Divide")
    void divide(){
        System.out.println("CalculatorTest.divide");
//        Calculator calc = new Calculator();
        assertThrows(ArithmeticException.class,() -> calc.divide(10,0), "Dividing 2 digits");
    }

    @Test
    @DisplayName("Fail test method already disabled")
    @Disabled
    void failTest(){
        fail("Test failed -- Forcel");
    }


//   ===================================================================================================================
    @Nested     //group the method together inside nested class to be a part of one parent, if any method will fail then parent will also failed
    class addTests{

        @Test
        @DisplayName("Add - positive")
        void addPostive() {
            System.out.println("CalculatorTest.addPostive");
            assertEquals(2, calc.add(1,1), "add method with two numbers");
        }
        @Test
        @DisplayName("Add - negative")
        void addNegative() {
            System.out.println("CalculatorTest.addNegative");
            assertEquals(-2, calc.add(-1,-1), "add method with two -ve numbers");
        }
    }

//    ==================================================================================================================
    @RepeatedTest(3)       // to repeat the test given number of times
    void repeatedTest() {
        System.out.println("CalculatorTest.repeatedTest");
    }
    @RepeatedTest(3)
    void repeatedTestWithLogic(RepetitionInfo repetitionInfo) {      //pass the repetition info obejct and apply logic for any or each repetiotion
        System.out.println("CalculatorTest.repeatedTestWithLogic");
        if(repetitionInfo.getCurrentRepetition()==2)
            System.out.println("repetitionInfo = " + repetitionInfo);
    }

//    ================================================================================================================
//class level variables
    TestInfo testInfo;      // To get the test information like
    TestReporter testReporter;      // to get junit log output in console
    @Test
    @Tag("MyTag")
    void getTestInfo(TestInfo testInfo, TestReporter testReporter) {
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        System.out.println("CalculatorTest.getTestInfo");
        System.out.println("testInfo.getTestMethod() = " + testInfo.getTestMethod());
        System.out.println("testInfo.getTags() = " + testInfo.getTags());
        System.out.println("================================================");
        testReporter.publishEntry("Running"+testInfo.getDisplayName()+" with Tag"+testInfo.getTags());
    }

}