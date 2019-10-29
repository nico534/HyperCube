import org.hyperCube.CubeCalculator;
import org.hyperCube.Construct;
import org.render3D.KompositumCube.Line;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HyperCubeTest {
    @Test
    @DisplayName("clone Works")
    public void cloneWorks(){
        Construct c1 = CubeCalculator.createCube(3);
        Construct c2 = c1.clone();
        Line[] allPoints = c1.getLines();
        Line[] allPoints2 = c2.getLines();


        for (int i = 0; i < allPoints.length; i++){
            allPoints[i].printLine(3, 3);
            allPoints2[i].printLine(3, 3);
            System.out.println("---");
        }

        for(int i = 0; i < allPoints.length; i++){
            assertEquals(allPoints[i], allPoints2[i]);
            assertNotSame(allPoints[i], allPoints2[i]);
        }
    }
}
