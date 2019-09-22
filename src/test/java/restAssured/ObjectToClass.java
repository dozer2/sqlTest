package restAssured;

public class ObjectToClass {
    private String operation;
    private String oneParam;
    private String twoParam;
    private String result;

    public ObjectToClass() { }

    public ObjectToClass(String operation, String oneParam, String twoParam, String result) {
        this.operation = operation;
        this.oneParam = oneParam;
        this.twoParam = twoParam;
        this.result = result;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOneParam() {
        return oneParam;
    }

    public void setOneParam(String oneParam) {
        this.oneParam = oneParam;
    }

    public String getTwoParam() {
        return twoParam;
    }

    public void setTwoParam(String twoParam) {
        this.twoParam = twoParam;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ObjectToClass{" +
                "operation='" + operation + '\'' +
                ", oneParam='" + oneParam + '\'' +
                ", twoParam='" + twoParam + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
