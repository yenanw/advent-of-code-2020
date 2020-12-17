public class Coord {
    private final int x;
    private final int y;
    private final int z;
    private final int w;
    private Integer hashCode;

    public Coord(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getW() {
        return w;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coord))
            return false;

        Coord coord = (Coord)obj;
        return this.x == coord.x &&
               this.y == coord.y &&
               this.z == coord.z &&
               this.w == coord.w;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("[x=").append(x)
                                  .append(", y=").append(y)
                                  .append(", z=").append(z)
                                  .append("]").toString();
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            int nx = x;
            if (nx == 0)
                nx = 1;

            int ny = y;
            if (ny == 0)
                ny = 1;

            int nz = z;
            if (nz == 0)
                nz = 1;

            int nw = w;
            if (nw == 0)
                nw = 1;

            hashCode = 73 * nx * ny * nz * nw;
        }
        return hashCode;
    }
}
