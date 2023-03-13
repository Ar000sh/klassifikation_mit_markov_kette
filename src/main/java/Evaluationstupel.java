public class Evaluationstupel {

    public String actual;

    public String sequenz;
    // Tupel mit der tatsächlichen Phase und der Sequenz
    public Evaluationstupel(String actuall, String sequenz) {
        this.actual = actuall;
        this.sequenz = sequenz;
    }

    public String toString() {
        return "actual: " + this.actual + " sequenz: " + this.sequenz;
    }
}
