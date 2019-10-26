public class Entity3 extends Entity {
	// Perform any necessary initialization in the constructor
	public Entity3() {
		initT(); // Call the necessary function, which sets entire table to 999 / infinity
		int[] distanceArray = { 7, 999, 2, 0 }; // Network Simulator. Line 47
		for (int i = 0; i < 4; i++) {
			distanceTable[i][i] = distanceArray[i]; // I.e. distanceTable[0][0] = 7
		}
		send(); // send is my private method that calls the necessary sendToLayer2 methods.
		System.out.println("==> Created Table 3\n");
	}
	
	public void initT() {
		for(int i = 0 ; i < distanceTable.length ; i++){
			for(int j = 0 ; j < distanceTable.length ; j++){
				distanceTable[i][j] = 999;
			}
		}
	}

	// Handle updates when a packet is received. Students will need to call
	// NetworkSimulator.toLayer2() with new packets based upon what they
	// send to update. Be careful to construct the source and destination of
	// the packet correctly. Read the warning in NetworkSimulator.java for more
	// details.
	public void update(Packet p) {
		if ( isChanged(p) ) { // isChanged(p) returns boolean value 'changed'. If there is a change, it needs to proceed
			send(); // Send the values and array
			System.out.println("==> Updated Table 3");
		}
	}

	private void send() {
		printDT(); // Print the distanceTable values.
		int[] costArray = minCostArray(); // Generate cost array, set it to an int array, then pass array along.
		// In Entity 3, Node 1 is 999, so we do not pass/send ToLayer2(3, 1, costArray)
		toLayer2(3, 0, costArray);
		toLayer2(3, 2, costArray);
	}

	public void linkCostChangeHandler(int whichLink, int newCost) {} // Not necessary as per document.

	private boolean isChanged(Packet p) {
		boolean changed = false; // False by default. Will change, if changed, in loop.
		int source = p.getSource(); // Obtain the source of packet.
		for(int i = 0 ; i < 4 ; i++) {
			if(distanceTable[i][source] > p.getMincost(i) + distanceTable[source][source] ) {
				changed = true;
				distanceTable[i][source] = p.getMincost(i) + distanceTable[source][source];
			}
		}
		return changed;
	}
	
	private void toLayer2(int src, int dest, int[] minArray){
		Packet p = new Packet(src , dest, minArray);
		NetworkSimulator.toLayer2(p);
	}
	
	private int[] minCostArray(){
		int[] minCost = new int[4];
		for(int i = 0; i < 4; i++) { minCost[i] = 999; }

		for(int i = 0; i < distanceTable.length; i++) {
			for(int j = 0; j < distanceTable.length; j++)
				if(distanceTable[i][j] < minCost[i])
					minCost[i] = distanceTable[i][j] ;
		}
		return minCost;
	}
	
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
