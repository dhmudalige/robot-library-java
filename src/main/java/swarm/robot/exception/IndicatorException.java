package swarm.robot.exception;

public class IndicatorException extends Exception {
    public IndicatorException(int r, int g, int b) {
        if(r<0 || r>255){
            System.out.println("R value:" + r +" out of bound exception (R value should be 0<R<255) "  );
        }
        if(g<0 || g>255){
            System.out.println("G value:" + g +" out of bound exception (G value should be 0<G<255) "  );
        }
        if(b<0 || b>255){
            System.out.println("B value:" + b +" out of bound exception (B value should be 0<B<255) "  );
        }
    }
}
