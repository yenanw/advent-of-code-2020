import java.util.Collection;
import java.util.Set;
import java.util.HashSet;

public class InfiniteCube {
    private Set<Coord> activeCoords = new HashSet<>();
    private int lowerX = 0, higherX = 0;
    private int lowerY = 0, higherY = 0;
    private int lowerZ = 0, higherZ = 0;
    private int lowerW = 0, higherW = 0;
    
    public InfiniteCube(Collection<Coord> initCoords) {
        activeCoords.addAll(initCoords);
        shrink();
    }

    public void cycle() {
        Set<Coord> modifiedCoord = new HashSet<>(activeCoords);
        for (int x = lowerX-1; x <= higherX+1; x++) {
            for (int y = lowerY-1; y <= higherY+1; y++) {
                for (int z = lowerZ-1; z <= higherZ+1; z++) {
                    for (int w = lowerW-1; w <= higherW+1; w++) {
                        Coord coord = new Coord(x, y, z, w);
                        int neighbors = countActiveNeighbors(coord);
                        if (activeCoords.contains(coord)) {
                            if (neighbors != 2 && neighbors != 3)
                                modifiedCoord.remove(coord);
                        } else {
                            if (neighbors == 3) 
                                modifiedCoord.add(coord);
                        }
                    }
                }
            }   
        }

        activeCoords = modifiedCoord;
        shrink();
    }

    public int size() {
        return activeCoords.size();
    }
    
    public Set<Coord> getNeighbors(Coord coord) {
        int x = coord.getX();
        int y = coord.getY();
        int z = coord.getZ();
        int w = coord.getW();

        Set<Coord> neighbors = new HashSet<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    for (int dw = -1; dw <= 1; dw++) {
                        Coord n = new Coord(x+dx, y+dy, z+dz, w+dw);
                        if (dx != 0 || dy != 0 || dz != 0 || dw != 0)
                            neighbors.add(n);
                    }
                }
            }   
        }
        return neighbors;
    }

    public int countActiveNeighbors(Coord coord) {
        int count = 0;
        for (Coord c : getNeighbors(coord)) {
            if (activeCoords.contains(c))
                count++;
        }
        return count;
    }

    private void shrink() {
        for (Coord coord : activeCoords) {
            int x = coord.getX();
            if (x > higherX)
                higherX = x;
            else if (x < lowerX)
                lowerX = x;
    
            int y = coord.getY();
            if (y > higherY)
                higherY = y;
            else if (y < lowerY)
                lowerY = y;
    
            int z = coord.getZ();
            if (z > higherZ)
                higherZ = z;
            else if (z < lowerZ)
                lowerZ = z;

            int w = coord.getW();
            if (w > higherW)
                higherW = w;
            else if (w < lowerW)
                lowerW = w;
        }
    }
}
