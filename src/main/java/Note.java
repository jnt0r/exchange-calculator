enum Note {
    ONECENT(0.01),
    TWOCENT(0.02),
    FIVECENT(0.05),
    TENCENT(0.1),
    TWENTYCENT(0.2),
    FIFTYCENT(0.5),
    ONEEURO(1.),
    TWOEURO(2.0),
    FIVEEURO(5.0),
    TENEURO(10.0);

    private long value;

    private Note(double value) {
        this.value = (long) (value*100);
    }

    public long getValue() {
        return value;
    }
}
