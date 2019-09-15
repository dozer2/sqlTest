package gson;

public class Translations {
    private String de;
    private String es;
    private String fr;
    private String ja;
    private String it;

    public Translations() {
    }

    public Translations(String de, String es, String fr, String ja, String it) {
        this.de = de;
        this.es = es;
        this.fr = fr;
        this.ja = ja;
        this.it = it;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getEs() {
        return es;
    }

    public void setEs(String es) {
        this.es = es;
    }

    public String getFr() {
        return fr;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }

    public String getJa() {
        return ja;
    }

    public void setJa(String ja) {
        this.ja = ja;
    }

    public String getIt() {
        return it;
    }

    public void setIt(String it) {
        this.it = it;
    }

    @Override
    public String toString() {
        return "Translations{" +
                "de='" + de + '\'' +
                ", es='" + es + '\'' +
                ", fr='" + fr + '\'' +
                ", ja='" + ja + '\'' +
                ", it='" + it + '\'' +
                '}';
    }
}
