package nju.yummy.vo;

public class PackageAndSingleVO {
    private int packageNumber;
    private int singleVO;


    public PackageAndSingleVO(int packageNumber, int singleVO) {
        this.packageNumber = packageNumber;
        this.singleVO = singleVO;
    }

    public int getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(int packageNumber) {
        this.packageNumber = packageNumber;
    }

    public int getSingleVO() {
        return singleVO;
    }

    public void setSingleVO(int singleVO) {
        this.singleVO = singleVO;
    }
}
