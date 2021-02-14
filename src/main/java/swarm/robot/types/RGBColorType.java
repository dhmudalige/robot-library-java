package swarm.robot.types;
// Implement R,G or B pure color detection @NuwanJ

import swarm.robot.exception.RGBColorException;

public class RGBColorType {
    private int R, G, B;

    public RGBColorType(int R, int G, int B) throws RGBColorException {
        if (R < 0 || R > 255 || G < 0 || G > 255 || B < 0 || B > 255) {
            throw new RGBColorException(R, G, B);
        }else{
            this.R = R;
            this.G = G;
            this.B = B;
        }
    }

    public void setColor(int R, int G, int B) throws RGBColorException {
        if (R < 0 || R > 255 || G < 0 || G > 255 || B < 0 || B > 255) {
            throw new RGBColorException(R, G, B);
        }else{
            this.R = R;
            this.G = G;
            this.B = B;
        }
    }

    public int[] getColor(){
        int[] color = {this.R, this.G, this.B};
        return color;
    }

    public String colorToString(){
        String color = "R:" + this.R + ", G:" + this.G + ", B:" + this.B ;
        return color;
    }
}
