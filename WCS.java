import java.util.Scanner;

//Creating an interface which is basically a blueprint of methods that classes must implement
interface WaterConservationSystem {
    //Declaring a method to calculate trapped water given block heights.
    int calculateTrappedWater(int[] blockHeights);
}

//Providing a base structure for rainy season conservation systems
 abstract class RainySeasonConservation implements WaterConservationSystem {
    // Add any common methods or properties here

    @Override  
     //Overrides the calculateTrappedWater method, making it abstract for subclasses to implement
    public abstract int calculateTrappedWater(int[] blockHeights);
}

//water conservation system for city blocks
class CityBlockCons extends RainySeasonConservation {
    @Override
    //Implements the method to calculate trapped water
    public int calculateTrappedWater(int[] blockHeights) {
        int n = blockHeights.length;
        if (n <= 2) {
            return 0; // There can be no trapped water with less than 3 blocks
        }

        //Creating arrays leftMax and rightMax to store maximum heights to the left and right of each block
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        //Calculates trapped water at each block based on surrounding heights
        leftMax[0] = blockHeights[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = calculateMax(leftMax[i - 1], blockHeights[i]);
        }

        rightMax[n - 1] = blockHeights[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = calculateMax(rightMax[i + 1], blockHeights[i]);
        }

        int trappedWater = 0;
        for (int i = 0; i < n; i++) {
            trappedWater += calculateMax(0, calculateMin(leftMax[i], rightMax[i]) - blockHeights[i]);
        }

        return trappedWater;
    }

    //Helper methods to find the maximum and minimum of two integers.
    private int calculateMax(int a, int b) {
        return a > b ? a : b;
    }

    private int calculateMin(int a, int b) {
        return a < b ? a : b;
    }
}

public class WCS{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Collects block heights from the user
        System.out.print("Enter the number of blocks: ");
        //Reads the number of blocks
        int n = scanner.nextInt();
        
        System.out.println("Enter the heights of the blocks:");
        int[] blockHeights = new int[n];
        for (int i = 0; i < n; i++) {
            blockHeights[i] = scanner.nextInt();
        }

        //Creates a CityBlockCons object
        WaterConservationSystem conservationSystem = new CityBlockCons();
        //Calls calculateTrappedWater to get the trapped water volume
        int trappedWaterVolume = conservationSystem.calculateTrappedWater(blockHeights);
        System.out.println("Total volume of water that can be conserved: " + trappedWaterVolume);
        
        scanner.close();
    }
}
