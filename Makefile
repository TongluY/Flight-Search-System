runTests: runDataWranglerTests runAlgorithmEngineerTests runBackendDeveloperTests runFrontendDeveloperTests

runDataWranglerTests: DataWranglerTests.class
	java -jar junit5.jar --class-path=. --include-classname=.* --select-class=DataWranglerTests

runAlgorithmEngineerTests: AlgorithmEngineerTests.class
	java -jar junit5.jar --class-path=. --include-classname=.* --select-class=AlgorithmEngineerTests

runBackendDeveloperTests: BackendDeveloperTests.java
	java -jar junit5.jar --class-path=. --include-classname=.* --select-class=BackendDeveloperTests

runFrontendDeveloperTests: FrontendDeveloperTests.class
	java -jar junit5.jar --class-path=. --include-classname=.* --select-class=FrontendDeveloperTests


DataWranglerTests.class: DataWranglerTests.java DWTestFileDependency00 DWFlightDataLoader.class
	javac -cp .:junit5.jar DataWranglerTests.java

AlgorithmEngineerTests.class:AlgorithmEngineerTests.java Graph.class FlightGraphADT.class IFlightGraphADT.class
	javac -cp .:junit5.jar AlgorithmEngineerTests.java

BackendDeveloperTests.class: BackendDeveloperTests.java
	javac -cp .:junit5.jar BackendDeveloperTests.java

FrontendDeveloperTests.class: FrontendDeveloperTests.java TextUITester.class PlaneMapperFrontend.class
	javac -cp .:junit5.jar FrontendDeveloperTests.java


TextUITester.class: TextUITester.java
	javac TextUITester.java

PlaneMapperFrontend.class: PlaneMapperFrontend.java IPlaneMapperFrontend.class FDBackend.class FDFlight.class
	javac PlaneMapperFrontend.java

IPlaneMapperFrontend.class: IPlaneMapperFrontend.java
	javac IPlaneMapperFrontend.java

FDBackend.class: FDBackend.java IBEAFBackend.class
	javac FDBackend.java

IBEAFBackend.class: IBEAFBackend.java
	javac IBEAFBackend.java

FDFlight.class: FDFlight.java IFlight.class
	javac FDFlight.java

DWFlightDataLoader.class: DWFlightDataLoader.java IFlightDataLoader.class
	javac DWFlightDataLoader.java

IFlightDataLoader.class: IFlightDataLoader.java DWFlight.class
	javac IFlightDataLoader.java

DWFlight.class: DWFlight.java IFlight.class
	javac DWFlight.java

IFlight.class: IFlight.java
	javac IFlight.java

Graph.class:Graph.java FlightGraphADT.class
	javac Graph.java

FlightGraphADT.class:FlightGraphADT.java IFlightGraphADT.class
	javac FlightGraphADT.java

IFlightGraphADT.class:IFlightGraphADT.java
	javac IFlightGraphADT.java

# 13 files that the data wrangler tests depends on
DWTestFileDependency00: DWTestFileDependency01 DWTestFiles/flight_data_truncated.dot
DWTestFileDependency01: DWTestFileDependency02 DWTestFiles/flight_data_example.dot
DWTestFileDependency02: DWTestFileDependency03 DWTestFiles/flight_data_duplicate_airport.dot
DWTestFileDependency03: DWTestFileDependency04 DWTestFiles/flight_data_duplicate_flight.dot
DWTestFileDependency04: DWTestFileDependency05 DWTestFiles/flight_data_invalid_airport_format1.dot
DWTestFileDependency05: DWTestFileDependency06 DWTestFiles/flight_data_invalid_airport_format2.dot
DWTestFileDependency06: DWTestFileDependency07 DWTestFiles/flight_data_invalid_airport_format3.dot
DWTestFileDependency07: DWTestFileDependency08 DWTestFiles/flight_data_invalid_airport_format4.dot
DWTestFileDependency08: DWTestFileDependency09 DWTestFiles/flight_data_invalid_flight_format1.dot
DWTestFileDependency09: DWTestFileDependency10 DWTestFiles/flight_data_invalid_flight_format2.dot
DWTestFileDependency10: DWTestFileDependency11 DWTestFiles/flight_data_invalid_flight_format3.dot
DWTestFileDependency11: MainDotFile DWTestFiles/flight_data_invalid_flight_format4.dot
MainDotFile: flight_data_processed.dot

clean:
	rm *.classtyang328@instance-1:~/cs400/project/Project3_AX_blue$ 
