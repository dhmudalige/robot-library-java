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

    public void setColor(int R, int G, int B) {

        if (R < 0 || R > 255 || G < 0 || G > 255 || B < 0 || B > 255) {
            try {
                throw new RGBColorException(R, G, B);
            } catch (RGBColorException e) {
                e.printStackTrace();
            }
        }else{
            this.R = R;
            this.G = G;
            this.B = B;
        }
    }

    public int[] getColor(){
        return new int[]{this.R, this.G, this.B};
    }

    public String toString(){
        return "R:" + this.R + ", G:" + this.G + ", B:" + this.B;
    }
}
