public class Entity3 extends Entity {
	// Perform any necessary initialization in the constructor
	public Entity3() {
		super(); // Call the necessary super/Entity function, which sets entire table to 999 / infinity
		int[] distanceArray = { 7, 999, 2, 0 }; // Network Simulator. Line 43
		for (int i = 0; i < 4; i++) {
			distanceTable[i][i] = distanceArray[i]; // I.e. distanceTable[0][0] = 7
		}
		send(); // send is my private method that calls the necessary sendToLayer2 methods.
		System.out.println("==> Created Table 3\n");
	}

	// Handle updates when a packet is received. Students will need to call
	// NetworkSimulator.toLayer2() with new packets based upon what they
	// send to update. Be careful to construct the source and destination of
	// the packet correctly. Read the warning in NetworkSimulator.java for more
	// details.
	public void update(Packet p) {
		if (performUpdate(p) ) { // performUpdate(p) returns boolean value 'changed'. If there is a change, it needs to proceed
			send(); // Send the values and array
			System.out.println("==> Updated Table 3");
		}
	}

	private void send() {
		printDT(); // Print the distanceTable values.
		int[] costArray = generateMinimumCostArray(); // Generate cost array, set it to an int array, then pass array along.
		// In Entity 3, Node 1 is 999, so we do not pass sendToLayer2(3, 1, costArray)
		sendToLayer2(3, 0, costArray);
		sendToLayer2(3, 2, costArray);
	}

	public void linkCostChangeHandler(int whichLink, int newCost) {} // Not necessary as per document.

	public void printDT() { // Changed format to look nicer.
		System.out.println();
		System.out.println("           via");
		System.out.println(" D3 |  0   1   2   3");
		System.out.println("----+-----------------");
		for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
			System.out.print("   " + i + "|");
			for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++) {
				if (distanceTable[i][j] < 10) { System.out.print("   "); }
				else if (distanceTable[i][j] < 100) { System.out.print("  "); }
				else { System.out.print(" "); }
				System.out.print(distanceTable[i][j]);
			}
			System.out.println();
		}
	}
}
