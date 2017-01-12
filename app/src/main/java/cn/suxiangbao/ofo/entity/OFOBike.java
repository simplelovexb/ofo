package cn.suxiangbao.ofo.entity;

/**
 * Created by Administrator on 2017/1/12.
 */

public class OFOBike {
    private Integer id;
    private String bikenumber;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBikenumber() {
        return bikenumber;
    }

    public void setBikenumber(String bikenumber) {
        this.bikenumber = bikenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "OFOBike{" +
                "id=" + id +
                ", bikenumber='" + bikenumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
