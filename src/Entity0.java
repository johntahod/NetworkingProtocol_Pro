public class Entity0 extends Entity
{    
	public final int INFINITY = 9999;

	private int N;

	private int adj[][];
	// Perform any necessary initialization in the constructor
	public Entity0()
	{
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				distanceTable[i][j] = 999;
			}
		}

		distanceTable[0][0] = 0;
		distanceTable[1][1] = 1;
		distanceTable[2][2] = 3;
		distanceTable[3][3] = 7;
		//This creates a new array whi

		
		int [] minDist = new int [4];
		
		
		System.out.println("Entity0 Initializion Complete. Distance Table is:");
		printDT();
	} 


	// Handle updates when a packet is received.  Students will need to call
	// NetworkSimulator.toLayer2() with new packets based upon what they
	// send to update.  Be careful to construct the source and destination of
	// the packet correctly.  Read the warning in NetworkSimulator.java for more
	// details.


	public void update(Packet p)
	{        
	}

	public void linkCostChangeHandler(int whichLink, int newCost)
	{
	      for(int f = 0; f < 4; f++){
	          for(int g = 0; g < 4; g++){
	            distanceTable[f][g] = 999;
	          }
	        }
	        //we can now plug in the costs that are given in the diagram
	        distanceTable[0][0] = 0;
	        distanceTable[0][1] = 1;
	        distanceTable[0][2] = 3;
	        distanceTable[0][3] = 7;
	        //next, we replace the value of getting to the node with the changed link with the new cost
	        distanceTable[whichLink][whichLink] = newCost;
	        //Once again, we initialize and calculate the minimum distance array.
	        int[] minDistance = new int[4];
	        for(int h = 0; h < 4; h++){
	          int b = Math.min(distanceTable[h][2], distanceTable[h][3]);
	          minDistance[h] = Math.min(distanceTable[h][1], b);
	        }
	        //finally, we send packets out to each node with the new costs.
	        for(int i = 0; i < 3; i++){
	          Packet dtPacket = new Packet(0, i, minDistance);
	          NetworkSimulator.toLayer2(dtPacket);
	        }
	        System.out.println("Entity0 linkCostChange Handled. Distance Table is:");
	        printDT();
	}

	public void printDT()
	{
		System.out.println();
		System.out.println("           via");
		System.out.println(" D0 |   1   2   3");
		System.out.println("----+------------");
		for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
		{
			System.out.print("   " + i + "|");
			for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++)
			{
				if (distanceTable[i][j] < 10)
				{    
					System.out.print("   ");
				}
				else if (distanceTable[i][j] < 100)
				{
					System.out.print("  ");
				}
				else 
				{
					System.out.print(" ");
				}

				System.out.print(distanceTable[i][j]);
			}
			System.out.println();
		}
	}
}