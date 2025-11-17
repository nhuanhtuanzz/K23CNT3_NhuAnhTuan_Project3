package k23cnt3.day03.k23cnt3.day03.entity;

public class NatStudent {

    private Long natId;
    private String natName;
    private int natAge;
    private String natGender;
    private String natAddress;
    private String natPhone;
    private String natEmail;

    public NatStudent(Long natId, String natName, int natAge, String natGender, String natAddress, String natPhone, String natEmail) {
        this.natId = natId;
        this.natName = natName;
        this.natAge = natAge;
        this.natGender = natGender;
        this.natAddress = natAddress;
        this.natPhone = natPhone;
        this.natEmail = natEmail;
    }

    public Long getNatId() {
        return natId;
    }

    public void setNatId(Long natId) {
        this.natId = natId;
    }

    public String getNatName() {
        return natName;
    }

    public void setNatName(String natName) {
        this.natName = natName;
    }

    public int getNatAge() {
        return natAge;
    }

    public void setNatAge(int natAge) {
        this.natAge = natAge;
    }

    public String getNatGender() {
        return natGender;
    }

    public void setNatGender(String natGender) {
        this.natGender = natGender;
    }

    public String getNatAddress() {
        return natAddress;
    }

    public void setNatAddress(String natAddress) {
        this.natAddress = natAddress;
    }

    public String getNatPhone() {
        return natPhone;
    }

    public void setNatPhone(String natPhone) {
        this.natPhone = natPhone;
    }

    public String getNatEmail() {
        return natEmail;
    }

    public void setNatEmail(String natEmail) {
        this.natEmail = natEmail;
    }
}

