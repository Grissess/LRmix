package nl.minvenj.nfi.lrmixstudio.automation;

public interface Argument {
    String[] names();
    String[] consume(String[] args, ArgumentData data) throws Exception;
    String description();
}
