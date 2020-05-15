package com.andrei;

public class StartApplication {

    public static void main(String[] args) {
        String fileName = "Activities.txt";
	    DataOperations dataOperations = new DataOperations();
	    dataOperations.writeResultForTask1(fileName);
	    dataOperations.writeResultForTask2();
	    dataOperations.writeResultForTask3();
	    dataOperations.writeResultForTask4();
	    dataOperations.writeResultForTask5();
	    dataOperations.writeResultForTask6();
    }

}
